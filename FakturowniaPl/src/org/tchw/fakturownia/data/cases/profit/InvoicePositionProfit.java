package org.tchw.fakturownia.data.cases.profit;

import java.math.BigDecimal;

import org.tchw.fakturownia.data.model.InvoicePosition;
import org.tchw.fakturownia.data.model.Product;

public class InvoicePositionProfit extends Profit<InvoicePosition> {

    public final Product product;

    public final BigDecimal singleProductProfit;

    public InvoicePositionProfit(InvoicePosition profitObject, BigDecimal value, Product product, BigDecimal singleProductProfit) {
        super(profitObject, value);
        this.product = product;
        this.singleProductProfit = singleProductProfit;
    }

}
