package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Path;

@Path("/server")
@LocalBean
public class RestServerBean implements RestServerRemote {

	@Override
	public String registerNewNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String newNodeAgentClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notifyAboutNewNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notifyAboutNewAgentClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String allNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String allAgentClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String allRunningAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteDeadNode(String nodeAlias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String handshake() {
		// TODO Auto-generated method stub
		return null;
	}

}
