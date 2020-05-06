package agents;

import javax.ejb.Remote;
import javax.jms.Message;

@Remote
public interface Agent {

	public String init();
	public void handleMessage(Message message);
	public String getAgentId();
	
}
