package handlers.requests.invoicesWithPathTokens;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class InvoiceWithPathTokenHandler implements Handler {

	private final GetInvoiceHandler getInvoiceHandler;
	private final PatchInvoiceHandler patchInvoiceHandler;

	public InvoiceWithPathTokenHandler(GetInvoiceHandler getInvoiceHandler, PatchInvoiceHandler patchInvoiceHandler) {
		this.getInvoiceHandler = getInvoiceHandler;
		this.patchInvoiceHandler = patchInvoiceHandler;
	}

	@Override
	public void handle(Context context) throws Exception {
		context.byMethod(methodCtx -> {
			methodCtx.get(getInvoiceHandler);
			methodCtx.patch(patchInvoiceHandler);
		});
	}
}
