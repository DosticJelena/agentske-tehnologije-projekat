package connectionmanager;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import agentcenter.AgentCenter;

@Remote
public interface ConnectionManager {

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	void addConnection(String connection);
	
	AgentCenter getNode();
	
}
