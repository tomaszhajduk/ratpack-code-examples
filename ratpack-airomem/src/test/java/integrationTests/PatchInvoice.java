package integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import main.RatpackBackendServer;
import model.InvoiceStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ratpack.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.registry.Registry;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ratpack.jackson.Jackson.toJson;

public class PatchInvoice {

	private static EmbeddedApp mainClassApplicationUnderTest;
	private static Registry registry;

	@BeforeAll
	public static void init() throws Exception {
		mainClassApplicationUnderTest = EmbeddedApp.fromServer(RatpackBackendServer.createRatpackServer());
		registry = Registry.of(r -> r
				.add(new ObjectMapper().registerModule(new JSR310Module())));
	}

	@Test
	public void shouldUpdateStatus() throws Exception {
		mainClassApplicationUnderTest.test(testHttpClient -> {
			ReceivedResponse receivedResponse = testHttpClient.requestSpec(requestSpec -> {
				requestSpec.headers(mutableHeaders ->
						mutableHeaders.add("Content-Type", "application/json"))
						.method(HttpMethod.PATCH)
						.body(body -> body.text(toJson(registry).apply(InvoiceStatus.APPROVED)));
			}).patch("invoices/581fa02c-a648-495c-b6a1-e2b763232071");
			assertEquals(200, receivedResponse.getStatusCode());
			assertTrue(receivedResponse.getBody().getText().contains("APPROVED"));
		});
	}

	@AfterAll
	public static void close() {
		mainClassApplicationUnderTest.close();
	}
}
