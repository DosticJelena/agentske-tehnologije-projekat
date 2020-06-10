package agents;

import javax.ejb.Remote;

import messagemanager.ACLMessage;

@Remote
public interface AgentRemote {

	public void init(AID aid);
	
	public void handleMessage(ACLMessage message);
	
	public AID getAgentAid();
	
	public void stop();
	
}
