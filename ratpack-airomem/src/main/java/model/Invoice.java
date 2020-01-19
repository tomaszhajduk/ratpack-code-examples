package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public final class Invoice implements Serializable {

	private final UUID uuid;

	@NotBlank
	private final String invoiceNumber;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private final LocalDate date;

	@NotNull
	@Valid
	private final Address billingAddress;

	@NotNull
	@Valid
	private final Address shippingAddress;

	@NotNull
	private final BigDecimal netValue;

	private final BigDecimal grossValue;

	@NotNull
	private final BigDecimal taxRate;

	@NotNull
	private final String taxIdentifier;

	private final InvoiceStatus invoiceStatus;

	public Invoice(@JsonProperty("invoiceNumber") String invoiceNumber, @JsonProperty("billingAddress") Address billingAddress
			, @JsonProperty("shippingAddress") Address shippingAddress, @JsonProperty("netValue") BigDecimal netValue
			, @JsonProperty("taxRate") BigDecimal taxRate, @JsonProperty("taxIdentifier") String taxIdentifier) {
		this.uuid = UUID.randomUUID();
		this.invoiceNumber = invoiceNumber;
		this.date = LocalDate.now();
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
		this.netValue = netValue;
		this.taxRate = taxRate;
		this.grossValue = (netValue != null && taxRate != null) ? netValue.add(netValue.multiply(taxRate)) : null;
		this.taxIdentifier = taxIdentifier;
		this.invoiceStatus = InvoiceStatus.NEW;
	}

	public Invoice(Invoice invoice, InvoiceStatus invoiceStatus) {
		this.uuid = invoice.uuid;
		this.invoiceNumber = invoice.invoiceNumber;
		this.date = invoice.date;
		this.billingAddress = invoice.billingAddress;
		this.shippingAddress = invoice.shippingAddress;
		this.netValue = invoice.netValue;
		this.taxRate = invoice.taxRate;
		this.grossValue = invoice.grossValue;
		this.taxIdentifier = invoice.taxIdentifier;
		this.invoiceStatus = invoiceStatus;
	}

	public UUID getUuid() {
		return uuid;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public LocalDate getDate() {
		return date;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public BigDecimal getNetValue() {
		return netValue;
	}

	public BigDecimal getGrossValue() {
		return grossValue;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public String getTaxIdentifier() {
		return taxIdentifier;
	}
}
