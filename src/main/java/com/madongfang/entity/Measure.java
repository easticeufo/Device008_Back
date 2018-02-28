package com.madongfang.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Measure {

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public Integer getHumidity() {
		return humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getReference1() {
		return reference1;
	}

	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}

	public String getReference2() {
		return reference2;
	}

	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}

	public String getReference3() {
		return reference3;
	}

	public void setReference3(String reference3) {
		this.reference3 = reference3;
	}

	public String getStandardName1() {
		return standardName1;
	}

	public void setStandardName1(String standardName1) {
		this.standardName1 = standardName1;
	}

	public String getStandardNumber1() {
		return standardNumber1;
	}

	public void setStandardNumber1(String standardNumber1) {
		this.standardNumber1 = standardNumber1;
	}

	public String getStandardAccuracy1() {
		return standardAccuracy1;
	}

	public void setStandardAccuracy1(String standardAccuracy1) {
		this.standardAccuracy1 = standardAccuracy1;
	}

	public String getStandardCertificateNumber1() {
		return standardCertificateNumber1;
	}

	public void setStandardCertificateNumber1(String standardCertificateNumber1) {
		this.standardCertificateNumber1 = standardCertificateNumber1;
	}

	public String getStandardName2() {
		return standardName2;
	}

	public void setStandardName2(String standardName2) {
		this.standardName2 = standardName2;
	}

	public String getStandardNumber2() {
		return standardNumber2;
	}

	public void setStandardNumber2(String standardNumber2) {
		this.standardNumber2 = standardNumber2;
	}

	public String getStandardAccuracy2() {
		return standardAccuracy2;
	}

	public void setStandardAccuracy2(String standardAccuracy2) {
		this.standardAccuracy2 = standardAccuracy2;
	}

	public String getStandardCertificateNumber2() {
		return standardCertificateNumber2;
	}

	public void setStandardCertificateNumber2(String standardCertificateNumber2) {
		this.standardCertificateNumber2 = standardCertificateNumber2;
	}

	public String getStandardName3() {
		return standardName3;
	}

	public void setStandardName3(String standardName3) {
		this.standardName3 = standardName3;
	}

	public String getStandardNumber3() {
		return standardNumber3;
	}

	public void setStandardNumber3(String standardNumber3) {
		this.standardNumber3 = standardNumber3;
	}

	public String getStandardAccuracy3() {
		return standardAccuracy3;
	}

	public void setStandardAccuracy3(String standardAccuracy3) {
		this.standardAccuracy3 = standardAccuracy3;
	}

	public String getStandardCertificateNumber3() {
		return standardCertificateNumber3;
	}

	public void setStandardCertificateNumber3(String standardCertificateNumber3) {
		this.standardCertificateNumber3 = standardCertificateNumber3;
	}

	public String getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(String sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getUnit0() {
		return unit0;
	}

	public void setUnit0(String unit0) {
		this.unit0 = unit0;
	}

	public String getUnit1() {
		return unit1;
	}

	public void setUnit1(String unit1) {
		this.unit1 = unit1;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public CirculationSheet getCirculationSheet() {
		return circulationSheet;
	}

	public void setCirculationSheet(CirculationSheet circulationSheet) {
		this.circulationSheet = circulationSheet;
	}

	public Integer getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(Integer decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public Double getAttachCost() {
		return attachCost;
	}

	public void setAttachCost(Double attachCost) {
		this.attachCost = attachCost;
	}

	public Double getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(Double materialCost) {
		this.materialCost = materialCost;
	}

	public Double getRepairCost() {
		return repairCost;
	}

	public void setRepairCost(Double repairCost) {
		this.repairCost = repairCost;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConclusion() {
		return conclusion;
	}

	public void setConclusion(String conclusion) {
		this.conclusion = conclusion;
	}

	public String getUncertainty() {
		return uncertainty;
	}

	public void setUncertainty(String uncertainty) {
		this.uncertainty = uncertainty;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getInteriorSign() {
		return interiorSign;
	}

	public void setInteriorSign(String interiorSign) {
		this.interiorSign = interiorSign;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String address; // 测量地点
	
	private Integer temperature; // 温度
	
	private Integer humidity; // 湿度
	
	private String model; // 型号
	
	private String accuracy; // 被检仪器准确度
	
	private String reference1; // 依据
	
	private String reference2;
	
	private String reference3;
	
	private String standardName1; // 标准器
	
	private String standardNumber1;
	
	private String standardAccuracy1;
	
	private String standardCertificateNumber1;
	
	private String standardName2;
	
	private String standardNumber2;
	
	private String standardAccuracy2;
	
	private String standardCertificateNumber2;
	
	private String standardName3;
	
	private String standardNumber3;
	
	private String standardAccuracy3;
	
	private String standardCertificateNumber3;
	
	private String sampleNumber; // 样品编号
	
	private String sampleName; // 数显式、指针式、预置式
	
	private Double cost; // 检测费用
	
	private Double attachCost; // 项目增加费
	
	private Double materialCost; // 材料费
	
	private Double repairCost; // 修理费
	
	private Double fare; // 车费
	
	private Integer reportType; // 报告类型，1-检定、2-校准
	
	private String manufacturer; // 制造厂名
	
	private String unit0;
	
	private String unit1;
	
	private Integer decimalDigits;
	
	private Date time;
	
	private Date expiryTime; // 有效期
	
	private String remark; // 备注
	
	private String conclusion; // 结论
	
	private String uncertainty; // 扩展不确定度
	
	private String direction; // 检定/校准方位
	
	private String type; // 测量类型
	
	private String interiorSign; // 内部标示
	
	@ManyToOne
	private CirculationSheet circulationSheet;
}
