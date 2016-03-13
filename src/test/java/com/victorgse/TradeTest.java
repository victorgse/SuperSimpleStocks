package com.victorgse;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class TradeTest {

    @Test
    public void testTradeCreation() {
        DateTime timeOfTrade = new DateTime();
        int quantityOfShares = 10;
        Trade.Type typeOfTrade = Trade.Type.BUY;
        BigDecimal tradePricePerShareInPennies = new BigDecimal("50");

        Trade testTrade = new Trade(timeOfTrade, quantityOfShares, typeOfTrade, tradePricePerShareInPennies);
        assertEquals(timeOfTrade, testTrade.getTimestamp());
        assertEquals(10, testTrade.getQuantityOfSharesTraded());
        assertEquals(Trade.Type.BUY, testTrade.getType());
        assertEquals(new BigDecimal("50"), testTrade.getTradePricePerShareInPennies());
    }

    @Test
    public void testTradeCompletedFifteenMinutesBeforeGivenDateTime() {
        int quantityOfShares = 30;
        Trade.Type typeOfTrade = Trade.Type.SELL;
        BigDecimal tradePricePerShareInPennies = new BigDecimal("80");

        Trade testTrade = new Trade(null, quantityOfShares, typeOfTrade, tradePricePerShareInPennies);
        assertEquals(true, testTrade.completedUpToFifteenMinutesBeforeGivenDateTime(new DateTime()));
        assertEquals(false, testTrade.completedUpToFifteenMinutesBeforeGivenDateTime(new DateTime().plusMinutes(20)));
    }

}
