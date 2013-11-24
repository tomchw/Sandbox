package org.tchw.fakturownia.api.example;

import org.tchw.fakturownia.api.invoice.Invoice;
import org.tchw.fakturownia.api.invoice.InvoiceBuilder;
import org.tchw.fakturownia.api.invoice.InvoiceEntity;
import org.tchw.fakturownia.api.request.PostRequest;


public class InsertInvoiceExample {

	public void execute() {
		Invoice invoice = InvoiceBuilder.create()
				.sellDate("2013-11-24")
				.issueDate("2013-11-24")
				.paymentTo("2013-12-01")
				.sellerName("Wystawca Sp. z o.o.")
				.buyerName("Klient1 Sp. z o.o.")
				.build();
		
		InvoiceEntity invoiceEntity = InvoiceEntity.create("sMEuDnemiZIPcbEL5g", invoice);
		System.out.println(invoiceEntity.toJsonString());
		PostRequest.create("https://tcc1.fakturownia.pl/invoices.json", invoiceEntity.toJsonString()).execute();
	}

	public static void main(String[] args) {
		new InsertInvoiceExample().execute();
	}
}
