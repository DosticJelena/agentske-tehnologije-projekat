package util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;

import agents.AgentRemote;
import agents.AgentType;

@Stateless
@LocalBean
public class AgentTypeLookup {

	private static final String INTF = "!" + AgentRemote.class.getName();
	private static final String EXP = "java:jboss/exported/";
	private Context context;
	
	@PostConstruct
	public void postConstruct() {
		context = ContextFactory.get(null);
	}
	
	public List<AgentType> parse() throws NamingException {
		List<AgentType> result = new ArrayList<>();
		NamingEnumeration<NameClassPair> moduleList = context.list(EXP);
		while (moduleList.hasMore()) {
			NameClassPair ncp = moduleList.next();
			String module = ncp.getName();
			processModule("", module, result);
		}
		return result;
	}
	
	private void processModule(String parentModule, String module, List<AgentType> result) throws NamingException {
		NamingEnumeration<NameClassPair> agentList;
		if (parentModule.equals("")) {
			agentList = context.list(EXP + "/" + module);
		} else {
			try {
				agentList = context.list(EXP + "/" + parentModule + "/" + module);
			} catch (NotContextException ex) {
				return;
			}
		}
		
		while (agentList.hasMore()) {
			NameClassPair ncp = agentList.next();
			String ejbName = ncp.getName();
			if (ejbName.contains("!")) {
				AgentType agClass = parseEjbNameIfValid(parentModule, module, ejbName);
				if (agClass != null) {
					result.add(agClass);
				}
			} else {
				processModule(module, ejbName, result);
			}
		}
	}
	
	private AgentType parseEjbNameIfValid(String parentModule, String module, String ejbName) {
		if (ejbName != null && ejbName.endsWith(INTF)) {
			return parseEjbName(parentModule, module, ejbName);
		}
		return null;
	}

	private AgentType parseEjbName(String parentModule, String module, String ejbName) {
		ejbName = extractAgentName(ejbName);
		if (parentModule.equals("")) {
			return new AgentType(module, ejbName);
		} else {
			return new AgentType(parentModule + "/" + module, ejbName);
		}
	}
	
	private String extractAgentName(String ejbName) {
		int n = ejbName.lastIndexOf(INTF);
		return ejbName.substring(0, n);
	}
}
