package org.tchw.fakturownia.api.cases.client;

import java.math.BigDecimal;

import org.tchw.fakturownia.api.model.InvoicePosition;
import org.tchw.fakturownia.api.model.Product;

public class InvoicePositionProfit extends Profit<InvoicePosition> {

    public final Product product;

    public final BigDecimal singleProductProfit;

    public InvoicePositionProfit(InvoicePosition profitObject, BigDecimal value, Product product, BigDecimal singleProductProfit) {
        super(profitObject, value);
        this.product = product;
        this.singleProductProfit = singleProductProfit;
    }

}
