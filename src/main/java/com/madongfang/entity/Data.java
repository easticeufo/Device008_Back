package com.madongfang.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Data {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getValue1() {
		return value1;
	}

	public void setValue1(Integer value1) {
		this.value1 = value1;
	}

	public Integer getValue2() {
		return value2;
	}

	public void setValue2(Integer value2) {
		this.value2 = value2;
	}

	public Integer getValue3() {
		return value3;
	}

	public void setValue3(Integer value3) {
		this.value3 = value3;
	}

	public Integer getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(Integer baseValue) {
		this.baseValue = baseValue;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public Integer getBaseValue0() {
		return baseValue0;
	}

	public void setBaseValue0(Integer baseValue0) {
		this.baseValue0 = baseValue0;
	}

	public Integer getDecimalDigit() {
		return decimalDigit;
	}

	public void setDecimalDigit(Integer decimalDigit) {
		this.decimalDigit = decimalDigit;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private Integer row;
	
	private Integer value1;
	
	private Integer value2;
	
	private Integer value3;
	
	private Integer baseValue0;
	
	private Integer baseValue;
	
	private Integer decimalDigit; // 小数位数
	
	@ManyToOne
	private Measure measure;
}
