package com.madongfang.api;

public class DataApi {

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

	@Override
	public String toString() {
		return "DataApi [id=" + id + ", row=" + row + ", value1=" + value1 + ", value2=" + value2 + ", value3=" + value3
				+ ", baseValue=" + baseValue + ", baseValue0=" + baseValue0 + ", decimalDigit=" + decimalDigit + "]";
	}

	private Integer id;
	
	private Integer row;
	
	private Integer value1; // 测量值1
	
	private Integer value2; // 测量值2
	
	private Integer value3; // 测量值3
	
	private Integer baseValue; // 基准值
	
	private Integer baseValue0;
	
	private Integer decimalDigit; // 小数位数
}
