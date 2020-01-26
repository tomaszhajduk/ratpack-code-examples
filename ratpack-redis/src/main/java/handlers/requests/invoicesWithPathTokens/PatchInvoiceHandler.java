package handlers.requests.invoicesWithPathTokens;

import com.google.common.base.Throwables;
import model.InvoiceStatus;
import org.apache.http.HttpStatus;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import services.invoices.InvoiceService;

import java.util.NoSuchElementException;

import static ratpack.jackson.Jackson.json;

public class PatchInvoiceHandler implements Handler, InvoiceWithPathToken {

	private final InvoiceService invoiceService;

	public PatchInvoiceHandler(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@Override
	public void handle(Context context) {
		context
				.parse(InvoiceStatus.class)
				.map(invoiceStatus ->
						invoiceService.changeStatus(
								extractUUIDfromPath(context.getPathTokens()), invoiceStatus)
				)
				.onError(IllegalArgumentException.class, (exception) ->
						context.clientError(HttpStatus.SC_BAD_REQUEST)
				)
				.onError(throwable -> Throwables.getRootCause(throwable) instanceof NoSuchElementException
						, (exception) -> context.clientError(HttpStatus.SC_NOT_FOUND)
				)
				.then(invoice -> context.render(json(invoice)));
	}
}
