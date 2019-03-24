package com.thoughtmechanix.licenses.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class License {

	@Id
	private String licenseId;
	
	@Basic(optional=false)
	private String productName;
	
	@Basic
	private String licenseType;
	
	@Basic
	private String organizationId;
	
	public String getLicenseId() {
		return licenseId;
	}

	public String getProductName() {
		return productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public License withId(String licenseId) {
		this.licenseId = licenseId;
		return this;
	}

	public License withProductName(String productName) {
		this.productName = productName;
		return this;
	}

	public License withLicenseType(String licenseType) {
		this.licenseType = licenseType;
		return this;
	}

	public License withOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}

}
