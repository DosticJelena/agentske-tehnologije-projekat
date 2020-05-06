package agents;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.jms.Message;

@Stateful
@LocalBean
public class AgentBean implements AgentRemote {

	private String agentAid;   //TODO AID 
	private AgentType agentType;
	
	@Override
	public String init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAgentAId() {
		return agentAid;
	}

}
