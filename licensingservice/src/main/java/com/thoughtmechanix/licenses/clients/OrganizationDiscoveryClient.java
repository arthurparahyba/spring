package com.thoughtmechanix.licenses.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.thoughtmechanix.licenses.model.Organization;

@Component
public class OrganizationDiscoveryClient {

	@Autowired
	private EurekaClient eurekaClient;
	
	public Organization getOrganization(String organizationId) {
		RestTemplate restTemplate = new RestTemplate();
		
		List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress("organizationservice", false);
		if(instances.isEmpty()) return null;
		
		String serviceUri = String.format("%sorganization/v1/%s",instances.get(0).getHomePageUrl(), organizationId);
		
		ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUri, HttpMethod.GET, null, Organization.class, organizationId);
		
		return restExchange.getBody();
	}
	
}
