package services;

import model.Address;
import model.Invoice;
import model.InvoiceStatus;

import java.math.BigDecimal;

public class InvoiceTest {

	protected Invoice createInvoice(String invoiceNumber) {
		Address billingAddress = new Address("Topolowa", "Cracow", "59", "40", "42-201");
		Address shippingAddress = new Address("Lipowa", "Cracow", "2", "12", "42-201");
		Invoice invoice = new Invoice(invoiceNumber, billingAddress, shippingAddress, new BigDecimal("100"), new BigDecimal("23"), "11223344");
		return new Invoice(invoice, InvoiceStatus.APPROVED);
	}
}
