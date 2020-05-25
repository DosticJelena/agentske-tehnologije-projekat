package connectionmanager;

import java.util.List;

import javax.ejb.Remote;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Remote
public interface ConnectionManager {

	@POST
	@Path("/new")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	List<String> newConnection(String connection);
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	void addConnection(String connection);
	
	//void moveAgent(List<ObjectField> agent);
	
}
