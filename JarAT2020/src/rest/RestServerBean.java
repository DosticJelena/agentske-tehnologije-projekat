package rest;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.Path;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agentmanager.RunningAgents;
import agents.AID;
import agents.AgentRemote;
import agents.AgentType;
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
	public void sendRunningAgents(List<String> connections) {
		for(String c: connections) {
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

	@Override
	public String allRunningAgents(Set<AID> agents, Collection<AgentRemote> agentObjects) throws Exception {		
		ws.sendMessage(JSON.om.writeValueAsString(agents));
		return "runningAgents";
	}

	public String deleteDeadNode(String nodeAlias) {
		return "deleteDeadNode" + " | " + nodeAlias;
	}

	public String handshake() {
		return "handshake";
	}

}
