package com.thoughtmechanix.licenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.services.LicenseService;
import com.thoughtmechanix.licenses.services.OrganizationService;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicenseServiceController {
	
	@Autowired
	private LicenseService licenseService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<License> getLicensesByOrg(
			@PathVariable("organizationId") String organizationId){
		
		List<License> licenses = licenseService.getLicensesByOrg(organizationId);
		licenses.stream()
		.forEach(license -> {
			Organization org = organizationService.getOrganizationById(license.getOrganizationId());
			license.withContactName(org.getContactName()).withOrganizationName(org.getNome())
			.withContactEmail(org.getContactEmail()).withContactPhone(org.getContactPhone());
		});

		return licenses;
	}
	
	@RequestMapping(value="/{licenseId}/{clientType}", method=RequestMethod.GET)
	public License getLicenseWithClient(
			@PathVariable("organizationId")String organizationId,
			@PathVariable("licenseId")String licenseId,
			@PathVariable("clientType")String clientType) {
		
		return licenseService.getLicense(organizationId, licenseId, clientType);
	}

	@RequestMapping(value="/{licenseId}", method=RequestMethod.GET)
	public License getLicenses(
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId) {
		
		License license = licenseService.getLicense(organizationId, licenseId);
		
		return license;
	}
	
}
