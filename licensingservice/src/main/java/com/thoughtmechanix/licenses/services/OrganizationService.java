package com.thoughtmechanix.licenses.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtmechanix.licenses.clients.OrganizationDiscoveryClient;
import com.thoughtmechanix.licenses.clients.OrganizationFeignClient;
import com.thoughtmechanix.licenses.clients.OrganizationRestTemplateClient;
import com.thoughtmechanix.licenses.model.Organization;

@Service
public class OrganizationService {
	
	@Autowired
	private OrganizationDiscoveryClient organizationDiscoveryClient;

	@Autowired
	private OrganizationRestTemplateClient organizationRestTemplateClient;

	@Autowired
	private OrganizationFeignClient organizationFeignClient;
	
	@HystrixCommand(fallbackMethod="buildFallbackOrganization", threadPoolKey="orgThreadPool")
	public Organization getOrganizationById(String organizationId) {
		return retrieveOrgInfo(organizationId, "feign");
	}
	
	@HystrixCommand(fallbackMethod="buildFallbackOrganization", threadPoolKey="orgThreadPool")
	public Organization getOrganizationById(String organizationId, String clientType) {
		return retrieveOrgInfo(organizationId, clientType);
	}

	private Organization retrieveOrgInfo(String organizationId, String clientType) {
		if ("discovery".equals(clientType)) {
			return organizationDiscoveryClient.getOrganization(organizationId);
		}
		if ("rest".equals(clientType)) {
			return organizationRestTemplateClient.getOrganization(organizationId);
		}
		if ("feign".equals(clientType)) {
			return organizationFeignClient.getOrganization(organizationId);
		}

		return null;
	}
	
	private Organization buildFallbackOrganization(String organizationId, String clientType) {
		return buildFallbackOrganization(organizationId);
	}
	
	private Organization buildFallbackOrganization(String organizationId) {
		Organization organization = new Organization();
		organization.setId(organizationId);
		organization.setNome("Não foi possível recuperar a organização");
		return organization;
	}
	
}
