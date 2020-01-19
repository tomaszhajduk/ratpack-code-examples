package integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import main.RatpackBackendServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ratpack.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.registry.Registry;
import ratpack.test.embed.EmbeddedApp;
import services.InvoiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ratpack.jackson.Jackson.toJson;

public class PostInvoiceTest extends InvoiceTest {

	private static EmbeddedApp mainClassApplicationUnderTest;
	private static Registry registry;

	@BeforeAll
	public static void init() throws Exception {
		mainClassApplicationUnderTest = EmbeddedApp.fromServer(RatpackBackendServer.createRatpackServer());
		registry = Registry.of(r -> r
				.add(new ObjectMapper().registerModule(new Jdk8Module())));
	}

	@Test
	public void shouldPostInvoice() throws Exception {
		mainClassApplicationUnderTest.test(testHttpClient -> {
			ReceivedResponse receivedResponse = testHttpClient.requestSpec(requestSpec -> {
				requestSpec.headers(mutableHeaders ->
						mutableHeaders.add("Content-Type", "application/json"))
						.method(HttpMethod.POST)
						.body(body -> body.text(toJson(registry).apply(createInvoice("FV 1/2020"))));
			}).post("invoices");
			assertEquals(200, receivedResponse.getStatusCode());
		});
	}

	@AfterAll
	public static void close() {
		mainClassApplicationUnderTest.close();
	}
}
