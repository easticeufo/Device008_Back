package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.ModelApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.Model;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.ModelRepository;

@Service
public class ModelService {

	public List<ModelApi> getModels() {
		List<ModelApi> models = new LinkedList<>();
		
		for (Model model : modelRepository.findAll()) {
			models.add(convertToModelApi(model));
		}
		
		return models;
	}
	
	public ModelApi getModel(int modelId) {
		Model model = modelRepository.findOne(modelId);
		if (model == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的型号"));
		}
		
		return convertToModelApi(model);
	}
	
	public ModelApi addModel(ModelApi modelApi) {
		Model model = new Model();
		model.setName(modelApi.getName());
		modelRepository.save(model);
		
		return convertToModelApi(model);
	}
	
	public ModelApi updateModel(int modelId, ModelApi modelApi) {
		Model model = modelRepository.findOne(modelId);
		if (model == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "未找到对应的型号"));
		}
		
		if (modelApi.getName() != null)
		{
			model.setName(modelApi.getName());
		}
		modelRepository.save(model);
		
		return convertToModelApi(model);
	}
	
	public void deleteModel(int modelId) {
		modelRepository.delete(modelId);
	}
	
	@Autowired
	private ModelRepository modelRepository;
	
	private ModelApi convertToModelApi(Model model)
	{
		ModelApi modelApi = new ModelApi();
		modelApi.setId(model.getId());
		modelApi.setName(model.getName());
		
		return modelApi;
	}
}
