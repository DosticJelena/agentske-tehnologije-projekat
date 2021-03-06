package messagemanager;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.NoSuchEJBException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import agentmanager.AgentManagerBean;
import agents.AID;
import agents.AgentRemote;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/mojQueue")
})
public class MDBConsumer implements MessageListener {

	@EJB
	private AgentManagerBean agm;

	@Override
	public void onMessage(Message msg) {
		try {
			processMessage(msg);
		} catch (JMSException ex) {
			System.out.println("Cannot process an incoming message.");
		} catch (NoSuchEJBException nEx) {
			System.out.println("*** No such EJB Exception *** (...)");
			nEx.printStackTrace();
		}
	}

	private void processMessage(Message msg) throws JMSException {
		ACLMessage acl = (ACLMessage) ((ObjectMessage) msg).getObject();
		AID aid = getAid(msg, acl);
		deliverMessage(acl, aid);
	}

	private AID getAid(Message msg, ACLMessage acl) throws JMSException {
		int i = msg.getIntProperty("AIDIndex");
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
