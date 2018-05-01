package test.java.files;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import main.java.files.RecordMerger;

public class RecordMergerTest {


	@Test
	public void testUpdateHeaderSet() {
		
		RecordMerger merger= new RecordMerger();
		String [] fileHeaders1={"First","Second","Third"};
		String [] fileHeaders2={"Third","Four"};
		String [] fileHeaders3={"First","Fifth","Third","Four"};
		HashSet <String> finalHeaders=new HashSet <String>();
		finalHeaders.add("First");
		finalHeaders.add("Second");
		finalHeaders.add("Third");
		finalHeaders.add("Four");
		finalHeaders.add("Fifth");
		merger.updateHeaderSet(fileHeaders1);
		merger.updateHeaderSet(fileHeaders2);
		merger.updateHeaderSet(fileHeaders3);
		assertEquals(merger.headerSet,finalHeaders);
		
	}

	@Test
	public void testUpdateRecords() {
		
		RecordMerger merger= new RecordMerger();
		String [] fileHeaders1={"ID","Second","Third"};
		List<String[]> linesList1=new ArrayList <String[]> ();
		String [] line1= {"11","12","13"};
		String [] line2= {"22","222","23"};
		linesList1.add(line1);
		linesList1.add(line2);
		merger.updateRecords(fileHeaders1, linesList1, 0);
		
		String [] fileHeaders2={"First","ID","Second"};
		List<String[]> linesList2=new ArrayList <String[]> ();
		String [] line3= {"31","11","12"};
		String [] line4= {"41","44","43"};
		linesList2.add(line3);
		linesList2.add(line4);
		merger.updateRecords(fileHeaders2, linesList2, 1);
		
		Table <Integer, String, String> finalRecords = HashBasedTable.create();
		finalRecords.put(11, "Second", "12");
		finalRecords.put(11, "Third", "13");
		finalRecords.put(11, "First", "31");
		finalRecords.put(22, "Second", "222");
		finalRecords.put(22, "Third", "23");
		finalRecords.put(44, "First", "41");
		finalRecords.put(44, "Second", "43");
		
		assertEquals(merger.records,finalRecords);
	}

	@Test
	public void testIsParsableToInt() {
		RecordMerger merger= new RecordMerger();
		assertTrue(merger.isParsableToInt("2345"));
		assertFalse(merger.isParsableToInt("65hgf"));
		
	}

	
	@Test
	public void testCreateFinalResult() {
		RecordMerger merger= new RecordMerger();
		String [] fileHeaders={"First","ID","Third"};
		List<String[]> linesList=new ArrayList <String[]> ();
		String [] line1= {"12","11","13"};
		String [] line2= {"222","22","23"};
		linesList.add(line1);
		linesList.add(line2);
		merger.updateHeaderSet(fileHeaders);
		merger.updateRecords(fileHeaders, linesList, 1);
		ArrayList <String []> finalResult=merger.createFinalResult();
		String [] firstLine=finalResult.get(0);
		String [] secondLine=finalResult.get(1);
		String [] thirdLine=finalResult.get(2);
		assertEquals(firstLine.length,3);
		assertEquals(firstLine[0],"ID");
		assertEquals(finalResult.size(),3);
		assertTrue(Arrays.asList(firstLine).contains("Third"));
		assertTrue(Arrays.asList(secondLine).contains("11") || Arrays.asList(thirdLine).contains("11"));
		assertTrue(Arrays.asList(secondLine).contains("22") || Arrays.asList(thirdLine).contains("22"));
	}

}
