package connectionmanager;

import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentcenter.AgentCenter;

@Remote
public interface ConnectionManager {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	void addConnection(String connection);
	
	@POST
	@Path("/new")
	@Consumes(MediaType.APPLICATION_JSON)
	public List<String> newConnection(String connection);
	
	@GET
	@Path("/host")
	@Produces(MediaType.APPLICATION_JSON)
	AgentCenter getNode();
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	List<String> getConnections();
	
}
