package agentmanager;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import agents.AID;
import agents.AgentRemote;
import util.AIDSerializer;

@Singleton
@LocalBean
public class RunningAgents {

	@JsonSerialize(keyUsing = AIDSerializer.class)
	public static Map<AID, AgentRemote> agents = new HashMap<>();

	public static Map<AID, AgentRemote> getAgents() {
		return agents;
	}
	
	public static AgentRemote getAgent(AID aid) {
		return agents.get(aid);
	}
	
	public static void removeAgent(AID aid) {
		agents.remove(aid);
	}
	
	
	
}
