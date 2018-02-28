package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.ReturnApi;
import com.madongfang.api.StandardApi;
import com.madongfang.entity.Standard;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.StandardRepository;

@Service
public class StandardService {

	public List<StandardApi> getStandards() {
		List<StandardApi> standards = new LinkedList<>();
		
		for (Standard standard : standardRepository.findAll()) {
			standards.add(convertToStandardApi(standard));
		}
		
		return standards;
	}
	
	public StandardApi getStandard(int standardId) {
		Standard standard = standardRepository.findOne(standardId);
		if (standard == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的标准器"));
		}
		
		return convertToStandardApi(standard);
	}
	
	public StandardApi addStandard(StandardApi standardApi) {
		Standard standard = new Standard();
		standard.setName(standardApi.getName());
		standard.setAccuracy(standardApi.getAccuracy());
		standard.setCertificateNumber(standardApi.getCertificateNumber());
		standard.setNumber(standardApi.getNumber());
		standardRepository.save(standard);
		
		return convertToStandardApi(standard);
	}
	
	public StandardApi updateStandard(int standardId, StandardApi standardApi)
	{
		Standard standard = standardRepository.findOne(standardId);
		if (standard == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的标准器"));
		}
		
		if (standardApi.getName() != null)
		{
			standard.setName(standardApi.getName());
		}
		if (standardApi.getAccuracy() != null)
		{
			standard.setAccuracy(standardApi.getAccuracy());
		}
		if (standardApi.getCertificateNumber() != null)
		{
			standard.setCertificateNumber(standardApi.getCertificateNumber());
		}
		if (standardApi.getNumber() != null)
		{
			standard.setNumber(standardApi.getNumber());
		}
		standardRepository.save(standard);
		
		return convertToStandardApi(standard);
	}
	
	public void deleteStandard(int standardId) {
		standardRepository.delete(standardId);
	}
	
	@Autowired
	private StandardRepository standardRepository;
	
	private StandardApi convertToStandardApi(Standard standard)
	{
		StandardApi standardApi = new StandardApi();
		standardApi.setId(standard.getId());
		standardApi.setName(standard.getName());
		standardApi.setAccuracy(standard.getAccuracy());
		standardApi.setCertificateNumber(standard.getCertificateNumber());
		standardApi.setNumber(standard.getNumber());
		
		return standardApi;
	}
}
