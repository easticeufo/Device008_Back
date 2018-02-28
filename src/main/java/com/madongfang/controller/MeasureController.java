package com.madongfang.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madongfang.api.DataApi;
import com.madongfang.api.MeasureApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.exception.HttpInternalServerErrorException;
import com.madongfang.service.MeasureService;

@RestController
@RequestMapping(value="/api/measures")
public class MeasureController {

	@GetMapping
	public List<MeasureApi> getMeasures(@RequestParam int circulationId)
	{	
		return measureService.getMeasures(circulationId);
		
	}
	
	@GetMapping(value="/{measureId}")
	public MeasureApi getMeasure(@PathVariable Integer measureId)
	{
		return measureService.getMeasure(measureId);
	}
	
	@PostMapping
	public MeasureApi addMeasure(@RequestBody MeasureApi measureApi)
	{
		return measureService.addMeasure(measureApi);
	}
	
	@PutMapping(value="/{measureId}")
	public MeasureApi updateMeasure(@PathVariable Integer measureId, @RequestBody MeasureApi measureApi)
	{
		return measureService.updateMeasure(measureId, measureApi);
	}
	
	@DeleteMapping(value="/{measureId}")
	public void deleteMeasure(@PathVariable Integer measureId)
	{
		measureService.deleteMeasure(measureId);
	}
	
	@PostMapping(value="/{measureId}/addDatas")
	public ReturnApi addDatas(@PathVariable int measureId, @RequestBody List<Integer> dataIds)
	{
		return measureService.addDatas(measureId, dataIds);
	}
	
	@GetMapping(value="/{measureId}/excel/jianding")
	public String exportJianding(HttpServletResponse response, @PathVariable Integer measureId)
	{
		MeasureApi measureApi = measureService.getMeasure(measureId);
		try {
			response.setContentType("application/binary;charset=ISO8859_1");
			String filename = new String("扭矩扳子_检定.xls".getBytes(), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			exportExcelJianding(response.getOutputStream(), measureApi);
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("导出excel异常:", e);
			throw new HttpInternalServerErrorException(new ReturnApi(-1, "导出excel失败"));
		}
		
		return null;
	}
	
	@GetMapping(value="/{measureId}/excel/jiaozhun")
	public String exportJiaozhun(HttpServletResponse response, @PathVariable Integer measureId)
	{
		MeasureApi measureApi = measureService.getMeasure(measureId);
		try {
			response.setContentType("application/binary;charset=ISO8859_1");
			String filename = new String("扭矩扳子_校准.xls".getBytes(), "ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=" + filename);
			exportExcelJiaozhun(response.getOutputStream(), measureApi);
		} catch (IllegalArgumentException | IllegalAccessException | IOException e) {
			// TODO Auto-generated catch block
			logger.error("导出excel异常:", e);
			throw new HttpInternalServerErrorException(new ReturnApi(-1, "导出excel失败"));
		}
		
		return null;
	}
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MeasureService measureService;
	
	private void exportExcelJianding(OutputStream out, MeasureApi measureApi) throws IllegalArgumentException, IllegalAccessException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(getClass().getResourceAsStream("/jianding.xls"));
		HSSFSheet sheet = workbook.getSheetAt(2);
		
		setExcelSheetData(sheet, measureApi);
		
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void exportExcelJiaozhun(OutputStream out, MeasureApi measureApi) throws IllegalArgumentException, IllegalAccessException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(getClass().getResourceAsStream("/jiaozhun.xls"));
		HSSFSheet sheet = workbook.getSheetAt(3);
		
		setExcelSheetData(sheet, measureApi);
		
		try {
			workbook.write(out);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void setExcelSheetData(HSSFSheet sheet, MeasureApi measureApi)
	{
		HSSFRow row;
		HSSFCell cell;
		row = sheet.getRow(8);
		cell = row.getCell(3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		cell.setCellValue(sdf.format(measureApi.getTime()));
		cell = row.getCell(4);
		sdf.applyPattern("MM");
		cell.setCellValue(sdf.format(measureApi.getTime()));
		cell = row.getCell(5);
		sdf.applyPattern("dd");
		cell.setCellValue(sdf.format(measureApi.getTime()));
		
		row = sheet.getRow(22);
		cell = row.getCell(1);
		cell.setCellValue(measureApi.getUnit0());
		cell = row.getCell(2);
		cell.setCellValue(measureApi.getUnit1());
		
		for (DataApi dataApi : measureApi.getDatas()) {
			int rowNumber = dataApi.getRow();
			if (rowNumber < 1 || rowNumber > 12)
			{
				logger.warn("数据行号错误：row={}", rowNumber);
				continue;
			}
			
			int factor = 1;
			for (int i = 0; i < dataApi.getDecimalDigit(); i++)
			{
				factor *= 10;
			}
			
			row = sheet.getRow(22 + rowNumber);
			cell = row.getCell(1);
			cell.setCellValue(dataApi.getBaseValue0().doubleValue() / factor);
			cell = row.getCell(2);
			cell.setCellValue(dataApi.getBaseValue().doubleValue() / factor);
			cell = row.getCell(3);
			cell.setCellValue(dataApi.getValue1().doubleValue() / factor);
			cell = row.getCell(4);
			cell.setCellValue(dataApi.getValue2().doubleValue() / factor);
			cell = row.getCell(5);
			cell.setCellValue(dataApi.getValue3().doubleValue() / factor);
			cell = row.getCell(9);
			if (cell == null)
			{
				cell = row.createCell(9);
			}
			cell.setCellValue(dataApi.getDecimalDigit());
			
		}
	}
}
