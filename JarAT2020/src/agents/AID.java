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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AID other = (AID) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AID [name=" + name + ", type=" + type + ", host=" + host + "]";
	}
	
	
	
}
