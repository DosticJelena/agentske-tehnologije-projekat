package rest;

import java.util.List;

import javax.ejb.Remote;

import agents.AID;
import agents.AgentBean;

@Remote
public interface RestServerRemote {

	public AID registerNewNode(AgentBean agClass, String runtimeName);
	public String newNodeAgentClasses();
	public String notifyAboutNewNode();
	public String notifyAboutNewAgentClasses();
	public String allNodes();
	public List<AgentBean> allAgentClasses();
	public String allRunningAgents();
	public String deleteDeadNode(String nodeAlias);
	public String handshake();
	
}
