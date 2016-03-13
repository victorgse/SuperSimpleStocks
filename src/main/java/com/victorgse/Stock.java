package com.victorgse;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Stock {

    public enum Type {
        COMMON, PREFERRED
    }

    private String symbol;
    private Type type;
    private BigDecimal lastDividendInPennies;
    private BigDecimal fixedDividendPercent;
    private BigDecimal parValueInPennies;
    private List<Trade> pastTrades = new ArrayList<Trade>();

    public Stock(String symbol, Type type, BigDecimal lastDividendInPennies, BigDecimal fixedDividendPercent, BigDecimal parValueInPennies) {
        this.symbol = symbol;
        this.type = type;
        this.lastDividendInPennies = lastDividendInPennies;
        this.fixedDividendPercent = fixedDividendPercent;
        this.parValueInPennies = parValueInPennies;
    }

    public BigDecimal calculateDividendYield(BigDecimal marketPriceInPennies) {
        BigDecimal dividendYield;
        int decimalDigits = 2;
        if (this.fixedDividendPercent != null) {
            dividendYield = this.fixedDividendPercent.multiply(this.parValueInPennies).divide(marketPriceInPennies, 2, RoundingMode.HALF_UP);
        } else {
            dividendYield = this.lastDividendInPennies.divide(marketPriceInPennies, decimalDigits, RoundingMode.HALF_UP);
        }
        return dividendYield;
    }

    public BigDecimal calculatePriceToEarningsRatio(BigDecimal marketPriceInPennies) {
        return marketPriceInPennies.divide(this.lastDividendInPennies, 2, RoundingMode.HALF_UP);
    }

    public String recordTrade(Trade trade) {
        this.pastTrades.add(trade);
        return String.format("Trade of stock %s recorded --- %s", this.getSymbol(), trade.toString());
    }

    public BigDecimal calculateVolumeWeightedStockPriceOfTradesInLastFifteenMins() {
        BigDecimal totalPriceOfSharesTradedInLastFifteenMins = new BigDecimal("0");
        BigDecimal totalQuantityOfSharesTradedInLastFifteenMins = new BigDecimal("0");
        for (Trade trade : this.pastTrades) {
            if (trade.completedUpToFifteenMinutesBeforeGivenDateTime(new DateTime())) {
                BigDecimal pricePerShareOfTrade = trade.getTradePricePerShareInPennies();
                BigDecimal quantityOfSharesTraded = new BigDecimal(trade.getQuantityOfSharesTraded());
                totalPriceOfSharesTradedInLastFifteenMins = totalPriceOfSharesTradedInLastFifteenMins.add(pricePerShareOfTrade.multiply(quantityOfSharesTraded));
                totalQuantityOfSharesTradedInLastFifteenMins = totalQuantityOfSharesTradedInLastFifteenMins.add(quantityOfSharesTraded);
            }
        }
        return totalPriceOfSharesTradedInLastFifteenMins.divide(totalQuantityOfSharesTradedInLastFifteenMins, RoundingMode.HALF_UP);
    }

    public String getSymbol() {
        return symbol;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getLastDividendInPennies() {
        return lastDividendInPennies;
    }

    public BigDecimal getFixedDividendPercent() {
        return fixedDividendPercent;
    }

    public BigDecimal getParValueInPennies() {
        return parValueInPennies;
    }

    public List<Trade> getPastTrades() {
        return pastTrades;
    }

    @Override
    public String toString() {
        return String.format("[Symbol: %s, Type: %s, Last Dividend In Pennies: %s, Fixed Dividend Percent: %s, Par Value In Pennies: %s]",
                this.getSymbol(), this.getType(), this.getLastDividendInPennies(), this.getFixedDividendPercent(), this.getParValueInPennies());
    }

}
