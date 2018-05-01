package test.java.files;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.files.FileParser;
import main.java.files.HTMLParser;

public class HTMLParserTest {

	final String HERE = Paths.get(".").toAbsolutePath().normalize().toString();
	final String HTML_FILE = Paths.get(HERE, "src/test/java/files/data.html").toString();
	final String HTML_FILE_NO_TABLE = Paths.get(HERE, "src/test/java/files/data2.html").toString();
	private FileParser htmlParser;  
	
	@Before 
	public void initialize() {
		htmlParser=new HTMLParser();
	}
	
	@Test
	public void testReadFromfile() {
		assertTrue(htmlParser.readFromfile(HTML_FILE));	
		assertFalse(htmlParser.readFromfile(HTML_FILE_NO_TABLE));	
	}

	
	@Test
	public void testGetLines() {
		assertTrue(htmlParser.readFromfile(HTML_FILE));	
		List<String[]> fileLines= htmlParser.getLines();
		String [] firstLine=fileLines.get(0);
		assertEquals(fileLines.size(),2);
		assertEquals(firstLine[0].trim(),"1111");
		assertEquals(firstLine[1].trim(),"John Smith");
	}

	@Test
	public void testGetHeaders() {
		assertTrue(htmlParser.readFromfile(HTML_FILE));	
		String [] fileHeaders= htmlParser.getHeaders();
		assertEquals(fileHeaders.length,2);
		assertEquals(fileHeaders[0].trim(),"ID");
		assertEquals(fileHeaders[1].trim(),"Name");
		
	}


}
