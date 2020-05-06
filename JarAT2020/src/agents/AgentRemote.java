package agents;

import javax.ejb.Remote;
import javax.jms.Message;

@Remote
public interface AgentRemote {

	public String init();
	public void handleMessage(Message message);
	public AID getAgentAId();
	
}
