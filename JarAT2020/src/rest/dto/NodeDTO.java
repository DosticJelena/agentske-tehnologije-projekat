package rest.dto;

import java.util.List;

public class NodeDTO {

	private String node;
	private String master;
	private List<String> connections;
	
	public NodeDTO() {
		super();
	}

	public NodeDTO(String node, String master, List<String> connections) {
		super();
		this.node = node;
		this.master = master;
		this.connections = connections;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(List<String> connections) {
		this.connections = connections;
	}
	
	
}
