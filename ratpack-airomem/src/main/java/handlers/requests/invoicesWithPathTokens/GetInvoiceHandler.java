package handlers.requests.invoicesWithPathTokens;

import org.apache.http.HttpStatus;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import services.invoices.InvoiceService;

import java.util.NoSuchElementException;

import static ratpack.jackson.Jackson.json;

public class GetInvoiceHandler implements Handler, InvoiceWithPathToken {

	private final InvoiceService invoiceService;

	public GetInvoiceHandler(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@Override
	public void handle(Context context) throws Exception {
		Promise.value(context.getPathTokens())
				.map(pathTokens -> extractUUIDfromPath(pathTokens))
				.map(uuid -> invoiceService.getInvoice(uuid))
				.onError(IllegalArgumentException.class, (exception) ->
						context.clientError(HttpStatus.SC_BAD_REQUEST)
				)
				.onError(NoSuchElementException.class, (exception) ->
						context.clientError(HttpStatus.SC_NOT_FOUND)
				)
				.then(invoice -> context.render(json(invoice)));
	}
}
