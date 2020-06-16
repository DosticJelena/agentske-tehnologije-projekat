package rest;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agentmanager.RunningAgents;
import agents.AID;
import agents.AgentRemote;
import agents.AgentType;
import connectionmanager.ConnectionManager;
import connectionmanager.ConnectionManagerBean;
import messagemanager.MessageManager;
import messagemanager.MessageManagerBean;
import util.JNDILookup;
import util.JSON;
import ws.WebSocketEndPoints;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	protected AgentManager agm() {
		return (AgentManager)JNDILookup.lookUp(JNDILookup.AgentManagerLookup, AgentManagerBean.class);
	}

	protected MessageManager msm() {
		return (MessageManager)JNDILookup.lookUp(JNDILookup.MessageManagerLookup, MessageManagerBean.class);
	}
	
	protected ConnectionManager cm() {
		return (ConnectionManager)JNDILookup.lookUp(JNDILookup.ConnectionManagerLookup, ConnectionManagerBean.class);
	}
	
	@EJB
	WebSocketEndPoints ws;
	
	
	public String newNodeAgentClasses() {
		return "newNodeAgentClasses";
	}

	////////////////////////////////////////
	public List<AgentType> allAgentClasses() {
		return agm().getAvailableAgentClasses();
	}
	
	public String notifyAboutNewAgentClasses() {
		return "notifyAboutNewAgentClasses";
	}
	////////////////////////////////////////
	
	public String allNodes() {
		return "allNodes";
	}
	
	@Override
	public void sendRunningAgents(String connection) {
		try {
			ResteasyClient client2 = new ResteasyClientBuilder().build();
			System.out.println("Salje zahtev...");
			ResteasyWebTarget rtarget2 = client2.target("http://" + connection + "/WarAT2020/rest/server");
			RestServerRemote rest2 = rtarget2.proxy(RestServerRemote.class);
		
			rest2.allRunningAgents(RunningAgents.getAgents().keySet(), RunningAgents.getAgents().values());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String allRunningAgents(Set<AID> agents, Collection<AgentRemote> agentObjects) throws Exception {		
		ws.sendMessage(JSON.om.writeValueAsString(agents));
		return "runningAgents";
	}

	public Response deleteDeadNode(String node) {
		try {
			cm().deleteConnection(node);
			System.out.println("deleted: " + node);
			return Response.status(200).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}

	public Response handshake() {
		System.out.println("alive");
		return Response.status(200).build();
	}

}
