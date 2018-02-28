package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.ManufacturerApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.Manufacturer;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.ManufacturerRepository;

@Service
public class ManufacturerService {

	public List<ManufacturerApi> getManufacturers()
	{
		List<ManufacturerApi> manufacturers = new LinkedList<>();
		
		for (Manufacturer manufacturer : manufacturerRepository.findAll()) {
			manufacturers.add(convertToManufacturerApi(manufacturer));
		}
		
		return manufacturers;
	}
	
	public ManufacturerApi getManufacturer(Integer manufacturerId) {
		Manufacturer manufacturer = manufacturerRepository.findOne(manufacturerId);
		if (manufacturer == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "不存在的厂商"));
		}
		
		return convertToManufacturerApi(manufacturer);
	}
	
	public ManufacturerApi addManufacturer(ManufacturerApi manufacturerApi) {
		Manufacturer manufacturer = new Manufacturer();
		manufacturer.setName(manufacturerApi.getName());
		manufacturerRepository.save(manufacturer);
		
		return convertToManufacturerApi(manufacturer);
	}
	
	public ManufacturerApi updateManufacturer(Integer manufacturerId, ManufacturerApi manufacturerApi) {
		Manufacturer manufacturer = manufacturerRepository.findOne(manufacturerId);
		if (manufacturer == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "不存在的厂商"));
		}
		
		if (manufacturerApi.getName() != null)
		{
			manufacturer.setName(manufacturerApi.getName());
		}
		
		manufacturerRepository.save(manufacturer);
		
		return convertToManufacturerApi(manufacturer);
	}
	
	public void deleteManufacturer(Integer manufacturerId) {
		manufacturerRepository.delete(manufacturerId);
	}
	
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	
	private ManufacturerApi convertToManufacturerApi(Manufacturer manufacturer)
	{
		ManufacturerApi manufacturerApi = new ManufacturerApi();
		manufacturerApi.setId(manufacturer.getId());
		manufacturerApi.setName(manufacturer.getName());
		
		return manufacturerApi;
	}
}
