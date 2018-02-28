package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.ReferenceApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.Reference;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.ReferenceRepository;

@Service
public class ReferenceService {

	public List<ReferenceApi> getReferences()
	{
		List<ReferenceApi> references = new LinkedList<>();
		
		for (Reference reference : referenceRepository.findAll()) {
			references.add(convertToReferenceApi(reference));
		}
		
		return references;
	}
	
	public ReferenceApi getReference(int referenceId) {
		Reference reference = referenceRepository.findOne(referenceId);
		if (reference == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的依据"));
		}
		
		return convertToReferenceApi(reference);
	}
	
	public ReferenceApi addReference(ReferenceApi referenceApi) {
		Reference reference = new Reference();
		reference.setName(referenceApi.getName());
		referenceRepository.save(reference);
		
		return convertToReferenceApi(reference);
	}
	
	public ReferenceApi updateReference(int referenceId, ReferenceApi referenceApi) {
		Reference reference = referenceRepository.findOne(referenceId);
		if (reference == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的依据"));
		}
		
		if (referenceApi.getName() != null)
		{
			reference.setName(referenceApi.getName());
		}
		referenceRepository.save(reference);
		
		return convertToReferenceApi(reference);
	}
	
	public void deleteReference(int referenceId) {
		referenceRepository.delete(referenceId);
	}
	
	@Autowired
	private ReferenceRepository referenceRepository;
	
	private ReferenceApi convertToReferenceApi(Reference reference) {
		ReferenceApi referenceApi = new ReferenceApi();
		referenceApi.setId(reference.getId());
		referenceApi.setName(reference.getName());
		
		return referenceApi;
	}
}
