package rest;

import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.Path;

import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agents.AID;
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

	public String allRunningAgents(Set<AID> agents) throws Exception {
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
