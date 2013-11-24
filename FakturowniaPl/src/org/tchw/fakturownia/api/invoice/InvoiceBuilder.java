package org.tchw.fakturownia.api.invoice;


public class InvoiceBuilder {

	private final Invoice invoice = new Invoice();
	
	private InvoiceBuilder() {
	}
	
	public static InvoiceBuilder create() {
		return new InvoiceBuilder();
	}

	public InvoiceBuilder sellDate(String sellDate) {
		invoice.sell_date = sellDate;
		return this;
	}

	public InvoiceBuilder issueDate(String issueDate) {
		invoice.issue_date = issueDate;
		return this;
	}

	public InvoiceBuilder paymentTo(String paymentTo) {
		invoice.payment_to = paymentTo;
		return this;
	}
	
	public InvoiceBuilder sellerName(String sellerName) {
		invoice.seller_name = sellerName;
		return this;
	}
	
	public InvoiceBuilder buyerName(String buyerName) {
		invoice.buyer_name = buyerName;
		return this;
	}
	
	public Invoice build() {
		return invoice;
	}
}
