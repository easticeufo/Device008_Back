package com.madongfang.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.madongfang.api.CirculationApi;
import com.madongfang.entity.User;
import com.madongfang.service.CirculationService;

@RestController
@RequestMapping(value="/api/circulations")
public class CirculationController {

	@GetMapping
	public List<CirculationApi> getCirculations(@SessionAttribute User user)
	{
		return circulationService.getCirculations(user.getId());
	}
	
	@GetMapping(value="/{circulationId}")
	public CirculationApi getCirculation(@PathVariable Integer circulationId)
	{
		return circulationService.getCirculation(circulationId);
	}
	
	@PostMapping
	public CirculationApi addCirculation(@SessionAttribute User user, @RequestBody CirculationApi circulationApi)
	{
		return circulationService.addCirculation(user.getId(), circulationApi);
	}
	
	@PutMapping(value="/{circulationId}")
	public CirculationApi updateCirculation(@PathVariable Integer circulationId,  @RequestBody CirculationApi circulationApi)
	{
		return circulationService.updateCirculation(circulationId, circulationApi);
	}
	
	@DeleteMapping(value="/{circulationId}")
	public void deleteCirculation(@PathVariable Integer circulationId)
	{
		circulationService.deleteCirculation(circulationId);
	}
	
	@GetMapping(value="/{circulationId}/export")
	public String export(HttpServletResponse response, @PathVariable Integer circulationId)
	{
		circulationService.export(response, circulationId);
		return null;
	}
	
	@Autowired
	private CirculationService circulationService;
}
