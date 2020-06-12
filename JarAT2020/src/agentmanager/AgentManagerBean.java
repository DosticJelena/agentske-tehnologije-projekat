package agentmanager;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.NamingException;

import com.fasterxml.jackson.core.JsonProcessingException;

import agentcenter.AgentCenter;
import agents.AID;
import agents.AgentRemote;
import agents.AgentType;
import nodes.NodeManager;
import util.AgentTypeLookup;
import util.JNDILookup;
import util.JSON;
import ws.WebSocketEndPoints;

@Stateless
@Remote(AgentManager.class)
@LocalBean
public class AgentManagerBean implements AgentManager {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private AgentTypeLookup agentTypeLookup;
	
	@EJB
	private WebSocketEndPoints ws;
	
	@Override
	public List<AID> getRunningAgents() {
		Set<AID> set = RunningAgents.getAgents().keySet();
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
				RunningAgents.getAgents().remove(aid);
			}
		}
		return new ArrayList<AID>(set);
	}
	
	@Override
	public void stopAgent(AID aid) {
		AgentRemote agent = RunningAgents.getAgent(aid);
		if (agent != null) {
			agent.stop();
			RunningAgents.removeAgent(aid);
			try {
				ws.sendMessage(JSON.om.writeValueAsString(RunningAgents.getAgents().keySet()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public AID startServerAgent(AgentType type, String name) {
		try {
			AgentCenter host = new AgentCenter();
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http;
			http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			
			host.setAddress((String) mBeanServer.getAttribute(http, "boundAddress") + ":8080");
			host.setAlias(NodeManager.getNodeName() + ":8080");
			
			AID aid = new AID(name, type, host);
			AgentRemote agent = null;

			try {
				String path = getAgentLookup(aid.getType(), true);
				agent = JNDILookup.lookUpWithAgentCenter(path, AgentRemote.class, null);
			} catch (IllegalStateException ex) {
				String path = getAgentLookup(aid.getType(), true);
				agent = JNDILookup.lookUpWithAgentCenter(path, AgentRemote.class, null);
			}

			RunningAgents.getAgents().put(aid, agent);
			agent.init(aid);
			
			ws.sendMessage(JSON.om.writeValueAsString(RunningAgents.getAgents().keySet()));
			
			return aid;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
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
	
	public AgentRemote getAgentReference(AID aid) {
		return RunningAgents.getAgent(aid);
	}
	
}
