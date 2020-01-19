package handlers.requests.invoicesWithPathTokens;

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

class GetInvoiceTest extends InvoiceTest {

	private static InvoiceCore invoiceCore;

	@BeforeAll
	static void init() {
		invoiceCore = mock(InvoiceCore.class);
	}

	@Test
	void shouldReturn404() throws Exception {
		when(invoiceCore.getInvoice(UUID.fromString("581fa02c-a648-495c-b6a1-e2b763232071")))
				.thenThrow(new NoSuchElementException());

		GetInvoiceHandler getInvoiceHandler = new GetInvoiceHandler(invoiceCore);

		HandlingResult result = handle(getInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581fa02c-a648-495c-b6a1-e2b763232071")
							.pathBinding(Map.of("uuid", "581fa02c-a648-495c-b6a1-e2b763232071"))
							.method("GET");
				}
		);

		assertEquals(404, result.getStatus().getCode());
	}

	@Test
	void shouldReturn400() throws Exception {
		GetInvoiceHandler getInvoiceHandler = new GetInvoiceHandler(invoiceCore);

		HandlingResult result = handle(getInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581232071")
							.pathBinding(Map.of("uuid", "581232071"))
							.method("GET");
				}
		);

		assertEquals(400, result.getStatus().getCode());
	}

	@Test
	void shouldReturn200() throws Exception {
		when(invoiceCore.getInvoice(UUID.fromString("581fa02c-a648-495c-b6a1-e2b763232071")))
				.thenReturn(createInvoice("FV 1/2020"));

		GetInvoiceHandler getInvoiceHandler = new GetInvoiceHandler(invoiceCore);

		HandlingResult result = handle(getInvoiceHandler, fixture -> {
					fixture
							.uri("invoices/581fa02c-a648-495c-b6a1-e2b763232071")
							.pathBinding(Map.of("uuid", "581fa02c-a648-495c-b6a1-e2b763232071"))
							.method("GET");
				}
		);

		assertEquals(200, result.getStatus().getCode());
	}
}