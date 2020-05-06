package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	@POST
	@Path("/node1")
	@Produces(MediaType.TEXT_PLAIN)
	public String registerNewNode() {
		return "registerNewNode";
	}

	@GET
	@Path("/agents/classes1")
	@Produces(MediaType.TEXT_PLAIN)
	public String newNodeAgentClasses() {
		return "newNodeAgentClasses";
	}

	//ponovljena - spojiti
	@POST
	@Path("/node2")
	@Produces(MediaType.TEXT_PLAIN)
	public String notifyAboutNewNode() {
		return "notifyAboutNewNode";
	}

	//ponovljena - spojiti
	@POST
	@Path("/agents/classes2")
	@Produces(MediaType.TEXT_PLAIN)
	public String notifyAboutNewAgentClasses() {
		return "notifyAboutNewAgentClasses";
	}

	@POST
	@Path("/nodes")
	@Produces(MediaType.TEXT_PLAIN)
	public String allNodes() {
		return "allNodes";
	}

	//ponovljena - spojiti
	@POST
	@Path("/agents/classes3")
	@Produces(MediaType.TEXT_PLAIN)
	public String allAgentClasses() {
		return "allAgentClasses";
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
