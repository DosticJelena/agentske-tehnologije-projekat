package connectionmanager;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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

import agentcenter.AgentCenter;
import agentmanager.AgentManager;
import nodes.NodeManager;


@Singleton
@Startup
@Remote(ConnectionManager.class)
@Path("/connection")
public class ConnectionManagerBean implements ConnectionManager {

	private String nodeAddr;
	private String nodeName;
	private AgentCenter ac = new AgentCenter();
	private String master = null; // podesiti master adresu unutar init() metode
	private List<String> connections = new ArrayList<String>();

	@EJB
	private AgentManager agm;
	
	@PostConstruct
	private void init() {
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			
			this.nodeAddr = (String) mBeanServer.getAttribute(http, "boundAddress");
			this.ac.setAddress(this.nodeAddr + ":8080");
			this.nodeName = NodeManager.getNodeName() + ":8080";
			this.ac.setAlias(this.nodeName);
			
			this.master = "192.168.0.20:8080";

			System.out.println("MASTER ADDR: " + master + ", node (agent center) alias: " + this.ac.getAlias() + ", node (agent center) address: " + this.ac.getAddress());
			
			if (master != null && !master.equals("")) {
				ResteasyClient client = new ResteasyClientBuilder().build();
				ResteasyWebTarget rtarget = client.target("http://" + master + "/WarAT2020/rest/server");
				ConnectionManager rest = rtarget.proxy(ConnectionManager.class);
				this.connections = rest.newConnection(this.ac.getAlias());
				this.connections.remove(this.ac.getAlias());
				this.connections.add(this.master);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public AgentCenter getNode() {
		return this.ac;
	}
	
	public String getNodeName() {
		return this.nodeName;
	}
	
	public String getNodeAddress() {
		return this.nodeAddr;
	}
	
	@Override
	public List<String> newConnection(String connection) {
		System.out.println("New node/agent center registered: " + connection);
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
	public void addConnection(String connection) {
		connections.add(connection);

	}

}
