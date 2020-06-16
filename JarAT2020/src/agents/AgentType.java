package agents;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonValue;

public class AgentType implements Serializable {

	private String type;
	private String module;
	
	public AgentType() {
		
	}
	
	public AgentType(String type, String module) {
		super();
		this.type = type;
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
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
		AgentType other = (AgentType) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "{\"type\": \"" + type + "\", \"module\": \"" + module + "\"}";
//	}
	
	
	
}
