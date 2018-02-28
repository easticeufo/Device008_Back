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

import com.madongfang.api.ReferenceApi;
import com.madongfang.service.ReferenceService;

@RestController
@RequestMapping(value="/api/references")
public class ReferenceController {

	@GetMapping
	public List<ReferenceApi> getReferences()
	{
		return referenceService.getReferences();
	}
	
	@GetMapping(value="/{referenceId}")
	public ReferenceApi getReference(@PathVariable int referenceId)
	{
		return referenceService.getReference(referenceId);
	}
	
	@PostMapping
	public ReferenceApi addReference(@RequestBody ReferenceApi referenceApi)
	{
		return referenceService.addReference(referenceApi);
	}
	
	@PutMapping(value="/{referenceId}")
	public ReferenceApi updateReference(@PathVariable int referenceId, @RequestBody ReferenceApi referenceApi)
	{
		return referenceService.updateReference(referenceId, referenceApi);
	}
	
	@DeleteMapping(value="/{referenceId}")
	public void deleteReference(@PathVariable int referenceId)
	{
		referenceService.deleteReference(referenceId);
	}
	
	@Autowired
	private ReferenceService referenceService;
}
