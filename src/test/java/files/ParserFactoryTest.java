package test.java.files;

import static org.junit.Assert.*;

import java.nio.file.Paths;

import org.junit.Test;

import main.java.files.CsvParser;
import main.java.files.FileParser;
import main.java.files.HTMLParser;
import main.java.files.ParserFactory;

public class ParserFactoryTest {

	final String HERE = Paths.get(".").toAbsolutePath().normalize().toString();
	final String CSV_FILE = Paths.get(HERE, "src/test/java/files/data.csv").toString();
	final String JSON_FILE = Paths.get(HERE, "src/test/java/files/data.json").toString();
	final String HTML_FILE = Paths.get(HERE, "src/test/java/files/data.html").toString();
	
	@Test
    public void testDetectCsv() {
		ParserFactory parser=new ParserFactory();
		FileParser curParser = parser.Parse(CSV_FILE);
		assertEquals(curParser.getClass(),CsvParser.class);   
    }
	
	@Test
    public void testDetectHtml() {
		ParserFactory parser=new ParserFactory();
		FileParser curParser = parser.Parse(HTML_FILE);
		assertEquals(curParser.getClass(),HTMLParser.class);   
    }
	
	@Test
	 public void testDetectJson() {
			ParserFactory parser=new ParserFactory();
			FileParser curParser = parser.Parse(JSON_FILE);
			assertNull(curParser);
	 }
			
    
}
