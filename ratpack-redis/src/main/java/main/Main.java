package main;

import ratpack.server.RatpackServer;

public class Main {

	public static void main(String... args) throws Exception {
		RatpackServer ratpackServer = RatpackBackendServer.createRatpackServer();
		ratpackServer.start();
	}
}
