package com.madongfang.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madongfang.api.CertificateApi;
import com.madongfang.api.ReturnApi;
import com.madongfang.entity.Certificate;
import com.madongfang.exception.HttpNotFoundException;
import com.madongfang.repository.CertificateRepository;

@Service
public class CertificateService {

	public List<CertificateApi> getCertificates() {
		List<CertificateApi> certificates = new LinkedList<>();
		
		for (Certificate certificate : certificateRepository.findAll()) {
			certificates.add(convertToCertificateApi(certificate));
		}
		
		return certificates;
	}
	
	public CertificateApi getCertificate(int certificateId) {
		Certificate certificate = certificateRepository.findOne(certificateId);
		if (certificate == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "不存在的证书"));
		}
		return convertToCertificateApi(certificate);
	}
	
	public CertificateApi addCertificate(CertificateApi certificateApi) {
		Certificate certificate = new Certificate();
		certificate.setUnit(certificateApi.getUnit());
		certificate.setAddress(certificateApi.getAddress());
		certificateRepository.save(certificate);
		
		return convertToCertificateApi(certificate);
	}
	
	public CertificateApi updateCertificate(int certificateId, CertificateApi certificateApi) {
		Certificate certificate = certificateRepository.findOne(certificateId);
		if (certificate == null)
		{
			throw new HttpNotFoundException(new ReturnApi(-1, "不存在的证书"));
		}
		
		if (certificateApi.getUnit() != null)
		{
			certificate.setUnit(certificateApi.getUnit());
		}
		if (certificateApi.getAddress() != null)
		{
			certificate.setAddress(certificateApi.getAddress());
		}
		certificateRepository.save(certificate);
		
		return convertToCertificateApi(certificate);
	}
	
	public void deleteCertificate(int certificateId) {
		certificateRepository.delete(certificateId);
	}
	
	@Autowired
	private CertificateRepository certificateRepository;
	
	private CertificateApi convertToCertificateApi(Certificate certificate)
	{
		CertificateApi certificateApi = new CertificateApi();
		certificateApi.setId(certificate.getId());
		certificateApi.setUnit(certificate.getUnit());
		certificateApi.setAddress(certificate.getAddress());
		return certificateApi;
	}
}
