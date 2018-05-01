package main.java.files;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLParser extends FileParser{
	
	@Override
	/**
	 * Reads a html file and extracts its headers and lines.
	 *
	 * @param Input file name
	 * @return True if there is no problem otherwise false
	 */
	public boolean readFromfile(String fileName) {
		File input = new File(fileName);
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			lines = new ArrayList<String[]>();
			
			for (Element table : doc.select("table")) {
	            for (Element row : table.select("tr")) {
	            	Elements ths = row.select("th");
	            	
	            	//if this is the header row get the headers
	            	if (ths.size()>0)
	            	{
	            		headers= new String [ths.size()];
		                for(int i=0; i<ths.size(); i++)
		                {
		                	headers[i] = ths.get(i).text();
		                }
	            	}
	                
	                Elements tds = row.select("td");
	                //if this is the data row get the line
	                if(tds.size()>0)
	                {
	                	String [] line= new String [tds.size()];
	                	for(int i=0; i<tds.size(); i++)
		                {
	                		line[i] = tds.get(i).text();
		                }
	                	lines.add(line);
	                }
	            }
	        }
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(lines.size()>0)
			return true;
		return false;
	}
		

}
