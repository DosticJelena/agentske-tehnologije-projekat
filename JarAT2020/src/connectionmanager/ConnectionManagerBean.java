package connectionmanager;

import java.lang.management.ManagementFactory;
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
import rest.RestServerRemote;



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
				ResteasyWebTarget rtarget = client.target("http://" + master + "/WarAT2020/rest/server");
				RestServerRemote rest = rtarget.proxy(RestServerRemote.class);
				this.connections = rest.registerNewNode(this.ac.getAddress(), this.master, this.connections);
				this.connections.remove(this.ac.getAddress());
				this.connections.add(this.master);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public AgentCenter getNode() {
		return this.ac;
	}
	
	public String getNodeName() {
		return this.ac.getAlias();
	}
	
	public String getNodeAddress() {
		return this.ac.getAddress();
	}

	@Override
	public void addConnection(String connection) {
		connections.add(connection);

	}

}
