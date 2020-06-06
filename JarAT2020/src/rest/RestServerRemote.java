package rest;

import java.util.List;

import javax.ejb.Remote;

import agents.AgentBean;

@Remote
public interface RestServerRemote {

	public List<String> registerNewNode(String node, String master, List<String> connections);
	public String newNodeAgentClasses();
	public String notifyAboutNewAgentClasses();
	public String allNodes();
	public List<AgentBean> allAgentClasses();
	public String allRunningAgents();
	public String deleteDeadNode(String nodeAlias);
	public String handshake();
	
}
