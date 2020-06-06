package rest;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agents.AgentBean;
import messagemanager.MessageManager;
import messagemanager.MessageManagerBean;
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
	
	@POST
	@Path("/node")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> registerNewNode(String node, String master, List<String> connections) {
		return agm().registerNewNode(node, master, connections);
	}
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public String newNodeAgentClasses() {
		return "newNodeAgentClasses";
	}

	////////////////////////////////////////
	@POST
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public List<AgentBean> allAgentClasses() {
		return agm().getAvailableAgentClasses();
	}
	
	public String notifyAboutNewAgentClasses() {
		return "notifyAboutNewAgentClasses";
	}
	////////////////////////////////////////

	@POST
	@Path("/nodes")
	@Produces(MediaType.TEXT_PLAIN)
	public String allNodes() {
		return "allNodes";
	}

	@POST
	@Path("/agents/running")
	@Produces(MediaType.TEXT_PLAIN)
	public String allRunningAgents() {
		return "allRunningAgents";
	}

	@DELETE
	@Path("/node/{alias}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String deleteDeadNode(@PathParam("alias") String nodeAlias) {
		return "deleteDeadNode" + " | " + nodeAlias;
	}

	@GET
	@Path("/node")
	@Produces(MediaType.TEXT_PLAIN)
	public String handshake() {
		return "handshake";
	}

}
