package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/client")
@LocalBean
public class RestClientBean implements RestClientRemote {

	@GET
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAgentClasses() {
		return "getAgentClasses";
	}

	@GET
	@Path("/agents/running")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRunningAgents() {
		return "getRunningAgents";
	}

	@PUT
	@Path("/agents/running/{type}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String startAgent(@PathParam("type") String agentClass, String agentName) {
		return "startAgent" + " | " + agentClass + " | " + agentName;
	}

	@DELETE
	@Path("/agents/running/{aid}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String stopAgent(@PathParam("aid") String agentAid) {
		return "stopAgent" + " | " + agentAid;
	}

	@POST
	@Path("/messages")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendACLMessage() {
		return "sendACLMessage";
	}

	@GET
	@Path("/messages")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPerf() {
		return "getPerf";
	}

}
