package rest;

import java.util.List;

import javax.ejb.Remote;

import agents.AID;
import agents.AgentType;
import messagemanager.ACLMessage;

@Remote
public interface RestClientRemote {

	public List<AgentType> getAgentClasses();
	public List<AID> getRunningAgents();
	public AID startAgent(String agentType, String agentName);
	public AID stopAgent(AID agentAid);
	public void sendACLMessage(ACLMessage msg);
	public List<String> getPerf();
	
}
