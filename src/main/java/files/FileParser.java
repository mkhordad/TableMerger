package main.java.files;
import java.util.List;

public abstract class FileParser {
	String [] headers ;
	List<String[]> lines;
	
	public FileParser()
	{
		headers= null;
		lines= null;
	}
	
	/**
	 * Reads the input file and extracts its headers and lines.
	 *
	 * @param Input file name
	 * @return True if there is no problem otherwise false
	 */
	public abstract boolean readFromfile(String fileName) ;
	
	public List<String []> getLines()
	{
		return lines;
	}
	public String [] getHeaders()
	{
		return headers;
	}
}
