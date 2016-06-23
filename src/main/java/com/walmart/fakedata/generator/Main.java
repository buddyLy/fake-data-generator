package com.walmart.fakedata.generator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class Main {
 
    public static void main(String[] args) {
    	Integer lineNbr=new Integer(args[0]);
    	String filename=args[1];
    	
    	System.out.println("Starting fake data generator");
    	System.out.println("Nbr of records to be created:" + lineNbr);
    	System.out.println("File written to:" + filename);
    	Main main = new Main();
    	
        main.getData(lineNbr, filename);
    	
    }
    
    public void getData(Integer lineNbr, String filename)
    {
    	FakeDataGenerator gen = new FakeDataGenerator();
    	int whereamiat=0;
    	int written_records=0;
    	for (int i=1; i<=lineNbr; i++){
    		if (i>whereamiat)
    		{
    			System.out.println("Nbr of records generated so far: "+i);
    			whereamiat++;
    			whereamiat=whereamiat*100;	
    		}
	    	String orderTime=gen.getOrderTime();
	    	String processingTime=gen.getProcessingTime();
	    	String firstName=gen.getFirstName();
	    	String lastName=gen.getLastName();
	    	String cardholdername=firstName+" "+lastName;
	    	String xref=gen.getXref();
	    	String xrefType=gen.getXrefType();
	    	String list1=gen.getCPID_list();
	    	String list2=gen.getCDID_list();
	    	String payload=gen.getEncryptedPayload();
	    	//System.out.println(orderTime+"|"+processingTime+"|"+firstName+"|"+lastName+"|"+cardholdername+"|"+xref+"|"+xrefType+"|"+list1+"|"+list2+"|"+payload);
	    
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(orderTime);
	    	buffer.append("|");
	    	buffer.append(processingTime);
	    	buffer.append("|");
	    	buffer.append(firstName);
	    	buffer.append("|");
	    	buffer.append(lastName);
	    	buffer.append("|");
	    	buffer.append(cardholdername);
	    	buffer.append("|");
	    	buffer.append(xref);
	    	buffer.append("|");
	    	buffer.append(xrefType);
	    	buffer.append("|");
	    	buffer.append(list1);
	    	buffer.append("|");
	    	buffer.append(list2);
	    	buffer.append("|");
	    	buffer.append(payload);
	    	buffer.append("\n");
	    	
	 
			try {
//				Files.write(Paths.get("/Users/lcle/git/wmgit/bitbucket/fake-data-generator/fake-data-geneartor/filename.txt"), 
//						buffer.toString().getBytes(), StandardOpenOption.APPEND);
				Files.write(Paths.get(filename), 
						buffer.toString().getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			written_records=i;
    	}
    	System.out.println("Nbr of records written: "+written_records);
    }
}