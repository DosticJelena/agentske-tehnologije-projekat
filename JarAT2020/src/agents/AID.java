package agents;

import java.io.Serializable;

import agentcenter.AgentCenter;

public class AID implements Serializable {

	private String name;
	private AgentType type;
	private AgentCenter host;
	
	public AID() {
		
	}
	
	public AID(String name, AgentType type, AgentCenter host) {
		super();
		this.name = name;
		this.type = type;
		this.host = host;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AgentType getType() {
		return type;
	}
	public void setType(AgentType type) {
		this.type = type;
	}
	public AgentCenter getHost() {
		return host;
	}
	public void setHost(AgentCenter host) {
		this.host = host;
	}
	
	
}
