package integrationTests;

import main.RatpackBackendServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetInvoiceTest {

	private static EmbeddedApp mainClassApplicationUnderTest;

	@BeforeAll
	public static void init() throws Exception {
		mainClassApplicationUnderTest = EmbeddedApp.fromServer(RatpackBackendServer.createRatpackServer());
	}

	@Test
	public void shouldReturnInvoice() throws Exception {
		mainClassApplicationUnderTest.test(testHttpClient -> {
			ReceivedResponse receivedResponse = testHttpClient.get("invoices/581fa02c-a648-495c-b6a1-e2b763232071");
			assertEquals(200, receivedResponse.getStatusCode());
			assertTrue(receivedResponse.getBody().getText().contains("581fa02c-a648-495c-b6a1-e2b763232071"));
		});
	}

	@AfterAll
	public static void close() {
		mainClassApplicationUnderTest.close();
	}
}
