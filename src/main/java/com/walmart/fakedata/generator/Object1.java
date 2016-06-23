package com.walmart.fakedata.generator;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.fluttercode.datafactory.impl.DataFactory;
public class Object1 {
	private String OrderTime;
	private String ProcessingTime;
	private String FirstName;
	private String LastName;
	private String CardHolderName;
	private String Xref;
	private String XrefType;
	private String CPID_list;
	private String CDID_list;
	private String EncryptedPayload;
	
	DataFactory df = null;
	public Object1(){
		 df = new DataFactory();	
	}
	
	public String getOrderTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());	
	}
	public void setOrderTime(String orderTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        orderTime = sdf.format(new Date());
		OrderTime = orderTime;
	}
	public String getProcessingTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        ProcessingTime = sdf.format(new Date());
		return ProcessingTime;
	}
	public void setProcessingTime(String processingTime) {
		ProcessingTime = processingTime;
	}
	public String getFirstName() {
		 return df.getFirstName();
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return df.getLastName();
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getCardHolderName() {
		return df.getFirstName() + df.getLastName();
	}
	public void setCardHolderName(String cardHolderName) {
		CardHolderName = cardHolderName;
	}
	public String getXref() {
		return df.getRandomChars(10);
	}
	public void setXref(String xref) {
		Xref = xref;
	}
	public String getXrefType() {
		return df.getRandomChars(3);
	}
	public void setXrefType(String xrefType) {
		XrefType = xrefType;
	}
	public String getCPID_list() {
		return "null";
	}
	public void setCPID_list(String cPID_list) {
		CPID_list = cPID_list;
	}
	public String getCDID_list() {
		return "null";
	}
	public void setCDID_list(String cDID_list) {
		CDID_list = cDID_list;
	}
	public String getEncryptedPayload() {
		return df.getRandomChars(10);
	}
	
	
}
