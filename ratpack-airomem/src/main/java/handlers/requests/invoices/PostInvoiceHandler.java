package handlers.requests.invoices;

import model.Invoice;
import org.apache.http.HttpStatus;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import services.invoices.InvoiceService;

import javax.validation.Validator;

import static ratpack.jackson.Jackson.json;

public class PostInvoiceHandler implements Handler {

	private final InvoiceService invoiceService;
	private final Validator validator;

	public PostInvoiceHandler(InvoiceService invoiceService, Validator validator) {
		this.invoiceService = invoiceService;
		this.validator = validator;
	}

	@Override
	public void handle(Context context) {
		context.parse(Invoice.class)
				.route(invoice -> validator.validate(invoice).size() > 0
						, (invoice) -> {
							context.clientError(HttpStatus.SC_BAD_REQUEST);
						})
				.then(invoice -> {
					Invoice inv = invoiceService.addInvoice(invoice);
					context.render(json(inv));
				});
	}
}
