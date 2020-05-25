package agents;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;

@Stateful
@LocalBean
public class AgentBean implements AgentRemote {

	private AID agentAid;  
	
	@Override
	public String init() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AID getAgentAid() {
		return agentAid;
	}

}
