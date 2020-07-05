package com.emrys.jms.jmsfundamentals.models;

import java.io.Serializable;

public class Patient implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String insuracneProvider;
	private Double copay;
	private Double amountToBepayed;
	
	public Patient() {
		
	}
	
	public Patient(int id, String name, String insuracneProvider, Double copay, Double amountToBepayed) {
		super();
		this.id = id;
		this.name = name;
		this.insuracneProvider = insuracneProvider;
		this.copay = copay;
		this.amountToBepayed = amountToBepayed;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInsuracneProvider() {
		return insuracneProvider;
	}

	public void setInsuracneProvider(String insuracneProvider) {
		this.insuracneProvider = insuracneProvider;
	}

	public Double getCopay() {
		return copay;
	}

	public void setCopay(Double copay) {
		this.copay = copay;
	}

	public Double getAmountToBepayed() {
		return amountToBepayed;
	}

	public void setAmountToBepayed(Double amountToBepayed) {
		this.amountToBepayed = amountToBepayed;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Patient [id=" + id + ", name=" + name + ", insuracneProvider=" + insuracneProvider + ", copay=" + copay
				+ ", amountToBepayed=" + amountToBepayed + "]";
	}
	
	

	
}
