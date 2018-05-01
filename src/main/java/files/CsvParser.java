package main.java.files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import au.com.bytecode.opencsv.CSVReader;

public class CsvParser extends FileParser{
	
	@Override
	/**
	 * Reads a csv file and extracts its headers and lines.
	 *
	 * @param Input file name
	 * @return True if there is no problem otherwise false
	 */
	public boolean readFromfile(String fileName) {
		try {
			//Because the file can be in any language we specify the encoding as UTF-8
			 CSVReader csvReader = new CSVReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
				
			 lines = csvReader.readAll(); 
			 csvReader.close();
			 
			 if(lines.size()<2)//There is no data in this file
					return false;
			 
			 //Header is the first line in the file
			 headers = lines.get(0);
			 
			 //Remove the header line from lines
			 lines.set(0, lines.get(lines.size()-1));
			 lines.remove(lines.size()-1);		     
	         return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return false;
    }

	
}
