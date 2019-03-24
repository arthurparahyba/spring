package com.thoughtmechanix.licenses.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;
	
	@Autowired
	private OrganizationDiscoveryClient organizationDiscoveryClient;
	
	@Autowired
	private OrganizationRestTemplateClient organizationRestTemplateClient;
	
	@Autowired
	private OrganizationFeignClient organizationFeignClient;
	
	public License getLicense(String organizationId, String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license;
	}
	
	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		
		Organization org = retrieveOrgInfo(organizationId, clientType);
		
		return license.withOrganizationName(org.getNome())
					.withContactName(org.getContactName())
					.withContactEmail(org.getContactEmail())
					.withContactPhone(org.getContactPhone());
	}
	
	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		if("discovery".equals(clientType)) {
			return organizationDiscoveryClient.getOrganization(organizationId);
		}
		if("rest".equals(clientType)) {
			return organizationRestTemplateClient.getOrganization(organizationId);
		}
		if("feign".equals(clientType)) {
			return organizationFeignClient.getOrganization(organizationId);
		}
		
		return null;
	}

	public List<License> getLicensesByOrg(String organizationId) {
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}

}
