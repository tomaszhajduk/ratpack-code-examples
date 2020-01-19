package model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Address implements Serializable {
	@NotNull
	private final String street;

	@NotNull
	private final String city;

	@NotNull
	private final String buildingNumber;

	private final String flatNumber;

	@NotNull
	private final String postalCode;

	public Address(@JsonProperty("street") String street, @JsonProperty("city") String city
			, @JsonProperty("buildingNumber") String buildingNumber, @JsonProperty("flatNumber") String flatNumber
			, @JsonProperty("postalCode") String postalCode) {
		this.street = street;
		this.city = city;
		this.buildingNumber = buildingNumber;
		this.flatNumber = flatNumber;
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}
}
