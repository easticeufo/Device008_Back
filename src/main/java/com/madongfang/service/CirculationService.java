package com.madongfang.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.CirculationApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.CirculationSheet;
import com.madongfang.entity.Data;
import com.madongfang.entity.Measure;
import com.madongfang.entity.User;
import com.madongfang.exception.HttpBadRequestException;
import com.madongfang.exception.HttpInternalServerErrorException;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.CirculationSheetRepository;
import com.madongfang.repository.DataRepository;
import com.madongfang.repository.MeasureRepository;
import com.madongfang.repository.UserRepository;

@Service
public class CirculationService {

	public List<CirculationApi> getCirculations(int userId) {
		List<CirculationApi> circulations = new LinkedList<>();
		
		for (CirculationSheet circulationSheet : circulationSheetRepository.findByUserId(userId)) {
			circulations.add(convertToCirculationApi(circulationSheet));
		}
		
		return circulations;
	}
	
	public CirculationApi getCirculation(Integer circulationId) {
		CirculationSheet circulationSheet = circulationSheetRepository.findOne(circulationId);
		if (circulationSheet == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "对应的流转单不存在"));
		}
		return convertToCirculationApi(circulationSheet);
	}
	
	public CirculationApi addCirculation(int userId, CirculationApi circulationApi) {
		User user = userRepository.findOne(userId);
		if (user == null)
		{
			throw new HttpBadRequestException(new ReturnApi(-1, "不存在该用户"));
		}
		
		CirculationSheet circulationSheet = new CirculationSheet();
		circulationSheet.setCertificateUnit(circulationApi.getCertificateUnit());
		circulationSheet.setNumber(circulationApi.getNumber());
		circulationSheet.setUser(user);
		circulationSheet.setCertificateAddress(circulationApi.getCertificateAddress());
		circulationSheetRepository.save(circulationSheet);
		
		return convertToCirculationApi(circulationSheet);
	}
	
	public CirculationApi updateCirculation(Integer circulationId, CirculationApi circulationApi) {
		CirculationSheet circulationSheet = circulationSheetRepository.findOne(circulationId);
		if (circulationSheet == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "对应的流转单不存在"));
		}
		
		if (circulationApi.getCertificateUnit() != null)
		{
			circulationSheet.setCertificateUnit(circulationApi.getCertificateUnit());
		}
		if (circulationApi.getNumber() != null)
		{
			circulationSheet.setNumber(circulationApi.getNumber());
		}
		if (circulationApi.getCertificateAddress() != null)
		{
			circulationSheet.setCertificateAddress(circulationApi.getCertificateAddress());
		}
		circulationSheetRepository.save(circulationSheet);
		
		return convertToCirculationApi(circulationSheet);
	}
	
	public void deleteCirculation(Integer circulationId) {
		circulationSheetRepository.delete(circulationId);
	}
	
	public void export(HttpServletResponse response, Integer circulationId) {
		CirculationSheet circulationSheet = circulationSheetRepository.findOne(circulationId);
		if (circulationSheet == null)
		{
			throw new HttpBadRequestException(new ReturnApi(-1, "不存在的流转单"));
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		List<Measure> measures = measureRepository.findByCirculationSheetIdOrderByReportTypeAsc(circulationId);
		
		try {
			response.setContentType("application/binary;charset=ISO8859_1");
			String filename = new String((circulationSheet.getNumber() + circulationSheet.getCertificateUnit() + ".zip").getBytes(), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
			ZipEntry zipEntry = new ZipEntry("列表" + circulationSheet.getCertificateUnit() + ".xls");
			zos.putNextEntry(zipEntry);
			HSSFWorkbook workbook = new HSSFWorkbook(getClass().getResourceAsStream("/liebiao.xls"));
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			for (int i = 0; i < measures.size(); i++) {
				Measure measure = measures.get(i);
				row = sheet.getRow(i+1);
				cell = row.getCell(0);
				cell.setCellValue(i+1);
				cell = row.getCell(1);
				if (measure.getReportType() == 1)
				{
					cell.setCellValue("检定");
				}
				else
				{
					cell.setCellValue("校准");
				}
				cell = row.getCell(2);
				cell.setCellValue(measure.getInteriorSign());
				cell = row.getCell(3);
				cell.setCellValue(circulationSheet.getCertificateUnit());
				cell = row.getCell(4);
				cell.setCellValue(circulationSheet.getCertificateAddress());
				cell = row.getCell(5);
				cell.setCellValue(measure.getSampleName());
				cell = row.getCell(6);
				cell.setCellValue(1);
				cell = row.getCell(7);
				cell.setCellValue(measure.getModel());
				cell = row.getCell(8);
				cell.setCellValue(measure.getSampleNumber());
				cell = row.getCell(9);
				cell.setCellValue(measure.getManufacturer());
				cell = row.getCell(10);
				cell.setCellValue(measure.getConclusion());
				cell = row.getCell(11);
				sdf.applyPattern("yyyy-MM-dd");
				cell.setCellValue(sdf.format(measure.getTime()));
				cell = row.getCell(12);
				if (cell == null)
				{
					cell = row.createCell(12);
				}
				if (measure.getCost() != null)
				{
					cell.setCellValue(measure.getCost());
				}
				cell = row.getCell(13);
				if (cell == null)
				{
					cell = row.createCell(13);
				}
				if (measure.getAttachCost() != null)
				{
					cell.setCellValue(measure.getAttachCost());
				}
				cell = row.getCell(14);
				if (cell == null)
				{
					cell = row.createCell(14);
				}
				if (measure.getMaterialCost() != null)
				{
					cell.setCellValue(measure.getMaterialCost());
				}
				cell = row.getCell(15);
				if (cell == null)
				{
					cell = row.createCell(15);
				}
				if (measure.getRepairCost() != null)
				{
					cell.setCellValue(measure.getRepairCost());
				}
				cell = row.getCell(16);
				if (cell == null)
				{
					cell = row.createCell(16);
				}
				if (measure.getFare() != null)
				{
					cell.setCellValue(measure.getFare());
				}
				cell = row.getCell(17);
				if (cell == null)
				{
					cell = row.createCell(17);
				}
				if (measure.getRemark() != null)
				{
					cell.setCellValue(measure.getRemark());
				}
			}
			workbook.write(zos);
			workbook.close();
			
			for (int i = 0; i < measures.size(); i++) {
				Measure measure = measures.get(i);
				zipEntry = new ZipEntry("原始记录" + (i+1) + ".xls");
				zos.putNextEntry(zipEntry);
				workbook = new HSSFWorkbook(getClass().getResourceAsStream("/yuanshijilu.xls"));
				sheet = workbook.getSheetAt(0);
				row = sheet.getRow(6);
				cell = row.getCell(5);
				cell.setCellValue(measure.getSampleName());
				cell = row.getCell(17);
				cell.setCellValue(circulationSheet.getCertificateUnit());
				row = sheet.getRow(7);
				cell = row.getCell(5);
				cell.setCellValue(measure.getModel());
				cell = row.getCell(17);
				cell.setCellValue(circulationSheet.getCertificateAddress());
				row = sheet.getRow(8);
				cell = row.getCell(5);
				cell.setCellValue(measure.getAccuracy());
				cell = row.getCell(17);
				sdf.applyPattern("yyyy");
				cell.setCellValue(sdf.format(measure.getTime()));
				cell = row.getCell(23);
				sdf.applyPattern("MM");
				cell.setCellValue(sdf.format(measure.getTime()));
				cell = row.getCell(26);
				sdf.applyPattern("dd");
				cell.setCellValue(sdf.format(measure.getTime()));
				row = sheet.getRow(9);
				cell = row.getCell(5);
				cell.setCellValue(measure.getSampleNumber());
				cell = row.getCell(17);
				sdf.applyPattern("yyyy");
				cell.setCellValue(sdf.format(measure.getExpiryTime()));
				cell = row.getCell(23);
				sdf.applyPattern("MM");
				cell.setCellValue(sdf.format(measure.getExpiryTime()));
				cell = row.getCell(26);
				sdf.applyPattern("dd");
				cell.setCellValue(sdf.format(measure.getExpiryTime()));
				row = sheet.getRow(10);
				cell = row.getCell(5);
				cell.setCellValue(measure.getManufacturer());
				cell = row.getCell(17);
				cell.setCellValue(measure.getTemperature());
				cell = row.getCell(27);
				cell.setCellValue(measure.getHumidity());
				row = sheet.getRow(12);
				cell = row.getCell(5);
				cell.setCellValue(measure.getAddress());
				row = sheet.getRow(13);
				cell = row.getCell(5);
				cell.setCellValue(measure.getReference1());
				row = sheet.getRow(14);
				cell = row.getCell(5);
				cell.setCellValue(measure.getReference2());
				row = sheet.getRow(17);
				cell = row.getCell(1);
				cell.setCellValue(measure.getStandardName1());
				cell = row.getCell(8);
				cell.setCellValue(measure.getStandardNumber1());
				cell = row.getCell(13);
				cell.setCellValue(measure.getStandardAccuracy1());
				cell = row.getCell(19);
				cell.setCellValue(measure.getStandardCertificateNumber1());
				row = sheet.getRow(18);
				cell = row.getCell(1);
				cell.setCellValue(measure.getStandardName2());
				cell = row.getCell(8);
				cell.setCellValue(measure.getStandardNumber2());
				cell = row.getCell(13);
				cell.setCellValue(measure.getStandardAccuracy2());
				cell = row.getCell(19);
				cell.setCellValue(measure.getStandardCertificateNumber2());
				row = sheet.getRow(19);
				cell = row.getCell(1);
				cell.setCellValue(measure.getStandardName3());
				cell = row.getCell(8);
				cell.setCellValue(measure.getStandardNumber3());
				cell = row.getCell(13);
				cell.setCellValue(measure.getStandardAccuracy3());
				cell = row.getCell(19);
				cell.setCellValue(measure.getStandardCertificateNumber3());
				if (measure.getUnit0() != null)
				{
					row = sheet.getRow(22);
					cell = row.getCell(2);
					cell.setCellValue(measure.getUnit0());
				}
				if (measure.getUnit1() != null)
				{
					row = sheet.getRow(22);
					cell = row.getCell(5);
					cell.setCellValue(measure.getUnit1());
					row = sheet.getRow(21);
					cell = row.getCell(13);
					cell.setCellValue("("+measure.getUnit1()+")");
					row = sheet.getRow(22);
					cell = row.getCell(19);
					cell.setCellValue("("+measure.getUnit1()+")");
				}
				row = sheet.getRow(21);
				cell = row.getCell(31);
				cell.setCellValue(measure.getDecimalDigits());
				for (Data data : dataRepository.findByMeasureId(measure.getId())) {
					int factor = 1;
					for (int j = 0; j < data.getDecimalDigit(); j++)
					{
						factor *= 10;
					}
					if (data.getRow() >= 1 && data.getRow() <= 5)
					{
						row = sheet.getRow(22 + data.getRow());
						if (data.getBaseValue0() != null)
						{
							cell = row.getCell(5);
							cell.setCellValue(data.getBaseValue0().doubleValue() / factor);
						}
						if (data.getBaseValue() != null)
						{
							cell = row.getCell(7);
							cell.setCellValue(data.getBaseValue().doubleValue() / factor);
						}
						if (data.getValue1() != null)
						{
							cell = row.getCell(8);
							cell.setCellValue(data.getValue1().doubleValue() / factor);
						}
						if (data.getValue2() != null)
						{
							cell = row.getCell(11);
							cell.setCellValue(data.getValue2().doubleValue() / factor);
						}
						if (data.getValue3() != null)
						{
							cell = row.getCell(14);
							cell.setCellValue(data.getValue3().doubleValue() / factor);
						}
						cell = row.getCell(32);
						cell.setCellValue(data.getDecimalDigit());
					}
					if (data.getRow() >= 7 && data.getRow() <= 11)
					{
						row = sheet.getRow(21 + data.getRow());
						if (data.getBaseValue0() != null)
						{
							cell = row.getCell(5);
							cell.setCellValue(data.getBaseValue0().doubleValue() / factor);
						}
						if (data.getBaseValue() != null)
						{
							cell = row.getCell(7);
							cell.setCellValue(data.getBaseValue().doubleValue() / factor);
						}
						if (data.getValue1() != null)
						{
							cell = row.getCell(8);
							cell.setCellValue(data.getValue1().doubleValue() / factor);
						}
						if (data.getValue2() != null)
						{
							cell = row.getCell(11);
							cell.setCellValue(data.getValue2().doubleValue() / factor);
						}
						if (data.getValue3() != null)
						{
							cell = row.getCell(14);
							cell.setCellValue(data.getValue3().doubleValue() / factor);
						}
						cell = row.getCell(32);
						cell.setCellValue(data.getDecimalDigit());
					}
				}
				if (measure.getReportType() == 1)
				{
					row = sheet.getRow(38);
					cell = row.getCell(3);
					cell.setCellValue(measure.getConclusion());
				}
				else if (measure.getReportType() == 2)
				{
					row = sheet.getRow(35);
					cell = row.getCell(10);
					cell.setCellValue(measure.getUncertainty());
				}
				
				if (measure.getType() != null)
				{
					row = sheet.getRow(35);
					cell = row.getCell(15);
					cell.setCellValue("2.测量类型："+measure.getType());
				}
				if (measure.getDirection() != null)
				{
					row = sheet.getRow(36);
					cell = row.getCell(15);
					cell.setCellValue("3.检定/校准方位："+measure.getDirection());
				}
				
				workbook.write(zos);
				workbook.close();
				if (measure.getReportType() == 1) // 检定报告
				{
					zipEntry = new ZipEntry("证书" + (i+1) + ".xls");
					zos.putNextEntry(zipEntry);
					workbook = new HSSFWorkbook(getClass().getResourceAsStream("/jianding.xls"));
					sheet = workbook.getSheetAt(0);
					row = sheet.getRow(8);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference1());
					row = sheet.getRow(9);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference2());
					row = sheet.getRow(10);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference3());
					row = sheet.getRow(13);
					cell = row.getCell(4);
					cell.setCellValue(measure.getAddress());
					row = sheet.getRow(15);
					cell = row.getCell(4);
					cell.setCellValue(measure.getTemperature());
					cell = row.getCell(16);
					cell.setCellValue(measure.getHumidity());
					row = sheet.getRow(21);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName1());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber1());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy1());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber1());
					row = sheet.getRow(22);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName2());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber2());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy2());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber2());
					row = sheet.getRow(23);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName3());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber3());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy3());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber3());
					row = sheet.getRow(30);
					cell = row.getCell(28);
					if (measure.getUnit0() != null)
					{
						cell.setCellValue("("+measure.getUnit0()+")");
					}
					cell = row.getCell(31);
					cell.setCellValue(measure.getUnit1());
					cell = row.getCell(33);
					cell.setCellValue(measure.getDecimalDigits());
					if (measure.getDirection() != null)
					{
						row = sheet.getRow(37);
						cell = row.getCell(3);
						cell.setCellValue("检定方位："+measure.getDirection());
					}
					
					sheet = workbook.getSheetAt(1);
					for (Data data : dataRepository.findByMeasureId(measure.getId())) {
						int factor = 1;
						for (int j = 0; j < data.getDecimalDigit(); j++)
						{
							factor *= 10;
						}
						if (data.getRow() >= 1 && data.getRow() <= 3)
						{
							row = sheet.getRow(16 + data.getRow());
							if (data.getBaseValue0() != null)
							{
								cell = row.getCell(4);
								cell.setCellValue(data.getBaseValue0().doubleValue() / factor);
							}
							if (data.getBaseValue() != null)
							{
								cell = row.getCell(5);
								cell.setCellValue(data.getBaseValue().doubleValue() / factor);
							}
							if (data.getValue1() != null)
							{
								cell = row.getCell(6);
								cell.setCellValue(data.getValue1().doubleValue() / factor);
							}
							if (data.getValue2() != null)
							{
								cell = row.getCell(7);
								cell.setCellValue(data.getValue2().doubleValue() / factor);
							}
							if (data.getValue3() != null)
							{
								cell = row.getCell(8);
								cell.setCellValue(data.getValue3().doubleValue() / factor);
							}
							cell = row.getCell(9);
							cell.setCellValue(data.getDecimalDigit());
						}
						if (data.getRow() >= 7 && data.getRow() <= 9)
						{
							row = sheet.getRow(16 + data.getRow());
							if (data.getBaseValue0() != null)
							{
								cell = row.getCell(4);
								cell.setCellValue(data.getBaseValue0().doubleValue() / factor);
							}
							if (data.getBaseValue() != null)
							{
								cell = row.getCell(5);
								cell.setCellValue(data.getBaseValue().doubleValue() / factor);
							}
							if (data.getValue1() != null)
							{
								cell = row.getCell(6);
								cell.setCellValue(data.getValue1().doubleValue() / factor);
							}
							if (data.getValue2() != null)
							{
								cell = row.getCell(7);
								cell.setCellValue(data.getValue2().doubleValue() / factor);
							}
							if (data.getValue3() != null)
							{
								cell = row.getCell(8);
								cell.setCellValue(data.getValue3().doubleValue() / factor);
							}
							cell = row.getCell(9);
							cell.setCellValue(data.getDecimalDigit());
						}
					}
					workbook.write(zos);
					workbook.close();
				}
				else if (measure.getReportType() == 2) // 校准报告
				{
					zipEntry = new ZipEntry("证书" + (i+1) + ".xls");
					zos.putNextEntry(zipEntry);
					workbook = new HSSFWorkbook(getClass().getResourceAsStream("/jiaozhun.xls"));
					sheet = workbook.getSheetAt(0);
					row = sheet.getRow(8);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference1());
					row = sheet.getRow(9);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference2());
					row = sheet.getRow(10);
					cell = row.getCell(1);
					cell.setCellValue(measure.getReference3());
					row = sheet.getRow(13);
					cell = row.getCell(4);
					cell.setCellValue(measure.getAddress());
					row = sheet.getRow(15);
					cell = row.getCell(4);
					cell.setCellValue(measure.getTemperature());
					cell = row.getCell(16);
					cell.setCellValue(measure.getHumidity());
					row = sheet.getRow(21);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName1());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber1());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy1());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber1());
					row = sheet.getRow(22);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName2());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber2());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy2());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber2());
					row = sheet.getRow(23);
					cell = row.getCell(1);
					cell.setCellValue(measure.getStandardName3());
					cell = row.getCell(6);
					cell.setCellValue(measure.getStandardNumber3());
					cell = row.getCell(10);
					cell.setCellValue(measure.getStandardAccuracy3());
					cell = row.getCell(20);
					cell.setCellValue(measure.getStandardCertificateNumber3());
					
					sheet = workbook.getSheetAt(1);
					row = sheet.getRow(7);
					cell = row.getCell(29);
					if (measure.getUnit0() != null)
					{
						cell.setCellValue("("+measure.getUnit0()+")");
					}
					cell = row.getCell(30);
					cell.setCellValue(measure.getUnit1());
					cell = row.getCell(32);
					cell.setCellValue(measure.getDecimalDigits());
					row = sheet.getRow(21);
					cell = row.getCell(14);
					cell.setCellValue(measure.getUncertainty());
					if (measure.getDirection() != null)
					{
						row = sheet.getRow(20);
						cell = row.getCell(3);
						cell.setCellValue("检定方位："+measure.getDirection());
					}
					
					sheet = workbook.getSheetAt(2);
					for (Data data : dataRepository.findByMeasureId(measure.getId())) {
						int factor = 1;
						for (int j = 0; j < data.getDecimalDigit(); j++)
						{
							factor *= 10;
						}
						if (data.getRow() >= 1 && data.getRow() <= 12)
						{
							row = sheet.getRow(16 + data.getRow());
							if (data.getBaseValue0() != null)
							{
								cell = row.getCell(4);
								cell.setCellValue(data.getBaseValue0().doubleValue() / factor);
							}
							if (data.getBaseValue() != null)
							{
								cell = row.getCell(5);
								cell.setCellValue(data.getBaseValue().doubleValue() / factor);
							}
							if (data.getValue1() != null)
							{
								cell = row.getCell(6);
								cell.setCellValue(data.getValue1().doubleValue() / factor);
							}
							if (data.getValue2() != null)
							{
								cell = row.getCell(7);
								cell.setCellValue(data.getValue2().doubleValue() / factor);
							}
							if (data.getValue3() != null)
							{
								cell = row.getCell(8);
								cell.setCellValue(data.getValue3().doubleValue() / factor);
							}
							cell = row.getCell(9);
							cell.setCellValue(data.getDecimalDigit());
						}
					}
					
					workbook.write(zos);
					workbook.close();
				}
			}
			
			zos.close();
		} catch (IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("导出excel异常:", e);
			throw new HttpInternalServerErrorException(new ReturnApi(-1, "导出excel失败"));
		}
	}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CirculationSheetRepository circulationSheetRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MeasureRepository measureRepository;
	
	@Autowired
	private DataRepository dataRepository;
	
	private CirculationApi convertToCirculationApi(CirculationSheet circulationSheet)
	{
		CirculationApi circulationApi = new CirculationApi();
		circulationApi.setId(circulationSheet.getId());
		circulationApi.setCertificateUnit(circulationSheet.getCertificateUnit());
		circulationApi.setNumber(circulationSheet.getNumber());
		circulationApi.setCertificateAddress(circulationSheet.getCertificateAddress());
		
		return circulationApi;
	}
}
