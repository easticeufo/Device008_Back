package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.MeasureApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.CirculationSheet;
import com.madongfang.entity.Data;
import com.madongfang.entity.Measure;
import com.madongfang.exception.HttpBadRequestException;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.CirculationSheetRepository;
import com.madongfang.repository.DataRepository;
import com.madongfang.repository.MeasureRepository;

@Service
public class MeasureService {

	public List<MeasureApi> getMeasures(int circulationId) {
		List<MeasureApi> measures = new LinkedList<>();
		
		for (Measure measure : measureRepository.findByCirculationSheetIdOrderByReportTypeAsc(circulationId)) {
			measures.add(convertToMeasureApi(measure));
		}
		
		return measures;
	}
	
	public MeasureApi getMeasure(Integer measureId) {
		Measure measure;
		if (measureId == 0) // 返回上一次的测量参数
		{
			measure = measureRepository.findFirstByOrderByIdDesc();
			measure.setId(null);
		}
		else
		{
			measure = measureRepository.findOne(measureId);
		}
		
		if (measure == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "该测量不存在"));
		}
		
		return convertToMeasureApi(measure);
	}
	
	public MeasureApi addMeasure(MeasureApi measureApi) {
		if (measureApi.getCirculationId() == null)
		{
			throw new HttpBadRequestException(new ReturnApi(-1, "没有对应的流转单号"));
		}
		CirculationSheet circulationSheet = circulationSheetRepository.findOne(measureApi.getCirculationId());
		if (circulationSheet == null)
		{
			throw new HttpBadRequestException(new ReturnApi(-1, "没有对应的流转单号"));
		}
		
		Measure measure = new Measure();
		measure.setAddress(measureApi.getAddress());
		measure.setCost(measureApi.getCost());
		measure.setHumidity(measureApi.getHumidity());
		measure.setModel(measureApi.getModel());
		measure.setReference1(measureApi.getReference1());
		measure.setReference2(measureApi.getReference2());
		measure.setReference3(measureApi.getReference3());
		measure.setReportType(measureApi.getReportType());
		measure.setSampleName(measureApi.getSampleName());
		measure.setSampleNumber(measureApi.getSampleNumber());
		measure.setStandardName1(measureApi.getStandardName1());
		measure.setStandardName2(measureApi.getStandardName2());
		measure.setStandardName3(measureApi.getStandardName3());
		measure.setStandardAccuracy1(measureApi.getStandardAccuracy1());
		measure.setStandardAccuracy2(measureApi.getStandardAccuracy2());
		measure.setStandardAccuracy3(measureApi.getStandardAccuracy3());
		measure.setStandardNumber1(measureApi.getStandardNumber1());
		measure.setStandardNumber2(measureApi.getStandardNumber2());
		measure.setStandardNumber3(measureApi.getStandardNumber3());
		measure.setStandardCertificateNumber1(measureApi.getStandardCertificateNumber1());
		measure.setStandardCertificateNumber2(measureApi.getStandardCertificateNumber2());
		measure.setStandardCertificateNumber3(measureApi.getStandardCertificateNumber3());
		measure.setTemperature(measureApi.getTemperature());
		measure.setManufacturer(measureApi.getManufacturer());
		measure.setTime(measureApi.getTime());
		measure.setExpiryTime(measure.getExpiryTime());
		measure.setUnit0(measureApi.getUnit0());
		measure.setUnit1(measureApi.getUnit1());
		measure.setCirculationSheet(circulationSheet);
		measure.setDecimalDigits(measureApi.getDecimalDigits());
		measure.setAttachCost(measureApi.getAttachCost());
		measure.setFare(measureApi.getFare());
		measure.setMaterialCost(measureApi.getMaterialCost());
		measure.setRepairCost(measureApi.getRepairCost());
		measure.setRemark(measureApi.getRemark());
		measure.setUncertainty(measureApi.getUncertainty());
		measure.setAccuracy(measureApi.getAccuracy());
		measure.setInteriorSign(measureApi.getInteriorSign());
		if (measureApi.getReportType() == 2)
		{
			measure.setConclusion("/");
		}
		else
		{
			measure.setConclusion(measureApi.getConclusion());
		}
		measure.setType(measureApi.getType());
		measure.setDirection(measureApi.getDirection());
		measureRepository.save(measure);
		
		return convertToMeasureApi(measure);
	}
	
	public MeasureApi updateMeasure(Integer measureId, MeasureApi measureApi)
	{
		Measure measure = measureRepository.findOne(measureId);
		if (measure == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "该测量不存在"));
		}
		
		if (measureApi.getAddress() != null)
		{
			measure.setAddress(measureApi.getAddress());
		}
		if (measureApi.getCost() != null)
		{
			measure.setCost(measureApi.getCost());
		}
		if (measureApi.getHumidity() != null)
		{
			measure.setHumidity(measureApi.getHumidity());
		}
		if (measureApi.getModel() != null)
		{
			measure.setModel(measureApi.getModel());
		}
		if (measureApi.getReference1() != null)
		{
			measure.setReference1(measureApi.getReference1());
		}
		if (measureApi.getReference2() != null)
		{
			measure.setReference2(measureApi.getReference2());
		}
		if (measureApi.getReference3() != null)
		{
			measure.setReference3(measureApi.getReference3());
		}
		if (measureApi.getReportType() != null)
		{
			measure.setReportType(measureApi.getReportType());
		}
		if (measureApi.getSampleName() != null)
		{
			measure.setSampleName(measureApi.getSampleName());
		}
		if (measureApi.getSampleNumber() != null)
		{
			measure.setSampleNumber(measureApi.getSampleNumber());
		}
		if (measureApi.getStandardName1() != null)
		{
			measure.setStandardName1(measureApi.getStandardName1());
		}
		if (measureApi.getStandardName2() != null)
		{
			measure.setStandardName2(measureApi.getStandardName2());
		}
		if (measureApi.getStandardName3() != null)
		{
			measure.setStandardName3(measureApi.getStandardName3());
		}
		if (measureApi.getStandardAccuracy1() != null)
		{
			measure.setStandardAccuracy1(measureApi.getStandardAccuracy1());
		}
		if (measureApi.getStandardAccuracy2() != null)
		{
			measure.setStandardAccuracy2(measureApi.getStandardAccuracy2());
		}
		if (measureApi.getStandardAccuracy3() != null)
		{
			measure.setStandardAccuracy3(measureApi.getStandardAccuracy3());
		}
		if (measureApi.getStandardCertificateNumber1() != null)
		{
			measure.setStandardCertificateNumber1(measureApi.getStandardCertificateNumber1());
		}
		if (measureApi.getStandardCertificateNumber2() != null)
		{
			measure.setStandardCertificateNumber2(measureApi.getStandardCertificateNumber2());
		}
		if (measureApi.getStandardCertificateNumber3() != null)
		{
			measure.setStandardCertificateNumber3(measureApi.getStandardCertificateNumber3());
		}
		if (measureApi.getStandardNumber1() != null)
		{
			measure.setStandardNumber1(measureApi.getStandardNumber1());
		}
		if (measureApi.getStandardNumber2() != null)
		{
			measure.setStandardNumber2(measureApi.getStandardNumber2());
		}
		if (measureApi.getStandardNumber3() != null)
		{
			measure.setStandardNumber3(measureApi.getStandardNumber3());
		}
		if (measureApi.getTemperature() != null)
		{
			measure.setTemperature(measureApi.getTemperature());
		}
		if (measureApi.getUnit0() != null)
		{
			measure.setUnit0(measureApi.getUnit0());
		}
		if (measureApi.getUnit1() != null)
		{
			measure.setUnit1(measureApi.getUnit1());
		}
		if (measureApi.getManufacturer() != null)
		{
			measure.setManufacturer(measureApi.getManufacturer());
		}
		if (measureApi.getDecimalDigits() != null)
		{
			measure.setDecimalDigits(measureApi.getDecimalDigits());
		}
		if (measureApi.getAttachCost() != null)
		{
			measure.setAttachCost(measureApi.getAttachCost());
		}
		if (measureApi.getFare() != null)
		{
			measure.setFare(measureApi.getFare());
		}
		if (measureApi.getMaterialCost() != null)
		{
			measure.setMaterialCost(measureApi.getMaterialCost());
		}
		if (measureApi.getRepairCost() != null)
		{
			measure.setRepairCost(measureApi.getRepairCost());
		}
		if (measureApi.getRemark() != null)
		{
			measure.setRemark(measureApi.getRemark());
		}
		if (measure.getReportType() == 2)
		{
			measure.setConclusion("/");
		}
		else
		{
			measure.setConclusion(measureApi.getConclusion());
		}
		if (measureApi.getUncertainty() != null)
		{
			measure.setUncertainty(measureApi.getUncertainty());
		}
		if (measureApi.getAccuracy() != null)
		{
			measure.setAccuracy(measureApi.getAccuracy());
		}
		if (measureApi.getType() != null)
		{
			measure.setType(measureApi.getType());
		}
		if (measureApi.getDirection() != null)
		{
			measure.setDirection(measureApi.getDirection());
		}
		if (measureApi.getTime() != null)
		{
			measure.setTime(measureApi.getTime());
		}
		if (measureApi.getExpiryTime() != null)
		{
			measure.setExpiryTime(measureApi.getExpiryTime());
		}
		if (measureApi.getInteriorSign() != null)
		{
			measure.setInteriorSign(measureApi.getInteriorSign());
		}
		
		measureRepository.save(measure);
		
		return convertToMeasureApi(measure);
	}
	
	public void deleteMeasure(Integer measureId) {
		measureRepository.delete(measureId);
	}
	
	public ReturnApi addDatas(int measureId, List<Integer> dataIds) {
		Measure measure = measureRepository.findOne(measureId);
		if (measure == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "该测量不存在"));
		}
		
		List<Data> datas = dataRepository.findByIdIn(dataIds);
		for (Data data : datas) {
			data.setMeasure(measure);
		}
		
		dataRepository.save(datas);
		return new ReturnApi(0, "OK");
	}
	
	@Autowired
	private MeasureRepository measureRepository;
	
	@Autowired
	private CirculationSheetRepository circulationSheetRepository;
	
	@Autowired
	private DataRepository dataRepository;
	
	private MeasureApi convertToMeasureApi(Measure measure)
	{	
		MeasureApi measureApi = new MeasureApi();
		
		measureApi.setId(measure.getId());
		measureApi.setAddress(measure.getAddress());
		measureApi.setCost(measure.getCost());
		measureApi.setHumidity(measure.getHumidity());
		measureApi.setModel(measure.getModel());
		measureApi.setReference1(measure.getReference1());
		measureApi.setReference2(measure.getReference2());
		measureApi.setReference3(measure.getReference3());
		measureApi.setReportType(measure.getReportType());
		measureApi.setSampleName(measure.getSampleName());
		measureApi.setSampleNumber(measure.getSampleNumber());
		measureApi.setStandardName1(measure.getStandardName1());
		measureApi.setStandardName2(measure.getStandardName2());
		measureApi.setStandardName3(measure.getStandardName3());
		measureApi.setStandardAccuracy1(measure.getStandardAccuracy1());
		measureApi.setStandardAccuracy2(measure.getStandardAccuracy2());
		measureApi.setStandardAccuracy3(measure.getStandardAccuracy3());
		measureApi.setStandardCertificateNumber1(measure.getStandardCertificateNumber1());
		measureApi.setStandardCertificateNumber2(measure.getStandardCertificateNumber2());
		measureApi.setStandardCertificateNumber3(measure.getStandardCertificateNumber3());
		measureApi.setStandardNumber1(measure.getStandardNumber1());
		measureApi.setStandardNumber2(measure.getStandardNumber2());
		measureApi.setStandardNumber3(measure.getStandardNumber3());
		measureApi.setTemperature(measure.getTemperature());
		measureApi.setTime(measure.getTime());
		measureApi.setUnit0(measure.getUnit0());
		measureApi.setUnit1(measure.getUnit1());
		measureApi.setManufacturer(measure.getManufacturer());
		measureApi.setDecimalDigits(measure.getDecimalDigits());
		measureApi.setAttachCost(measure.getAttachCost());
		measureApi.setFare(measure.getFare());
		measureApi.setMaterialCost(measure.getMaterialCost());
		measureApi.setRepairCost(measure.getRepairCost());
		measureApi.setRemark(measure.getRemark());
		measureApi.setConclusion(measure.getConclusion());
		measureApi.setUncertainty(measure.getUncertainty());
		measureApi.setAccuracy(measure.getAccuracy());
		measureApi.setType(measure.getType());
		measureApi.setDirection(measure.getDirection());
		measureApi.setExpiryTime(measure.getExpiryTime());
		measureApi.setInteriorSign(measure.getInteriorSign());
		
		return measureApi;
	}
}
