package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum InvoiceStatus implements Serializable {
	NEW("NEW"),
	APPROVED("APPROVED"),
	PAID("PAID");

	private final String invoiceStatus;
	private static Map<String, InvoiceStatus> INVOICE_STATUS_MAP = Stream
			.of(InvoiceStatus.values())
			.collect(Collectors.toMap(s -> s.invoiceStatus, Function.identity()));

	InvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	@JsonCreator
	public static InvoiceStatus fromString(@JsonProperty("invoiceStatus") String invoiceStatus) {
		return Optional
				.ofNullable(INVOICE_STATUS_MAP.get(invoiceStatus))
				.orElseThrow(() -> new IllegalArgumentException(invoiceStatus));
	}
}
