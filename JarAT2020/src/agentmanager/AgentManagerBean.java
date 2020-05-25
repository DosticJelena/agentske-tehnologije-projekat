package agentmanager;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import agents.AID;
import agents.AgentBean;
import agents.AgentRemote;
import connectionmanager.ConnectionManager;

@Stateless
@Remote(AgentManager.class)
@LocalBean
public class AgentManagerBean implements AgentManager {

	private static final long serialVersionUID = 1L;
	
	private Map<AID, AgentRemote> agents;
	
	@EJB
	ConnectionManager com;
	
	@Override
	public List<AgentBean> getAvailableAgentClasses() {
		//TODO: return jndiTreeParser.parse(); //lista agenata
		return null;
	}
	
	public AgentRemote getAgentReference(AID aid) {
		return agents.get(aid);
	}
	
}
