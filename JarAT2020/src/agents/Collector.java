package agents;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import messagemanager.ACLMessage;

@Stateful
@LocalBean
public class Collector implements AgentRemote {

	private AID agentAid; 

	private static final int MAX_PAGES_TO_SEARCH = 10;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
	
	private static final String USER_AGENT =
	            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>(); 
	private Document htmlDocument; 
  
	@Override
	public void init(AID aid) {
		this.agentAid = aid;;
	}

	@Override
	public void handleMessage(ACLMessage message) {
		System.out.println("Collector agent [" + agentAid.getName() + "] primio poruku");
		search(message.content, "nekretnine", message.sender.getName());
		
	}

	private String nextUrl()
    {
        String nextUrl;
        do
        {
            nextUrl = this.pagesToVisit.remove(0);
        } while(this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
	
	public void search(String url, String searchWord, String senderName)
    {
		System.out.println("Pretrazujem sajt: " + url);
        while(this.pagesVisited.size() < MAX_PAGES_TO_SEARCH)
        {
            String currentUrl;
            if(this.pagesToVisit.isEmpty())
            {
                currentUrl = url;
                this.pagesVisited.add(url);
            }
            else
            {
                currentUrl = nextUrl();
            }
            crawl(currentUrl, senderName); 
                                   
            boolean success = searchForWord(searchWord);
            if(success)
            {
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }
            this.pagesToVisit.addAll(getLinks());
        }
        System.out.println(String.format("**Done** Visited %s web page(s)", this.pagesVisited.size()));
    }
	
    public boolean crawl(String url, String senderName) { 
    	try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            
            String filePath = System.getProperty("user.home") + "\\Documents" + "\\" + senderName + "_AT_2020.txt";
            
            File f = new File(filePath);
            if(!f.exists()){
              f.createNewFile();
              System.out.println("Kreiram fajl...");
            }else{
              System.out.println("Pisem u postojeci fajl...");
            }
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write("----------\n\n" + new Date().toString() + "\n" + "[" + url + "]\n\n");
             
            writer.close();
            
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) 
			{
            	System.out.println("\n**Visiting** Received web page at " + url);
			}
			if(!connection.response().contentType().contains("text/html"))
			{
				System.out.println("**Failure** Retrieved something other than HTML");
				return false;
			}
            Elements linksOnPage = htmlDocument.select("div.row");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
            System.out.println("Error in out HTTP request " + ioe);
            return false;
        }
    }
    
    public boolean searchForWord(String searchWord) { 
	    if(this.htmlDocument == null)
        {
             System.out.println("ERROR! Call crawl() before performing analysis on the document");
             return false;
        }
    	System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }
    
    public List<String> getLinks() 
    {
      return this.links;
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
