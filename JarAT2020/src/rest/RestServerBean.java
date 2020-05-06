package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String registerNewNode() {
		return "registerNewNode";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String newNodeAgentClasses() {
		return "newNodeAgentClasses";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String notifyAboutNewNode() {
		return "notifyAboutNewNode";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String notifyAboutNewAgentClasses() {
		return "notifyAboutNewAgentClasses";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String allNodes() {
		return "allNodes";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String allAgentClasses() {
		return "allAgentClasses";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String allRunningAgents() {
		return "allRunningAgents";
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String deleteDeadNode(String nodeAlias) {
		return "deleteDeadNode";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String handshake() {
		return "handshake";
	}

}
