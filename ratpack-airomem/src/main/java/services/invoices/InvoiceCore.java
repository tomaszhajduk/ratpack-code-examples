package services.invoices;

import model.Invoice;
import model.InvoiceStatus;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InvoiceCore implements InvoiceService, Serializable {
	private static final long serialVersionUID = 1L;
	private final Map<UUID, Invoice> invoices;

	public InvoiceCore() {
		invoices = new ConcurrentHashMap<>();
	}

	@Override
	public Invoice addInvoice(Invoice invoice) {
		invoices.put(invoice.getUuid(), invoice);
		return invoice;
	}

	@Override
	public Invoice changeStatus(UUID uuid, InvoiceStatus invoiceStatus) {
		return Optional.ofNullable(invoices.get(uuid))
				.map(invoice -> new Invoice(invoice, invoiceStatus))
				.map(invoice -> {
					invoices.put(invoice.getUuid(), invoice);
					return invoice;
				})
				.orElseThrow(() -> new NoSuchElementException());
	}

	@Override
	public Invoice getInvoice(UUID uuid) {
		return Optional.ofNullable(invoices.get(uuid))
				.orElseThrow(() -> new NoSuchElementException());
	}
}