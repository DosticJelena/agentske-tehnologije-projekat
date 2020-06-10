package agentmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import agentcenter.AgentCenter;
import agents.AID;
import agents.AgentRemote;
import agents.AgentType;
import util.AgentTypeLookup;
import util.JNDILookup;

@Stateless
@Remote(AgentManager.class)
@LocalBean
public class AgentManagerBean implements AgentManager {

	private static final long serialVersionUID = 1L;
	
	private Map<AID, AgentRemote> agents = new HashMap<>();
	
	@EJB
	private AgentTypeLookup agentTypeLookup;
	
	@Override
	public List<AID> getRunningAgents() {
		Set<AID> set = agents.keySet();
		if (set.size() > 0) {
			AID aid = set.iterator().next();
			try {
				try {
					JNDILookup.lookUpWithAgentCenter(getAgentLookup(aid.getType(), true), AgentRemote.class, null);
				} catch (Exception ex) {
					JNDILookup.lookUpWithAgentCenter(getAgentLookup(aid.getType(), false), AgentRemote.class, null);
				}
			} catch (Exception ex) {
				set.remove(aid);
				agents.remove(aid);
			}
		}
		return new ArrayList<AID>(set);
	}
	
	@Override
	public void stopAgent(AID aid) {
		AgentRemote agent = agents.get(aid);
		if (agent != null) {
			agent.stop();
			agents.remove(aid);
		}
	}
	
	@Override
	public AID startServerAgent(AgentType type, String name) {
		AgentCenter host = new AgentCenter("test","192.168.0.20");

		AID aid = new AID(name, type, host);
		AgentRemote agent = null;

		try {
			String path = getAgentLookup(aid.getType(), true);
			agent = JNDILookup.lookUpWithAgentCenter(path, AgentRemote.class, null);
		} catch (IllegalStateException ex) {
			String path = getAgentLookup(aid.getType(), true);
			agent = JNDILookup.lookUpWithAgentCenter(path, AgentRemote.class, null);
		}

		agents.put(aid, agent);
		agent.init(aid);
		
		return aid;
	}
	
	@Override
	public List<AgentType> getAvailableAgentClasses() {
		try {
			return agentTypeLookup.parse();
		} catch (NamingException ex) {
			throw new IllegalStateException(ex);
		}
	}
	
	
	/*
	 *  --- pomocne metode ---
	 */
	
	private String getAgentLookup(AgentType agentType, boolean stateful) {
		if (agentType.getModule().contains("/")) {
			// in ear file
			if (stateful)
				return String.format("ejb:%s//%s!%s?stateful", agentType.getModule(),
						agentType.getType(), AgentRemote.class.getName());
			else
				return String.format("ejb:%s//%s!%s", agentType.getModule(), agentType.getType(),
						AgentRemote.class.getName());
		} else {
			// in jar file
			if (stateful)
				return String.format("ejb:/%s//%s!%s?stateful", agentType.getModule(),
						agentType.getType(), AgentRemote.class.getName());
			else
				return String.format("ejb:/%s//%s!%s", agentType.getModule(), agentType.getType(),
						AgentRemote.class.getName());
		}
	}
	
//	private AgentRemote getAgentReference(AID aid) {
//		
//	}
	
}
