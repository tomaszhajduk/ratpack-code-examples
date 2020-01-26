package services.invoices;

import model.Invoice;
import model.InvoiceStatus;

import java.util.UUID;

public interface InvoiceService {

	Invoice addInvoice(Invoice invoice);

	Invoice changeStatus(UUID uuid, InvoiceStatus invoiceStatus);

	Invoice getInvoice(UUID uuid);
}
