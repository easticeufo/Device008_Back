package com.madongfang.api;

public class CirculationApi {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCertificateUnit() {
		return certificateUnit;
	}

	public void setCertificateUnit(String certificateUnit) {
		this.certificateUnit = certificateUnit;
	}

	public String getCertificateAddress() {
		return certificateAddress;
	}

	public void setCertificateAddress(String certificateAddress) {
		this.certificateAddress = certificateAddress;
	}

	private Integer id;
	
	private String number;
	
	private String certificateUnit;
	
	private String certificateAddress;
}
