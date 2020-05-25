package agents;

import javax.ejb.Remote;

import messagemanager.ACLMessage;

@Remote
public interface AgentRemote {

	public String init();
	public void handleMessage(ACLMessage message);
	public AID getAgentAid();
	
}
