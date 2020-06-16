package rest;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.Path;

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
	public String allRunningAgents(Set<AID> agents, Collection<AgentRemote> agentObjects) throws Exception {
		AID[] agentList = (AID[]) agents.toArray();
		AgentRemote[] agentObjectList = (AgentRemote[]) agentObjects.toArray();
		System.out.println("Setting running agents...");
		for (int i=0; i<agentList.length; i++) {
			RunningAgents.agents.put(agentList[i], agentObjectList[i]);
		}
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
