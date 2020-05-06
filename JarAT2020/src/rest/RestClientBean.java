package rest;

import javax.ejb.LocalBean;
import javax.ws.rs.Path;

@Path("/client")
@LocalBean
public class RestClientBean implements RestClientRemote {

	@Override
	public String getAgentClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRunningAgents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String startAgent(String agentClass, String agentName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String stopAgent(String agentAid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sendACLMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPerf() {
		// TODO Auto-generated method stub
		return null;
	}

}
