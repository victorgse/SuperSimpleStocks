package com.victorgse.stocks;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CommonStock extends Stock {

    public CommonStock(String symbol, BigDecimal lastDividendInPennies, BigDecimal parValueInPennies) {
        this.symbol = symbol;
        this.lastDividendInPennies = lastDividendInPennies;
        this.parValueInPennies = parValueInPennies;
    }

    @Override
    public BigDecimal calculateDividendYield(BigDecimal marketPriceInPennies) {
        return this.lastDividendInPennies.divide(marketPriceInPennies, 2, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return String.format("[Symbol: %s, Last Dividend In Pennies: %s, Par Value In Pennies: %s]",
                this.getSymbol(), this.getLastDividendInPennies(), this.getParValueInPennies());
    }

}
