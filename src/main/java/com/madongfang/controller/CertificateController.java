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

import com.madongfang.api.CertificateApi;
import com.madongfang.service.CertificateService;

@RestController
@RequestMapping(value="/api/certificates")
public class CertificateController {

	@GetMapping
	private List<CertificateApi> getCertificates() {
		return certificateService.getCertificates();
	}
	
	@GetMapping(value="/{certificateId}")
	public CertificateApi getCertificate(@PathVariable int certificateId)
	{
		return certificateService.getCertificate(certificateId);
	}
	
	@PostMapping
	public CertificateApi addCertificate(@RequestBody CertificateApi certificateApi)
	{
		return certificateService.addCertificate(certificateApi);
	}
	
	@PutMapping(value="/{certificateId}")
	public CertificateApi updateCertificate(@PathVariable int certificateId, @RequestBody CertificateApi certificateApi)
	{
		return certificateService.updateCertificate(certificateId, certificateApi);
	}
	
	@DeleteMapping(value="/{certificateId}")
	public void deleteCertificate(@PathVariable int certificateId)
	{
		certificateService.deleteCertificate(certificateId);
	}
	
	@Autowired
	private CertificateService certificateService;
}
