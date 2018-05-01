package test.java.files;

import static org.junit.Assert.*;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import main.java.files.CsvParser;
import main.java.files.FileParser;

public class CsvParserTest {

	final String HERE = Paths.get(".").toAbsolutePath().normalize().toString();
	final String CSV_FILE = Paths.get(HERE, "src/test/java/files/data.csv").toString();
	private FileParser csvParser;  
	
	@Before 
	public void initialize() {
		csvParser=new CsvParser();
	}
	
	@Test
	public void testReadFromfile() {
		assertTrue(csvParser.readFromfile(CSV_FILE));		
	}

	
	@Test
	public void testGetLines() {
		assertTrue(csvParser.readFromfile(CSV_FILE));	
		List<String[]> fileLines= csvParser.getLines();
		String [] thirdLine=fileLines.get(2);
		assertEquals(fileLines.size(),9);
		assertEquals(thirdLine[0].trim(),"I am so excited about the concert");
		assertEquals(thirdLine[1].trim(),"pos");
	}

	@Test
	public void testGetHeaders() {
		assertTrue(csvParser.readFromfile(CSV_FILE));	
		String [] fileHeaders=csvParser.getHeaders();
		assertEquals(fileHeaders.length,2);
		assertEquals(fileHeaders[0].trim(),"I love this car");
		assertEquals(fileHeaders[1].trim(),"pos");
		
	}

}
