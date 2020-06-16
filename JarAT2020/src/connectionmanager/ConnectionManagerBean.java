package connectionmanager;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;

import agentcenter.AgentCenter;
import agentmanager.AgentManager;
import agentmanager.RunningAgents;
import agents.AID;
import agents.AgentRemote;
import nodes.NodeManager;
import rest.RestServerRemote;
import util.JSON;
import ws.WebSocketEndPoints;



@Singleton
@Startup
@Remote(ConnectionManager.class)
@Path("/connection")
public class ConnectionManagerBean implements ConnectionManager {

	private AgentCenter ac = new AgentCenter();
	private String master = null; // podesiti master adresu unutar init() metode
	private List<String> connections = new ArrayList<String>();

	@EJB
	private AgentManager agm;

	@EJB
	private WebSocketEndPoints ws;
	
	@PostConstruct
	private void init() {
					
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			
			this.ac.setAddress((String) mBeanServer.getAttribute(http, "boundAddress") + ":8080");
			this.ac.setAlias(NodeManager.getNodeName() + ":8080");
			
			this.master = "192.168.0.20:8080";

			System.out.println("\nMASTER ADDR: " + master + ", node (agent center) alias: " + this.ac.getAlias() + ", node (agent center) address: " + this.ac.getAddress() + "\n");
			
			if (master != null && !master.equals("") && !master.equals(this.ac.getAddress())) {				
				
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget rtarget = client.target("http://" + master + "/WarAT2020/rest/connection");
				ConnectionManager rest = rtarget.proxy(ConnectionManager.class);
				this.connections = rest.newConnection(this.ac.getAddress());
				this.connections.remove(this.ac.getAddress());
				this.connections.add(this.master);
				
				for(String c: this.connections) {
					ResteasyClient client2 = new ResteasyClientBuilder().build();
					ResteasyWebTarget rtarget2 = client2.target("http://" + c + "/WarAT2020/rest/server");
					RestServerRemote rest2 = rtarget2.proxy(RestServerRemote.class);
					try {
						rest2.allRunningAgents(RunningAgents.getAgents().keySet(), RunningAgents.getAgents().values());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<String> newConnection(String connection) {
		System.out.println("New node registered: " + connection);
		for (String c : connections) {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget = client.target("http://" + c + "/WarAT2020/rest/connection");
			ConnectionManager rest = rtarget.proxy(ConnectionManager.class);
			rest.addConnection(connection);
			
		}
		connections.add(connection);
		return connections;
	}
	
	@Override
	public AgentCenter getNode() {
		return this.ac;
	}
	
	@Override
	public List<String> getConnections() {
		return this.connections;
	}
	
	public String getNodeName() {
		return this.ac.getAlias();
	}
	
	public String getNodeAddress() {
		return this.ac.getAddress();
	}

	@Override
	public void addConnection(String connection) {
		System.out.println("Adding connection... " + connection);
		connections.add(connection);

	}

}
