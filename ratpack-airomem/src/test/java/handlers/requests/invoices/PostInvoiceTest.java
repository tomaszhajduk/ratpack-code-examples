package handlers.requests.invoices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ratpack.registry.Registry;
import ratpack.test.handling.HandlingResult;
import services.InvoiceTest;
import services.invoices.InvoiceCore;

import javax.validation.Validation;
import javax.validation.Validator;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ratpack.jackson.Jackson.toJson;
import static ratpack.test.handling.RequestFixture.handle;

class PostInvoiceTest extends InvoiceTest {

	private static InvoiceCore invoiceCore;
	private static Validator validator;
	private static Registry registry;

	@BeforeAll
	static void init() throws Exception {
		invoiceCore = new InvoiceCore();
		validator = Validation.buildDefaultValidatorFactory().getValidator();
		registry = Registry.of(r -> r
				.add(new ObjectMapper().registerModule(new Jdk8Module())));
	}

	@Test
	void shouldReturn400() throws Exception {
		PostInvoiceHandler postInvoiceHandler = new PostInvoiceHandler(invoiceCore, validator);

		HandlingResult result = handle(postInvoiceHandler, fixture ->
				fixture
						.uri("invoices")
						.body(toJson(registry).apply(createInvoice(null)).getBytes(StandardCharsets.UTF_8), "application/json")
						.method("POST")
		);

		assertEquals(400, result.getStatus().getCode());
	}

	@Test
	void shouldReturn200() throws Exception {
		PostInvoiceHandler postInvoiceHandler = new PostInvoiceHandler(invoiceCore, validator);

		HandlingResult result = handle(postInvoiceHandler, fixture ->
				fixture
						.uri("invoices")
						.method("POST")
						.body(toJson(registry).apply(createInvoice("FV 1/2020")).getBytes(StandardCharsets.UTF_8), "application/json")
		);

		assertEquals(200, result.getStatus().getCode());
	}

}