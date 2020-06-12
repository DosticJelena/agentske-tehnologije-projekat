package ws;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@Singleton
@LocalBean
@ServerEndpoint(value="/ws/{username}")
public class WebSocketEndPoints {

	private static Map<String, Session> sessions = new HashMap<String, Session>();
	private static Map<String, String> sessionToUser = new HashMap<String, String>();
	
	@OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
    	sessions.put(username, session);
    	sessionToUser.put(session.getId(), username);
    }
	
	@OnMessage
    public void onMessage(String message) {
    	
    }
	
	public void updateRunningAgents(String users) {
//    	for (Session session: sessions.values()) {
//    		try {
//				session.getBasicRemote().sendText(users);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//    	}
    }
	
	@OnClose
	public void close(Session session) throws IOException {
		String username = sessionToUser.get(session.getId());
		sessions.remove(username);
		session.close();
	}
	
	@OnError
	public void error(Session session, Throwable t) throws IOException {
		String username = sessionToUser.get(session.getId());
		sessions.remove(username);
		session.close();
		t.printStackTrace();
	}
	
}
