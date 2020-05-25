package agentmanager;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import agents.AgentBean;

@Remote
public interface AgentManager extends Serializable {

//	public void startServerAgent(AID aid, AgentInitArgs args, boolean replace);
//
//	public AID startServerAgent(AgentBean agClass, @PathParam("name") String runtimeName);
//
//	public AID startClientAgent(AgentBean agClass, String runtimeName);
//
//	public void stopAgent(AID aid);
//
//	public List<AID> getRunningAgents();
//
//	public AID getAIDByRuntimeName(String runtimeName);

	public List<AgentBean> getAvailableAgentClasses();

//	public void pingAgent(AID aid);
//
//	public void reconstructAgent(List<ObjectField> agent);
//
//	public void move(AID aid, String host, List<ObjectField> agent);
	
}
