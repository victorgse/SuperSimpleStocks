package com.victorgse.stocks;

import com.victorgse.Trade;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public abstract class Stock {

    String symbol;
    BigDecimal lastDividendInPennies;
    BigDecimal parValueInPennies;
    List<Trade> pastTrades = new ArrayList<Trade>();

    public abstract BigDecimal calculateDividendYield(BigDecimal marketPriceInPennies);

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

    public BigDecimal getLastDividendInPennies() {
        return lastDividendInPennies;
    }

    public BigDecimal getParValueInPennies() {
        return parValueInPennies;
    }

    public List<Trade> getPastTrades() {
        return pastTrades;
    }

}
