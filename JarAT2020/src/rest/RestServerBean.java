package rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ws.rs.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private WebSocketEndPoints ws;
	
	
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

	public String allRunningAgents(String agentss) {
		TypeReference<HashMap<AID,AgentRemote>> typeRef = new TypeReference<HashMap<AID,AgentRemote>>() {};
		try {
			//RunningAgents.agents = JSON.om.readValue(agentss, typeRef);
			HashMap<AID,AgentRemote> mapFromJson = (HashMap<AID,AgentRemote>)new ObjectMapper().readValue(agentss, HashMap.class);
			RunningAgents.agents = mapFromJson;
			ws.sendMessage(JSON.om.writeValueAsString(RunningAgents.getAgents()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "allRunningAgents";
	}

	public String deleteDeadNode(String nodeAlias) {
		return "deleteDeadNode" + " | " + nodeAlias;
	}

	public String handshake() {
		return "handshake";
	}

}
