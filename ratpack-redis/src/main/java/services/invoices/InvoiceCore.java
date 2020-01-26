package services.invoices;

import model.Invoice;
import model.InvoiceStatus;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class InvoiceCore implements InvoiceService, Serializable {
	private static final long serialVersionUID = 1L;
	private final RMap<UUID, Invoice> invoices;
	private final RedissonClient redissonClient;

	public InvoiceCore(RedissonClient redissonClient) {
		this.redissonClient = redissonClient;
		this.invoices = redissonClient.getMap("invoices");
	}

	@Override
	public Invoice addInvoice(Invoice invoice) {
		invoices.fastPut(invoice.getUuid(), invoice);
		return invoice;
	}

	public Invoice changeStatus(UUID uuid, InvoiceStatus invoiceStatus) {
		return Optional.ofNullable(invoices.get(uuid))
				.map(invoice -> new Invoice(invoice, invoiceStatus))
				.map(invoice -> invoices.put(invoice.getUuid(), invoice))
				.orElseThrow(() -> new NoSuchElementException());
	}

	@Override
	public Invoice getInvoice(UUID uuid) {
		return Optional.ofNullable(invoices.get(uuid))
				.orElseThrow(() -> new NoSuchElementException());
	}
}