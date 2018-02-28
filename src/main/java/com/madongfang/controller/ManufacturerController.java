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

import com.madongfang.api.ManufacturerApi;
import com.madongfang.service.ManufacturerService;

@RestController
@RequestMapping(value="/api/manufacturers")
public class ManufacturerController {

	@GetMapping
	public List<ManufacturerApi> getManufacturers() {
		return manufacturerService.getManufacturers();
	}
	
	@GetMapping(value="/{manufacturerId}")
	public ManufacturerApi getManufacturer(@PathVariable Integer manufacturerId)
	{
		return manufacturerService.getManufacturer(manufacturerId);
	}
	
	@PostMapping
	public ManufacturerApi addManufacturer(@RequestBody ManufacturerApi manufacturerApi)
	{
		return manufacturerService.addManufacturer(manufacturerApi);
	}
	
	@PutMapping(value="/{manufacturerId}")
	public ManufacturerApi addManufacturer(@PathVariable Integer manufacturerId, @RequestBody ManufacturerApi manufacturerApi)
	{
		return manufacturerService.updateManufacturer(manufacturerId, manufacturerApi);
	}
	
	@DeleteMapping(value="/{manufacturerId}")
	public void deleteManufacturer(@PathVariable Integer manufacturerId)
	{
		manufacturerService.deleteManufacturer(manufacturerId);
	}
	
	@Autowired
	private ManufacturerService manufacturerService;
}
