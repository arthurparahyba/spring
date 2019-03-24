package com.thoughtmechanix.organization.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtmechanix.organization.model.Organization;
import com.thoughtmechanix.organization.repository.OrganizationRepository;

@Service
public class OrganizationService {
	
	@Autowired
	private OrganizationRepository organizationRepository;

	public Optional<Organization> getOrganization(String organizationId) {
		
		return organizationRepository.findById(organizationId);
	}

}
