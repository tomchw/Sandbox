package org.tchw.fakturownia.api.invoice;

import org.junit.Test;

import static org.junit.Assert.fail;

public class InvoiceTest {

	@Test
	public void test() {
		String invoice = InvoiceBuilder.create()
			.sellDate("2013-11-24")
			.issueDate("2013-11-24")
			.paymentTo("2013-12-01")
			.sellerName("Wystawca Sp. z o.o.")
			.buyerName("Klient1 Sp. z o.o.")
//			.addPosition()
//			    .name("Produkt A1")
//			    .tax(23)
//			    .totalPriceGross(10.23)
//			    .quantity(1)
//			    .exit()
			.build().toJsonString();    
				
		System.out.println(invoice);
		
		fail("Not yet implemented");
	}

	/**
	 
{
				"api_token": "API_TOKEN",
				"invoice": {
					"kind":"vat", 
					"number": null, 
					"sell_date": "2013-11-24", 
					"issue_date": "2013-11-24", 
					"payment_to": "2013-12-01",
					"seller_name": "Wystawca Sp. z o.o.", 
					"seller_tax_no": "5252445767", 
					"buyer_name": "Klient1 Sp. z o.o.",
					"buyer_tax_no": "5252445767",
					"positions":[
						{"name":"Produkt A1", "tax":23, "total_price_gross":10.23, "quantity":1},
						{"name":"Produkt A2", "tax":0, "total_price_gross":50, "quantity":3}
					]		
				}}'
	 
	 */
}
