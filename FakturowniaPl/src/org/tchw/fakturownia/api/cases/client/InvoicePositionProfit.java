package org.tchw.fakturownia.api.cases.client;

import java.math.BigDecimal;

import org.tchw.fakturownia.api.model.InvoicePosition;
import org.tchw.fakturownia.api.model.Product;

public class InvoicePositionProfit extends Profit<InvoicePosition> {

    public final Product product;

    public InvoicePositionProfit(InvoicePosition profitObject, BigDecimal value, Product product) {
        super(profitObject, value);
        this.product = product;
    }

}
