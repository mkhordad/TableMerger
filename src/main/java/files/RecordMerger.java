package main.java.files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import au.com.bytecode.opencsv.CSVWriter;

public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";
	public static final String COMMON_COLUMN = "ID";
	public HashSet <String> headerSet = new HashSet <String>();
	public Table <Integer, String, String> records = HashBasedTable.create();
	//This is equivalent to HashMap<Integer, HashMap<String, String>>
	//or more clearly HashMap<ID, HashMap<Column Name, column data>>
	
	/**
	 *
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		ParserFactory parser=new ParserFactory();
		RecordMerger merger= new RecordMerger();
		for (int i=0; i< args.length; i++)
		{
			System.out.println("Parsing "+ args[i]);
			FileParser curParser = parser.Parse(args[i]);
			
			if (curParser==null)
			{
				System.err.println("ERROR: Unable to process file type for " + args[i]);
				System.exit(1);
			}
			Boolean success = curParser.readFromfile(args[i]);
			if(!success) 
			{
				System.err.println("ERROR: Unable to read from file " + args[i]);
				System.exit(1);
			}
			String [] fileHeaders=curParser.getHeaders();
			List<String[]> fileLines= curParser.getLines();
			
			
			int idIndex= merger.updateHeaderSet(fileHeaders);
			if (idIndex<0)
			{
				System.err.println("ERROR: Unable to find "+ COMMON_COLUMN +" in " + args[i]);
				System.exit(1);
			}
			
			merger.updateRecords(fileHeaders, fileLines, idIndex);			
		}
		
		ArrayList <String []> finalResults=merger.createFinalResult();
		merger.writeCSVFile(finalResults);	
		System.out.println("Merge is done.");
	}
	
	
	/**
	 * adds the new columns to the header set and returns the 
	 * index of the COMMON_COLUMN
	 *
	 * @param The array of columns' names
	 * @return The index of the COMMON_COLUMN 
	 *         or -1 if COMMON_COLUMN is not available in the columns
	 */
	public int updateHeaderSet(String [] fileHeaders)
	{
		int idIndex=-1;
		for (int i=0; i<fileHeaders.length; i++)
		{
			if (fileHeaders[i].trim().equals(COMMON_COLUMN))
				idIndex=i;
			headerSet.add(fileHeaders[i]);
		}
		return idIndex;
	}
	
	/**
	 * adds the new lines to the the table of records
	 *
	 * @param The array of columns' names extracted from the file  
	 * @param The list of lines extracted from the file
	 * @param The index of the COMMON_COLUMN   
	 */
	public void updateRecords(String [] fileHeaders, List<String[]> fileLines, int idIndex)
	{
		for (String [] curLine: fileLines)
		{
			//check if the id is correctly an integer 
			//(There might be an error in the input file)
			if(!isParsableToInt(curLine[idIndex])) 
				continue;
			int curID= Integer.parseInt(curLine[idIndex]);
			
			for (int i=0 ; i< curLine.length; i++)
			{
				if(i != idIndex)
				{
					if (!records.contains(curID, fileHeaders[i]))
						records.put(curID, fileHeaders[i], curLine [i]);
				}
			}
		}
	}
	
	
	/**
	 * Check if a string is parsable to int
	 *
	 * @param The input string
	 * @return true if the input is convertible to integer
	 */
	public boolean isParsableToInt(String text)
	{
		try
		{
		  Integer.parseInt(text);
		  return true;
		}
		catch(NumberFormatException nfe)
		{
		  return false;
		}
	}
		
	/**
	 * Writes the result available in headerSet and records to the FILENAME_COMBINED file
	 *
	 */
	public void writeCSVFile(ArrayList <String []> resultLines)
	{
		CSVWriter csvWriter;
		try {
			csvWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(FILENAME_COMBINED), "UTF-8"));	
			csvWriter.writeAll(resultLines);
	        
	       try {
				csvWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	       
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
		 
	}
	
	public ArrayList <String []> createFinalResult()
	{
		//Make the header line
		String [] finalHeaders = new String[headerSet.size()];
		Iterator<String> headerItr= headerSet.iterator();
		
		//ID is always the first column in output
		finalHeaders[0]= COMMON_COLUMN;
		int index=1;
		while  (headerItr.hasNext() )
		{
			String curHeader = headerItr.next().toString().trim();
			
			if (!curHeader.equals(COMMON_COLUMN))
			{
				finalHeaders[index]= curHeader;
				index++;
			}
		}
		ArrayList <String []> finalResult= new ArrayList <String []> ();
		finalResult.add(finalHeaders);
		
		//Get the list of IDs and sort them
		Set <Integer> finalIDSet = records.rowKeySet();
		ArrayList <Integer> finalIDs = new ArrayList <Integer> (finalIDSet);
		Collections.sort (finalIDs);	
   
	        for (int curID : finalIDs)
	        {
	        	String [] curLine= new String [finalHeaders.length];
	        	for (int i=0; i< finalHeaders.length; i++)
	        	{
	        		if(finalHeaders[i].trim().equals(COMMON_COLUMN))
	        			curLine[i]= Integer.toString(curID);
	        		else
	        		{
	        			//If the data related to curID and the current column is vailable 
	        			//insert in the curLine otherwise it should be empty.
	        			if(records.contains(curID, finalHeaders[i]))
	        				curLine[i]= records.get(curID, finalHeaders[i]);
	        			else
	        				curLine[i]="";
	        		}
	        	}
	            
	        	finalResult.add(curLine);
	        }
		return finalResult;
	}
	
}
