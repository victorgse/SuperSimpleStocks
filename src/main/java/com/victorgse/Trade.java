package com.victorgse;

import java.math.BigDecimal;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

public class Trade {

    public enum Type {
        BUY, SELL
    }

    private DateTime timestamp;
    private int quantityOfSharesTraded;
    private Type type;
    private BigDecimal tradePricePerShareInPennies;

    public Trade(DateTime timestamp, int quantityOfSharesTraded, Type type, BigDecimal tradePricePerShare) {
        if (timestamp != null) {
            this.timestamp = timestamp;
        } else {
            this.timestamp = new DateTime();
        }
        this.quantityOfSharesTraded = quantityOfSharesTraded;
        this.type = type;
        this.tradePricePerShareInPennies = tradePricePerShare;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public int getQuantityOfSharesTraded() {
        return quantityOfSharesTraded;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getTradePricePerShareInPennies() {
        return tradePricePerShareInPennies;
    }

    public boolean completedUpToFifteenMinutesBeforeGivenDateTime(DateTime dateTimeToCompareTimestampWith) {
        return Minutes.minutesBetween(this.timestamp, dateTimeToCompareTimestampWith).isLessThan(Minutes.minutes(15));
    }

    @Override
    public String toString() {
        return String.format("[Timestamp: %s, Quantity: %d, Type: %s, Price per Share in Pennies: %s]",
                this.getTimestamp(), this.getQuantityOfSharesTraded(), this.getType(), this.getTradePricePerShareInPennies());
    }

}
