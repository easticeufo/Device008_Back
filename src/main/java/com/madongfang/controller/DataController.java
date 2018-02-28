package com.madongfang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madongfang.api.DataApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.service.DataService;

@RestController
@RequestMapping(value="/api/datas")
public class DataController {

	@GetMapping
	public List<DataApi> getDatas(@RequestParam int measureId)
	{
		return dataService.getDatas(measureId);
	}
	
	@PostMapping
	public ReturnApi addDatas(@RequestBody List<DataApi> datas)
	{
		return dataService.addDatas(datas);
	}
	
	@Autowired
	private DataService dataService;
}
