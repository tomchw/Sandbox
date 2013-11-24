package org.tchw.fakturownia.api.invoice;

import com.google.gson.Gson;

public class Invoice {

	String sell_date;

	String issue_date;

	String payment_to;

	String seller_name;
	
	String buyer_name;
	
	public String toJsonString() {
		return new Gson().toJson(this);
	}
	
}
