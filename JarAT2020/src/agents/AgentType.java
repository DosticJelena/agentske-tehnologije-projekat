package agents;

public class AgentType {

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
	
	
	
}
