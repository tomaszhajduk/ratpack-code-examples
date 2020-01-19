package services.invoices;

import model.Invoice;
import model.InvoiceStatus;
import pl.setblack.airomem.core.Persistent;
import services.invoices.commands.AddInvoicePersistentCommand;
import services.invoices.commands.ChangeStatusPersistenceCommand;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class InvoicePersistence implements InvoiceService {

	final Persistent<InvoiceCore> controller;

	final Path storeFolder = Paths.get("invoiceStore");

	public InvoicePersistence() {
		controller = Persistent.loadOptional(storeFolder, () -> new InvoiceCore());
	}

	@Override
	public Invoice addInvoice(Invoice invoice) {
		return controller.executeAndQuery(new AddInvoicePersistentCommand(invoice));
	}

	@Override
	public Invoice changeStatus(UUID uuid, InvoiceStatus invoiceStatus) {
		return controller.executeAndQuery(new ChangeStatusPersistenceCommand(uuid, invoiceStatus));
	}

	@Override
	public Invoice getInvoice(UUID uuid) {
		return controller.query(core -> core.getInvoice(uuid));
	}

	public void close() {
		controller.close();
	}
}
