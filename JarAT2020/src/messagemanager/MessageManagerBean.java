package messagemanager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import agents.AID;

@Stateless
@Remote(MessageManager.class)
@LocalBean
public class MessageManagerBean implements MessageManager {

	@EJB
	private JMSFactory factory;

	private Session session;
	private MessageProducer defaultProducer;

	@PostConstruct
	public void postConstruct() {
		session = factory.getSession();
		defaultProducer = factory.getDefaultProducer(session);
	}

	@PreDestroy
	public void preDestroy() {
		try {
			session.close();
		} catch (JMSException e) {
		}
	}
	
	@Override
	public List<String> getPerformatives() {
		final Performative[] arr = Performative.values();
		List<String> list = new ArrayList<>(arr.length);
		for (Performative p : arr)
			list.add(p.toString());
		return list;
	}

	@Override
	public void post(ACLMessage message) {
		post(message, 0L);
	}

	@Override
	public void post(ACLMessage message, long delayMillisec) {
		for (int i = 0; i < message.receivers.size(); i++) {
			if (message.receivers.get(i) == null) {
				throw new IllegalArgumentException("AID cannot be null.");
			}
			postToReceiver(message, i);
		}
	}
	
	private void postToReceiver(ACLMessage msg, int index) {
		AID aid = msg.receivers.get(index);
		try {
			ObjectMessage jmsMsg = session.createObjectMessage(msg);
			jmsMsg.setObjectProperty("FullAID", aid);
			jmsMsg.setIntProperty("AIDIndex", index);
			defaultProducer.send(jmsMsg);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public String ping() {
		return "Pong from " + System.getProperty("jboss.node.name");
	}

}
