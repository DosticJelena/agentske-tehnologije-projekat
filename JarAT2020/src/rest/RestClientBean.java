package rest;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.ejb.LocalBean;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import agentcenter.AgentCenter;
import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import agents.AID;
import agents.AgentBean;
import agents.AgentType;
import connectionmanager.ConnectionManager;
import connectionmanager.ConnectionManagerBean;
import messagemanager.ACLMessage;
import messagemanager.MessageManager;
import messagemanager.MessageManagerBean;
import nodes.NodeManager;
import util.JNDILookup;

@Path("/client")
@LocalBean
public class RestClientBean implements RestClientRemote {

	protected AgentManager agm() {
		return (AgentManager)JNDILookup.lookUp(JNDILookup.AgentManagerLookup, AgentManagerBean.class);
	}
	protected MessageManager msm() {
		return (MessageManager)JNDILookup.lookUp(JNDILookup.MessageManagerLookup, MessageManagerBean.class);
	}
	protected ConnectionManager cnm() {
		return (ConnectionManager)JNDILookup.lookUp(JNDILookup.ConnectionManagerLookup, ConnectionManagerBean.class);
	}
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AgentType> getAgentClasses() {
		return agm().getAvailableAgentClasses();
	}

	@GET
	@Path("/agents/running")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AID> getRunningAgents() {
		return agm().getRunningAgents();
	}

	@PUT
	@Path("/agents/running/{type}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public AID startAgent(@PathParam("type") String agentType, @PathParam("name") String agentName) {
		AgentType at = new AgentType();
		at.setModule("EarAT2020/JarAT2020");
		at.setType(agentType);
		return agm().startServerAgent(at, agentName);
	}

	@DELETE
	@Path("/agents/running")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AID stopAgent(AID agentAid) {
		agm().stopAgent(agentAid);
		return agentAid;
	}

	@POST
	@Path("/messages")
	@Consumes(MediaType.APPLICATION_JSON)
	public void sendACLMessage(ACLMessage msg) {
		msm().post(msg, 0);
	}

	@GET
	@Path("/messages")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPerf() {
		return msm().getPerformatives();
	}
	
	//dodatno
	
	@GET
	@Path("/host")
	@Produces(MediaType.APPLICATION_JSON)
	public AgentCenter getHost() {
		try {
			AgentCenter ac = new AgentCenter();
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			ObjectName http = new ObjectName("jboss.as:socket-binding-group=standard-sockets,socket-binding=http");
			
			ac.setAddress((String) mBeanServer.getAttribute(http, "boundAddress") + ":8080");
			ac.setAlias(NodeManager.getNodeName() + ":8080");
			return ac;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

}
