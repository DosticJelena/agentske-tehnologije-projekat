package agentmanager;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agents.AID;
import agents.AgentBean;
import agents.AgentRemote;
import connectionmanager.ConnectionManager;
import nodes.NodeManager;

@Stateless
@Remote(AgentManager.class)
@LocalBean
public class AgentManagerBean implements AgentManager {

	private static final long serialVersionUID = 1L;
	
	private Map<AID, AgentRemote> agents;
	
	@Override
	public List<String> registerNewNode(String node, String master, List<String> connections) {
		System.out.println("New node/agent center registered: " + node);
		
		for (String c : connections) {
			ResteasyClient client = new ResteasyClientBuilder().build();
			ResteasyWebTarget rtarget = client.target("http://" + c + "/WarAT2020/rest/connection");
			ConnectionManager rest = rtarget.proxy(ConnectionManager.class);
			rest.addConnection(node);
		}
		
		connections.add(node);
		
		//TODO: GET...POST/agent/classes -> master predaje novom tipove agenata, i ostalim daje njegove tipove
		//TODO: POST/nodes -> dostavlja spisak ostalih servera novom serveru
		
		
		return connections;
	}
	
	@Override
	public List<AgentBean> getAvailableAgentClasses() {
		//TODO: return jndiTreeParser.parse(); //lista agenata
		return null;
	}
	
	public AgentRemote getAgentReference(AID aid) {
		return agents.get(aid);
	}
	
}
