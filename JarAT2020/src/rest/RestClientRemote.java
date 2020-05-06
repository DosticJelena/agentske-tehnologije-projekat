package rest;

import javax.ejb.Remote;

@Remote
public interface RestClientRemote {

	public String getAgentClasses();
	public String getRunningAgents();
	public String startAgent(String agentClass, String agentName);
	public String stopAgent(String agentAid);
	public String sendACLMessage();
	public String getPerf();
	
}
