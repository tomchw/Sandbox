package org.tchw.fakturownia.data.cases.profit;

import java.math.BigDecimal;

public class Profit<T> {

    private final T profitObject;

    private final BigDecimal value;

    public Profit(T profitObject, BigDecimal value) {
        this.profitObject = profitObject;
        this.value = value.setScale(2);
    }

    public T profitObject() {
        return profitObject;
    }

    public BigDecimal profitValue() {
        return value;
    }

    public static BigDecimal sum(Iterable<? extends Profit<?>> invoicePositionsProfits) {
        BigDecimal invoiceProfit = BigDecimal.ZERO;
        for (Profit<?> invoicePositionProfit : invoicePositionsProfits) {
            invoiceProfit = invoiceProfit.add(invoicePositionProfit.profitValue());
        }
        return invoiceProfit;
    }
}
