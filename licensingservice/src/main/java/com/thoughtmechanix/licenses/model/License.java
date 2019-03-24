package com.thoughtmechanix.licenses.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class License {

	@Id
	private String licenseId;

	@Basic(optional = false)
	private String productName;

	@Basic
	private String licenseType;

	@Basic
	private String organizationId;

	@Transient
	private String organizationName = "";

	@Transient
	private String contactName = "";

	@Transient
	private String contactPhone = "";

	@Transient
	private String contactEmail = "";

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
	
	public License withOrganizationName(String organizationName) {
		this.organizationName = organizationName;
		return this;
	}
	
	public License withContactName(String contactName) {
		this.contactName = contactName;
		return this;
	}
	
	public License withContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
		return this;
	}
	
	public License withContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
		return this;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}
	
	
}
