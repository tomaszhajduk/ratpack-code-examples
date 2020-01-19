package services.invoices.commands;

import model.Invoice;
import model.InvoiceStatus;
import pl.setblack.airomem.core.Command;
import services.invoices.InvoiceCore;

import java.io.Serializable;
import java.util.UUID;

public final class ChangeStatusPersistenceCommand implements Command<InvoiceCore, Invoice>, Serializable {
	private static final long serialVersionUID = 1L;

	private final UUID uuid;
	private final InvoiceStatus invoiceStatus;

	public ChangeStatusPersistenceCommand(UUID uuid, InvoiceStatus invoiceStatus) {
		this.uuid = uuid;
		this.invoiceStatus = invoiceStatus;
	}

	@Override
	public Invoice execute(InvoiceCore invoiceCore) {
		return invoiceCore.changeStatus(uuid, invoiceStatus);
	}
}
