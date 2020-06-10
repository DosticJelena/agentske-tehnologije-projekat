package util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import agentcenter.AgentCenter;
import agentmanager.AgentManager;
import agentmanager.AgentManagerBean;
import connectionmanager.ConnectionManager;
import connectionmanager.ConnectionManagerBean;
import messagemanager.MessageManager;
import messagemanager.MessageManagerBean;

public class JNDILookup {

	public static final String JNDIPathChat = "ejb:EarAT2020/JarAT2020/";
	public static final String AgentManagerLookup = JNDIPathChat + AgentManagerBean.class.getSimpleName() + "!"
			+ AgentManager.class.getName();
	public static final String MessageManagerLookup = JNDIPathChat + MessageManagerBean.class.getSimpleName() + "!"
			+ MessageManager.class.getName();
	public static final String ConnectionManagerLookup = JNDIPathChat + ConnectionManagerBean.class.getSimpleName() + "!"
			+ ConnectionManager.class.getName();

	private static Context createInitialContext() throws NamingException {
        Properties jndiProperties = new Properties();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, 
          "org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(Context.URL_PKG_PREFIXES, 
          "org.jboss.ejb.client.naming");
        jndiProperties.put(Context.PROVIDER_URL, 
           "http-remoting://192.168.0.20:8080");
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        return new InitialContext(jndiProperties);
    }
	
	public static <T> T lookUp(String name, Class<T> c) {
		
		T bean = null;
		try {
			Context context = createInitialContext();
			
			System.out.println("Looking up: " + name);
			bean = (T) context.lookup(name);
			
			context.close();

		} catch (NamingException e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T lookUpWithAgentCenter(String name, Class<T> c, AgentCenter remote) {
		try {
			return (T) ContextFactory.get(remote).lookup(name);
		} catch (NamingException ex) {
			throw new IllegalStateException("Failed to lookup " + name, ex);
		}
	}
}