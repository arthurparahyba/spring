package com.thoughtmechanix.licenses.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thoughtmechanix.licenses.model.License;
import com.thoughtmechanix.licenses.model.Organization;
import com.thoughtmechanix.licenses.repository.LicenseRepository;

@Service
public class LicenseService {
	
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private LicenseRepository licenseRepository;


	public License getLicense(String organizationId, String licenseId) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
		return license;
	}

	public License getLicense(String organizationId, String licenseId, String clientType) {
		License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

		Organization org = organizationService.getOrganizationById(organizationId, clientType);

		return license.withOrganizationName(org.getNome()).withContactName(org.getContactName()).withOrganizationName(org.getNome())
				.withContactEmail(org.getContactEmail()).withContactPhone(org.getContactPhone());
	}


//	@HystrixCommand(commandProperties= {
//			@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="12000")
//	})
	@HystrixCommand(fallbackMethod="buildFallbackLicenseList",
			threadPoolKey="licensesByOrgThreadPool",
			threadPoolProperties= {
					@HystrixProperty(name="coreSize", value="25"),
					@HystrixProperty(name="maxQueueSize", value="10")
			})
	public List<License> getLicensesByOrg(String organizationId) {
		//randomlyRunLong();
		
		return licenseRepository.findByOrganizationId(organizationId);
	}
	
	private List<License> buildFallbackLicenseList(String organizationId){
		
		List<License> fallbackList = new ArrayList<>();
		License license  = new License()
				.withId("00000000")
				.withOrganizationId(organizationId)
				.withProductName("Desculpe, mas não foi possível obter licenças para esta organização");
		
		fallbackList.add(license);
		return fallbackList;
	}

	private void randomlyRunLong() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3 - 1) + 1) + 1;
		if (randomNum == 3)
			sleep();
	}

	private void sleep() {
		System.out.println("Dormindo 11 segundos");
		try {
			Thread.sleep(11000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void saveLicense(License license) {
		license.withId(UUID.randomUUID().toString());
		licenseRepository.save(license);
	}

}
