package rest;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.ejb.LocalBean;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
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

	protected MessageManager msm() {
		return (MessageManager)JNDILookup.lookUp(JNDILookup.MessageManagerLookup, MessageManagerBean.class);
	}
	protected ConnectionManager cnm() {
		return (ConnectionManager)JNDILookup.lookUp(JNDILookup.ConnectionManagerLookup, ConnectionManagerBean.class);
	}
	
	@GET
	@Path("/agents/classes")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAgentClasses() {
		return "getAgentClasses";
	}

	@GET
	@Path("/agents/running")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRunningAgents() {
		return "getRunningAgents";
	}

	@PUT
	@Path("/agents/running/{type}/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String startAgent(@PathParam("type") String agentClass, String agentName) {
		return "startAgent" + " | " + agentClass + " | " + agentName;
	}

	@DELETE
	@Path("/agents/running/{aid}")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public String stopAgent(@PathParam("aid") String agentAid) {
		return "stopAgent" + " | " + agentAid;
	}

	@POST
	@Path("/messages")
	@Produces(MediaType.TEXT_PLAIN)
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
