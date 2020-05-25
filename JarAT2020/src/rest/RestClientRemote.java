package rest;

import java.util.List;

import javax.ejb.Remote;

import messagemanager.ACLMessage;

@Remote
public interface RestClientRemote {

	public String getAgentClasses();
	public String getRunningAgents();
	public String startAgent(String agentClass, String agentName);
	public String stopAgent(String agentAid);
	public void sendACLMessage(ACLMessage msg);
	public List<String> getPerf();
	
}
