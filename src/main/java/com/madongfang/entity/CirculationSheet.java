package com.madongfang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CirculationSheet {

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true)
	private String number; // 流转号
	
	private String certificateUnit; // 证书单位/委托方名称
	
	private String certificateAddress; // 证书单位地址
	
	@ManyToOne
	private User user;
}
