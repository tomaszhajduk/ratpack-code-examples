package endpoints;

import handlers.requests.invoices.PostInvoiceHandler;
import handlers.requests.invoicesWithPathTokens.GetInvoiceHandler;
import handlers.requests.invoicesWithPathTokens.InvoiceWithPathTokenHandler;
import handlers.requests.invoicesWithPathTokens.PatchInvoiceHandler;
import ratpack.error.ServerErrorHandler;
import ratpack.func.Action;
import ratpack.handling.Chain;
import services.invoices.InvoiceService;

import javax.validation.Validator;

public final class InvoiceEndpoint {

	private final PostInvoiceHandler postInvoiceHandler;
	private final InvoiceWithPathTokenHandler invoiceWithPathTokenHandler;

	public static InvoiceEndpoint buildInvoiceEndpoint(Validator validator, InvoiceService invoiceService) {
		final PostInvoiceHandler postInvoiceHandler = new PostInvoiceHandler(invoiceService, validator);

		final GetInvoiceHandler getInvoiceHandler = new GetInvoiceHandler(invoiceService);
		final PatchInvoiceHandler patchInvoiceHandler = new PatchInvoiceHandler(invoiceService);
		final InvoiceWithPathTokenHandler invoiceWithPathTokenHandler = new InvoiceWithPathTokenHandler(getInvoiceHandler, patchInvoiceHandler);
		return new InvoiceEndpoint(postInvoiceHandler, invoiceWithPathTokenHandler);
	}

	private InvoiceEndpoint(PostInvoiceHandler postInvoiceHandler, InvoiceWithPathTokenHandler invoiceWithPathTokenHandler) {
		this.postInvoiceHandler = postInvoiceHandler;
		this.invoiceWithPathTokenHandler = invoiceWithPathTokenHandler;
	}

	public final Action<Chain> invoiceChain() {
		return chain ->
				chain
						.path("invoices/:uuid", invoiceWithPathTokenHandler)
						.path("invoices", invoiceCtx ->
								invoiceCtx.byMethod(methodCtx -> methodCtx.post(postInvoiceHandler))
						).register(registry ->
						registry.add(ServerErrorHandler.class, (context, throwable) ->
								context.render("caught by error handler: " + throwable.getMessage())
						));
	}
}
