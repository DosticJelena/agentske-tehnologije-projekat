package agentmanager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AgentInitArgs implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, String> args;

	public AgentInitArgs() {
		args = new HashMap<>();
	}
	
	public AgentInitArgs(String... keyValues) {
		args = new HashMap<>(keyValues.length);
		for (String str : keyValues) {
			String[] kv = str.split("=");
			args.put(kv[0], kv[1]);
		}
	}

	public String get(String key, String def) {
		return key != null ? key : def;
	}
}
