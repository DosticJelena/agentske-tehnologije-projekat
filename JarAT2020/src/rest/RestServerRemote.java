package rest;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import agents.AID;
import agents.AgentRemote;
import agents.AgentType;

@Remote
public interface RestServerRemote {


//	@POST
//	@Path("/node")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public List<String> registerNewNode(NodeDTO dto);
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public String newNodeAgentClasses();
	
	@POST
	@Path("/nodes")
	@Produces(MediaType.TEXT_PLAIN)
	public String allNodes();
	
	@POST
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public List<AgentType> allAgentClasses();

	@POST
	@Path("/agents/send")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendRunningAgents(String connection);
	
	@POST
	@Path("/agents/running")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String allRunningAgents(Set<AID> agnents, Collection<AgentRemote> agentObjects) throws Exception;
	
	@DELETE
	@Path("/node/{alias}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response deleteDeadNode(@PathParam("alias") String nodeAlias);
	
	@GET
	@Path("/node")
	public Response handshake();
	
}
