package rest;

import java.util.List;

import javax.ejb.EJB;
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
import agents.AID;
import agents.AgentBean;
import messagemanager.MessageManager;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	@EJB
	AgentManager agm;

	@EJB
	MessageManager msm;
	
	////////////////////////////////////////
	@POST
	@Path("/node")
	@Produces(MediaType.TEXT_PLAIN)
	public AID registerNewNode(AgentBean agClass, String runtimeName) {
		//return agm.startServerAgent(agClass, runtimeName);
		return null;
	}
	
	public String notifyAboutNewNode() {
		return "notifyAboutNewNode";
	}
	////////////////////////////////////////

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
		return agm.getAvailableAgentClasses();
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
