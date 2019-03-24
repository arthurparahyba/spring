package com.thoughtmechanix.organization.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.services.OrganizationService;

@RestController
@RequestMapping(value="/v1")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value="/{organizationId}")
	public Organization getOrganizationById(
			@PathVariable("organizationId") String organizationId) {
		
		return organizationService.getOrganization(organizationId).orElse(null);
	}
}
