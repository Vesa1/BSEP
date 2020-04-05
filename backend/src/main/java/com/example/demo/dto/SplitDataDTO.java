package com.example.demo.dto;

public class SplitDataDTO {

	private String UID;
	private String EMAILADDRESS;
	private String C;
	private String OU;
	private String O;
	private String GIVENNAME;
	private String SURNAME;
	private String CN;
	
	public SplitDataDTO() {}

	public SplitDataDTO(String uID, String eMAILADDRESS,  String c, String uo, String o, String gIVENNAME, String sURNAME,
			String cN) {
		super();
		UID = uID;
		EMAILADDRESS = eMAILADDRESS;
		C = c;
		OU = uo;
		O = o;
		GIVENNAME = gIVENNAME;
		SURNAME = sURNAME;
		CN = cN;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getEMAILADDRESS() {
		return EMAILADDRESS;
	}

	public void setEMAILADDRESS(String eMAILADDRESS) {
		EMAILADDRESS = eMAILADDRESS;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getO() {
		return O;
	}

	public void setO(String o) {
		O = o;
	}

	public String getGIVENNAME() {
		return GIVENNAME;
	}

	public void setGIVENNAME(String gIVENNAME) {
		GIVENNAME = gIVENNAME;
	}

	public String getSURNAME() {
		return SURNAME;
	}

	public void setSURNAME(String sURNAME) {
		SURNAME = sURNAME;
	}

	public String getCN() {
		return CN;
	}

	public void setCN(String cN) {
		CN = cN;
	}

	@Override
	public String toString() {
		return "DataSubjectsDTO [UID=" + UID + ", EMAILADDRESS=" + EMAILADDRESS + ", C=" + C + ", O=" + O
				+ ", GIVENNAME=" + GIVENNAME + ", SURNAME=" + SURNAME + ", CN=" + CN + "]";
	}

	public String getOU() {
		return OU;
	}

	public void setOU(String oU) {
		OU = oU;
	}
	
	
}
