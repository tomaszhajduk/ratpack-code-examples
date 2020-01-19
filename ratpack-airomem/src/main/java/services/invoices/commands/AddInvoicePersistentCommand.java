package services.invoices.commands;

import model.Invoice;
import pl.setblack.airomem.core.Command;
import services.invoices.InvoiceCore;

import java.io.Serializable;

public final class AddInvoicePersistentCommand implements Command<InvoiceCore, Invoice>, Serializable {
	private static final long serialVersionUID = 1L;

	private final Invoice invoice;

	public AddInvoicePersistentCommand(Invoice invoice) {
		this.invoice = invoice;
	}

	@Override
	public Invoice execute(InvoiceCore invoiceCore) {
		return invoiceCore.addInvoice(invoice);
	}
}
