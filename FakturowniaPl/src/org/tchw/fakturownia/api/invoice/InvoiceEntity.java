package org.tchw.fakturownia.api.invoice;

import com.google.gson.Gson;

public class InvoiceEntity {

	private final String api_token;
	
	private final Invoice invoice;
	
	private InvoiceEntity(String apiToken, Invoice invoice) {
		this.api_token = apiToken;
		this.invoice = invoice;
	}

	public static InvoiceEntity create(String apiToken, Invoice invoice) {
		return new InvoiceEntity(apiToken, invoice);
	}
	
	public String toJsonString() {
		return new Gson().toJson(this);
	}
}
