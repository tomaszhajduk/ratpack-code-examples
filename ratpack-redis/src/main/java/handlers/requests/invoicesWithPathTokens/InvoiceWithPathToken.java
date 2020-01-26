package handlers.requests.invoicesWithPathTokens;

import ratpack.path.PathTokens;

import java.util.UUID;

public interface InvoiceWithPathToken {

	default UUID extractUUIDfromPath(PathTokens pathTokens) {
		return UUID.fromString(pathTokens.get("uuid"));
	}
}
