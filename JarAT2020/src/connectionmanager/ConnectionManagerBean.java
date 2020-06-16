package connectionmanager;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentcenter.AgentCenter;
import agentmanager.AgentManager;
import javafx.scene.chart.PieChart.Data;
import nodes.NodeManager;
import rest.RestServerRemote;
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
				
//				ResteasyClient client2 = new ResteasyClientBuilder().build();
//				ResteasyWebTarget rtarget2 = client2.target("http://" + master + "/WarAT2020/rest/server");
//				RestServerRemote rest2 = rtarget2.proxy(RestServerRemote.class);
//				rest2.sendRunningAgents(this.ac.getAddress());
					
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
	
	@Override
	public void deleteConnection(String connection) {
		System.out.println("Removing connection... " + connection);
		connections.remove(connection);
		System.out.println("Removed");
	}
	
	@Schedules({
		@Schedule(hour="*", minute="*", second="*/20", info="healthcheck")
	})
	public void healthcheck() {
		System.out.println("healthcheck [" + this.connections.size() + "]");
		
		for(String connection : this.connections) {
			if(!connection.equals(this.ac.getAddress())) {
				ResteasyClient rc = new ResteasyClientBuilder().build();			
				String path = "http://" + connection + "/WarAT2020/rest/server";
				ResteasyWebTarget rwt = rc.target(path);
				Response response = rwt.request(MediaType.APPLICATION_JSON).get();
				
				if(response.getStatus() != 200) {
					Response response2 = rwt.request(MediaType.APPLICATION_JSON).get();
					if(response2.getStatus() != 200) {
						connections.remove(connection);
						for(String c : connections) {
							if(!c.equals(connection) && !c.equals(this.ac.getAddress())) {
								ResteasyClient rc2 = new ResteasyClientBuilder().build();			
								String path2 = "http://" + c + ":8080/ChatWAR/rest/server/node/" + connection;
								ResteasyWebTarget rwt2 = rc2.target(path2);
								Response response3 = rwt2.request(MediaType.APPLICATION_JSON).delete();
								System.out.println("Status [deleted]: " + response3.getStatus());
							}
						}
					}
				}
			}
		}
	}

}
