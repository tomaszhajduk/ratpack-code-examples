package services.invoices;

import model.Invoice;
import model.InvoiceStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import services.InvoiceTest;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceCoreTest extends InvoiceTest {

	private static InvoiceCore invoiceCore;

	@BeforeAll
	public static void init() {
		invoiceCore = new InvoiceCore();
	}

	@Test
	public void shouldAddInvoice() {
		Invoice invoice = createInvoice("FV 1/2020");

		Invoice resultInvoice = invoiceCore.addInvoice(invoice);

		assertTrue(invoiceCore.getInvoice(resultInvoice.getUuid()) != null);
	}

	@Test
	public void shouldChangeStatus() {
		Invoice invoice = createInvoice("FV 1/2020");
		Invoice addedInvoice = invoiceCore.addInvoice(invoice);

		Invoice modifiedInvoice = invoiceCore.changeStatus(addedInvoice.getUuid(), InvoiceStatus.APPROVED);

		assertEquals(InvoiceStatus.APPROVED, modifiedInvoice.getInvoiceStatus());
	}

	@Test
	public void shouldThrowNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () ->
				invoiceCore.changeStatus(UUID.fromString("581fa02c-a648-495c-b6a1-e2b763232071")
						, InvoiceStatus.APPROVED));
	}
}