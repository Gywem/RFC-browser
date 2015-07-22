package project3OOPT.persistance.dao;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import project3OOPT.persistance.model.Rfc;

/*
 * RfcDAO
 * Class responsible of control the access to the RFCs
 * */
public class RfcDAO {
	private static RfcDAO Singleton;
	
	public static RfcDAO getInstance(){
		if(Singleton == null) return new RfcDAO();
		else return Singleton;
	}
	
	private RfcDAO() {
		this.cacheRFCs = new HashMap<Integer, Rfc>();
		this.cacheSearchRFCs = new HashMap<String, List<Rfc>>();
	}
	
	private Map<Integer, Rfc> cacheRFCs;
	private Map<String, List<Rfc>> cacheSearchRFCs;
	
	public Rfc read(int number) {
		if(cacheRFCs.containsKey(new Integer(number))) {
			return cacheRFCs.get(new Integer(number));
		}
		try {
			URL url = new URL("http://www.ietf.org/rfc/rfc"+number+".txt");
			Scanner s = new Scanner(url.openStream());
			
			String result = "";
			while(s.hasNext()) {
				result += s.nextLine() + "<br/>";
			}
			s.close();
			
			return new Rfc(number, result);
		} catch (IOException e) {			
			return null;
		}		
	}
	
	public List<Rfc> readByName(String name) {
		Document doc;
		Elements headlinesRfcs;
		List<Rfc> result = new ArrayList<Rfc>();
		
		if(cacheSearchRFCs.containsKey(name)) return cacheSearchRFCs.get(name);
		try {
			doc = Jsoup.connect("http://www.rfc-editor.org/search/rfc_search_detail.php?title="+name+"&pubstatus%5B%5D=Any&pub_date_type=any").get();

			headlinesRfcs = doc.select(".gridtable tbody tr td:first-child, .gridtable tbody tr .title, .gridtable tbody tr .special");
			
			Iterator<Element> it = headlinesRfcs.iterator();
			
			while(it.hasNext()) {
				Element eRfc = it.next();
				
				String firstNumber = eRfc.html().replaceFirst(".*?(\\d+).*", "$1");
				
				int number = Integer.parseInt(firstNumber);
				
				Rfc rfc = new Rfc(number, "");				

				Element eTitle = it.next();
				String title = eTitle.html();
				
				if(rfc != null) {
					rfc.setTitle(title);
					
					result.add(rfc);
				}
			}
			
			cacheSearchRFCs.put(name, result);
			
			return result;			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
