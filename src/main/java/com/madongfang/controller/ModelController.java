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

import com.madongfang.api.ModelApi;
import com.madongfang.service.ModelService;

@RestController
@RequestMapping(value="/api/models")
public class ModelController {

	@GetMapping
	public List<ModelApi> getModels()
	{
		return modelService.getModels();
	}
	
	@GetMapping(value="/{modelId}")
	public ModelApi getModel(@PathVariable int modelId)
	{
		return modelService.getModel(modelId);
	}
	
	@PostMapping
	public ModelApi addModel(@RequestBody ModelApi modelApi)
	{
		return modelService.addModel(modelApi);
	}
	
	@PutMapping(value="/{modelId}")
	public ModelApi updateModel(@PathVariable int modelId, @RequestBody ModelApi modelApi)
	{
		return modelService.updateModel(modelId, modelApi);
	}
	
	@DeleteMapping(value="/{modelId}")
	public void deleteModel(@PathVariable int modelId)
	{
		modelService.deleteModel(modelId);
	}
	
	@Autowired
	private ModelService modelService;
}
