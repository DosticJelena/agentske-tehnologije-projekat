package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/client")
@LocalBean
public class RestClientBean implements RestClientRemote {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getAgentClasses() {
		return "getAgentClasses";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getRunningAgents() {
		return "getRunningAgents";
	}

	@PUT
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String startAgent(String agentClass, String agentName) {
		return "startAgent";
	}

	@DELETE
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String stopAgent(String agentAid) {
		return "stopAgent";
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public String sendACLMessage() {
		return "sendACLMessage";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getPerf() {
		return "getPerf";
	}

}
