package com.madongfang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.madongfang.api.StandardApi;
import com.madongfang.service.StandardService;

@RestController
@RequestMapping(value="/api/standards")
public class StandardController {

	@GetMapping
	public List<StandardApi> getStandards()
	{
		return standardService.getStandards();
	}
	
	@GetMapping(value="/{standardId}")
	public StandardApi getStandard(@PathVariable int standardId)
	{
		return standardService.getStandard(standardId);
	}
	
	@PostMapping
	public StandardApi addStandard(@RequestBody StandardApi standardApi)
	{
		return standardService.addStandard(standardApi);
	}
	
	@PutMapping(value="/{standardId}")
	public StandardApi updateStandard(@PathVariable int standardId, @RequestBody StandardApi standardApi)
	{
		return standardService.updateStandard(standardId, standardApi);
	}
	
	@DeleteMapping(value="/{standardId}")
	public void deleteStandard(@PathVariable int standardId)
	{
		standardService.deleteStandard(standardId);
	}
	
	@Autowired
	private StandardService standardService;
}
