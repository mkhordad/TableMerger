package main.java.files;
import java.io.File;
import java.io.IOException;
import org.apache.tika.Tika;


/**
 * @author mkhordad
 *  This class is responsible for detecting the type of the input file, 
 *  and returning the appropriate object. 
 */
public class ParserFactory {

	private final FileParser csvParser=new CsvParser();  
	private final FileParser htmlParser=new HTMLParser(); 
	/**
	 * Detects the file type and returns the appropriate parser for it
	 *
	 * @param Input file name
	 * @return True if there is no problem otherwise false
	 * @throws IOException 
	 */
	public FileParser Parse(String fileName)
	{
		
		String type = identifyFileType(fileName); 
		if (type==null)
			return null;
		switch (type){
		case "text/csv": 
			return  csvParser;
		case "text/html": 
			return htmlParser;
		default : 
			return null;
		}	
	}
	
	/**
	 * Detects the file type
	 *
	 * @param Input file name
	 * @return File type
	 * @throws IOException 
	 */
	private String identifyFileType(String fileName)
	{    
		File inputFile= new File(fileName);
		try {
			return new Tika().detect(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
