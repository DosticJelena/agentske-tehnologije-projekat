package rest;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ws.rs.Path;

import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agents.AgentBean;
import messagemanager.MessageManager;
import messagemanager.MessageManagerBean;
import rest.dto.NodeDTO;
import util.JNDILookup;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	protected AgentManager agm() {
		return (AgentManager)JNDILookup.lookUp(JNDILookup.AgentManagerLookup, AgentManagerBean.class);
	}

	protected MessageManager msm() {
		return (MessageManager)JNDILookup.lookUp(JNDILookup.MessageManagerLookup, MessageManagerBean.class);
	}
	
	public List<String> registerNewNode(NodeDTO dto) {
		return agm().registerNewNode(dto.getNode(), dto.getMaster(), dto.getConnections());
	}
	
	public String newNodeAgentClasses() {
		return "newNodeAgentClasses";
	}

	////////////////////////////////////////
	public List<AgentBean> allAgentClasses() {
		return agm().getAvailableAgentClasses();
	}
	
	public String notifyAboutNewAgentClasses() {
		return "notifyAboutNewAgentClasses";
	}
	////////////////////////////////////////
	
	public String allNodes() {
		return "allNodes";
	}

	public String allRunningAgents() {
		return "allRunningAgents";
	}

	public String deleteDeadNode(String nodeAlias) {
		return "deleteDeadNode" + " | " + nodeAlias;
	}

	public String handshake() {
		return "handshake";
	}

}
