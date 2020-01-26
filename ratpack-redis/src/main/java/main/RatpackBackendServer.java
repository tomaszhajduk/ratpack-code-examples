package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import endpoints.InvoiceEndpoint;
import org.redisson.api.RedissonClient;
import ratpack.registry.Registry;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;
import services.invoices.InvoiceCore;
import services.invoices.InvoiceService;

import javax.validation.Validation;
import javax.validation.Validator;
import java.net.URI;

public final class RatpackBackendServer {

	private RatpackBackendServer() {
	}

	public static RatpackServer createRatpackServer() throws Exception {
		final Registry registry = Registry.of(r -> r
				.add(new ObjectMapper().registerModule(new Jdk8Module())));

		final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		final RedissonClient redissonClient = new RedisConnection().getRedissonClient();

		final InvoiceService invoiceService = new InvoiceCore(redissonClient);
		final InvoiceEndpoint invoiceEndpoint = InvoiceEndpoint.buildInvoiceEndpoint(validator, invoiceService);

		return RatpackServer.of(server -> server
				.serverConfig(ServerConfig.embedded()
						.publicAddress(new URI("http://localhost"))
						.port(8085))
				.registry(registry)
				.handlers(chain -> {
					chain.insert(invoiceEndpoint.invoiceChain());
				})
		);
	}
}
