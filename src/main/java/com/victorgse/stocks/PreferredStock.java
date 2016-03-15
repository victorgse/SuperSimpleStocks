package com.victorgse.stocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PreferredStock extends Stock {

    private BigDecimal fixedDividendPercent;

    public PreferredStock(String symbol, BigDecimal lastDividendInPennies, BigDecimal fixedDividendPercent, BigDecimal parValueInPennies) {
        this.symbol = symbol;
        this.lastDividendInPennies = lastDividendInPennies;
        this.fixedDividendPercent = fixedDividendPercent;
        this.parValueInPennies = parValueInPennies;
    }

    @Override
    public BigDecimal calculateDividendYield(BigDecimal marketPriceInPennies) {
        return this.fixedDividendPercent.multiply(this.parValueInPennies).divide(marketPriceInPennies, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getFixedDividendPercent() {
        return this.fixedDividendPercent;
    }

    @Override
    public String toString() {
        return String.format("[Symbol: %s, Last Dividend In Pennies: %s, Fixed Dividend Percent: %s, Par Value In Pennies: %s]",
                this.getSymbol(), this.getLastDividendInPennies(), this.getFixedDividendPercent(), this.getParValueInPennies());
    }

}
