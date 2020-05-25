package messagemanager;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agentmanager.AgentManagerBean;
import agents.AID;
import agents.AgentRemote;

public class MDBConsumer implements MessageListener {

	@EJB
	private AgentManagerBean agm;

	@Override
	public void onMessage(Message msg) {
		try {
			processMessage(msg);
		} catch (JMSException ex) {
			System.out.println("Cannot process an incoming message.");
		}
	}

	private void processMessage(Message msg) throws JMSException {
		ACLMessage acl = (ACLMessage) ((ObjectMessage) msg).getObject();
		AID aid = getAid(msg, acl);
		deliverMessage(acl, aid);
	}

	private AID getAid(Message msg, ACLMessage acl) throws JMSException {
		int i = msg.getIntProperty("AIDIndex");
		//AID aid = (AID) msg.getObjectProperty("FullAID");
		return acl.receivers.get(i);
	}

	private void deliverMessage(ACLMessage msg, AID aid) {
		AgentRemote agent = agm.getAgentReference(aid);
		if (agent != null) {
			agent.handleMessage(msg);
		} else {
			System.out.println("No such agent: {" + aid.getName() + "}");
		}
	}
	
}
