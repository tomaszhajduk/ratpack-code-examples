package handlers.requests.invoicesWithPathTokens;

import model.InvoiceStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ratpack.test.handling.HandlingResult;
import services.InvoiceTest;
import services.invoices.InvoiceCore;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ratpack.test.handling.RequestFixture.handle;

class PatchInvoiceTest extends InvoiceTest {
	static InvoiceCore invoiceCore;

	@BeforeAll
	static void init() {
		invoiceCore = mock(InvoiceCore.class);
	}

	@Test
	void shouldReturn404() throws Exception {
		when(invoiceCore.changeStatus(UUID.fromString("581fa02c-a648-495c-b6a1-e2b763232071"), InvoiceStatus.APPROVED))
				.thenThrow(new NoSuchElementException());

		PatchInvoiceHandler patchInvoiceHandler = new PatchInvoiceHandler(invoiceCore);

		HandlingResult result = handle(patchInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581fa02c-a648-495c-b6a1-e2b763232071")
							.pathBinding(Map.of("uuid", "581fa02c-a648-495c-b6a1-e2b763232071"))
							.body("{\"invoiceStatus\":\"APPROVED\"}", "application/json")
							.method("PATCH");
				}
		);

		assertEquals(404, result.getStatus().getCode());
	}

	@Test
	void shouldReturn400() throws Exception {
		PatchInvoiceHandler patchInvoiceHandler = new PatchInvoiceHandler(invoiceCore);

		HandlingResult result = handle(patchInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581232071")
							.pathBinding(Map.of("uuid", "581232071"))
							.body("{\"invoiceStatus\":\"APPROVED\"}", "application/json")
							.method("PATCH");
				}
		);

		assertEquals(400, result.getStatus().getCode());
	}

	@Test
	void shouldReturn200() throws Exception {
		when(invoiceCore.changeStatus(UUID.fromString("581fa02c-a648-495c-b6a1-e2b763232071"), InvoiceStatus.APPROVED))
				.thenReturn(createInvoice("FV 1/2020"));

		PatchInvoiceHandler patchInvoiceHandler = new PatchInvoiceHandler(invoiceCore);

		HandlingResult result = handle(patchInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581fa02c-a648-495c-b6a1-e2b763232071")
							.pathBinding(Map.of("uuid", "581fa02c-a648-495c-b6a1-e2b763232071"))
							.body("{\"invoiceStatus\":\"APPROVED\"}", "application/json")
							.method("PATCH");
				}
		);

		assertEquals(200, result.getStatus().getCode());
	}
}