package agents;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;

@Stateful
@LocalBean
public class Searcher implements AgentRemote {

	private AID agentAid;  
	
	@Override
	public void init(AID aid) {
		this.agentAid = aid;;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AID getAgentAid() {
		return agentAid;
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
