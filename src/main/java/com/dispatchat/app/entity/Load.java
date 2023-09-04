package com.dispatchat.app.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "loads")
public class Load {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String equipmentType;
	private String pickupLocation;

	private Long userID;
	private String pickupCity;
	private String pickupState;
	private String pickupCountry;
	private String deliveryCity;
	private String deliveryState;
	private String deliveryCountry;
	private Date pickupDate;
	private String deliveryLocation;
	private int rate;

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getRate() {
		return rate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public String getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public String getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(String deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getPickupCity() {
		return pickupCity;
	}

	public void setPickupCity(String pickupCity) {
		this.pickupCity = pickupCity;
	}

	public String getPickupState() {
		return pickupState;
	}

	public void setPickupState(String pickupState) {
		this.pickupState = pickupState;
	}

	public String getPickupCountry() {
		return pickupCountry;
	}

	public void setPickupCountry(String pickupCountry) {
		this.pickupCountry = pickupCountry;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public void setDeliveryCountry(String deliveryCountry) {
		this.deliveryCountry = deliveryCountry;
	}
}
