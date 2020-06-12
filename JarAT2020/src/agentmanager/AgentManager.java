package agentmanager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import agents.AID;
import agents.AgentRemote;
import agents.AgentType;

@Remote
public interface AgentManager extends Serializable {

	public AID startServerAgent(AgentType type, String runtimeName);

	public void stopAgent(AID aid);

	public List<AID> getRunningAgents();
	
	public AgentRemote getAgentReference(AID aid);

//	public AID getAIDByRuntimeName(String runtimeName);

	public List<AgentType> getAvailableAgentClasses();

//	public void pingAgent(AID aid);
//
//	public void reconstructAgent(List<ObjectField> agent);
//
//	public void move(AID aid, String host, List<ObjectField> agent);
	
}
