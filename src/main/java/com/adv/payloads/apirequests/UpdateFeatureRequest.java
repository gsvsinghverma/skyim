package com.adv.payloads.apirequests;

public class UpdateFeatureRequest {

	private Long id;
	private String name;
	private String code;
	private double amount;
	private String description;
	private String subFeatures;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubFeatures() {
		return subFeatures;
	}

	public void setSubFeatures(String subFeatures) {
		this.subFeatures = subFeatures;
	}

}
