package rest;

import javax.ejb.Remote;

@Remote
public interface RestServerRemote {

	public String registerNewNode();
	public String newNodeAgentClasses();
	public String notifyAboutNewNode();
	public String notifyAboutNewAgentClasses();
	public String allNodes();
	public String allAgentClasses();
	public String allRunningAgents();
	public String deleteDeadNode(String nodeAlias);
	public String handshake();
	
}
