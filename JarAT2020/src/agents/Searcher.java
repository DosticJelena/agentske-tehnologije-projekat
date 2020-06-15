package agents;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import messagemanager.ACLMessage;
import ws.WebSocketResults;

@Stateful
@LocalBean
public class Searcher implements AgentRemote {

	private AID agentAid;
	
	@EJB
	private WebSocketResults wsResults;

	
	@Override
	public void init(AID aid) {
		this.agentAid = aid;;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println("Searcher agent [" + agentAid.getName() + "] primio poruku");
		String filePath = System.getProperty("user.home") + "\\Documents" + "\\" + message.sender.getName() + "_AT_2020.txt";
		String fileContent = readFromFile(filePath);
		System.out.println(fileContent);
		try {
			wsResults.sendMessage(fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String readFromFile(String path) {
		String fileContent = "";
		try {
		      File myObj = new File(path);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        fileContent += "\n" + data;
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return fileContent;
	}

	@Override
	public AID getAgentAid() {
		return agentAid;
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
	}

}
