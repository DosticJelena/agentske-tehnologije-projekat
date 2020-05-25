package messagemanager;

import java.util.List;

public interface MessageManager {

	List<String> getPerformatives();

	void post(ACLMessage message);

	void post(ACLMessage message, long delayMillisec);

	String ping();
	
}
