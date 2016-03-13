package com.victorgse;

import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class StockTest {

    @Test
    public void testGetDividendYield() {
        Stock sampleStock = new Stock("GIN", Stock.Type.PREFERRED, new BigDecimal("8"), new BigDecimal("0.02"), new BigDecimal("100"));
        assertEquals(new BigDecimal("0.10"), sampleStock.calculateDividendYield(new BigDecimal("20")));
        assertEquals(new BigDecimal("0.16"), sampleStock.calculateDividendYield(new BigDecimal("12.5")));

        Stock sampleStock2 = new Stock("JOE", Stock.Type.COMMON, new BigDecimal("8"), null, new BigDecimal("250"));
        assertEquals(new BigDecimal("0.16"), sampleStock2.calculateDividendYield(new BigDecimal("50")));
        assertEquals(new BigDecimal("0.08"), sampleStock2.calculateDividendYield(new BigDecimal("100")));

        Stock sampleStock3 = new Stock("TEA", Stock.Type.COMMON, new BigDecimal("0"), null, new BigDecimal("100"));
        assertEquals(new BigDecimal("0.00"), sampleStock3.calculateDividendYield(new BigDecimal("30")));
        assertEquals(new BigDecimal("0.00"), sampleStock3.calculateDividendYield(new BigDecimal("60")));
    }

    @Test
    public void testGetPriceToEarningsRatio() {
        Stock sampleStock = new Stock("POP", Stock.Type.COMMON, new BigDecimal("8"), null, new BigDecimal("100"));
        assertEquals(new BigDecimal("2.00"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("16")));
        assertEquals(new BigDecimal("2.25"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("18")));
        assertEquals(new BigDecimal("2.50"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("20")));
    }

    @Test
    public void testRecordTrade() {
        Stock sampleStock = new Stock("POP", Stock.Type.COMMON, new BigDecimal("8"), null, new BigDecimal("100"));

        Trade testTrade = new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("50"));
        Trade testTrade2 = new Trade(new DateTime(), 12, Trade.Type.SELL, new BigDecimal("40"));
        sampleStock.recordTrade(testTrade);

        assertEquals(true, sampleStock.getPastTrades().contains(testTrade));
        assertEquals(false, sampleStock.getPastTrades().contains(testTrade2));
    }

    @Test
    public void testGetVolumeWeightedStockPriceOfTradesInLastFifteenMins() {
        Stock sampleStock = new Stock("JOE", Stock.Type.COMMON, new BigDecimal("8"), null, new BigDecimal("250"));

        Trade testTrade = new Trade(new DateTime().minusMinutes(20), 50, Trade.Type.BUY, new BigDecimal("90"));
        Trade testTrade2 = new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("10"));
        Trade testTrade3 = new Trade(new DateTime(), 10, Trade.Type.SELL, new BigDecimal("10"));
        sampleStock.recordTrade(testTrade2);
        sampleStock.recordTrade(testTrade3);

        assertEquals(new BigDecimal("10"), sampleStock.calculateVolumeWeightedStockPriceOfTradesInLastFifteenMins());
    }

    @Test
    public void testStockCreation() {
        Stock sampleStock = new Stock("ALE", Stock.Type.COMMON, new BigDecimal("23"), null, new BigDecimal("60"));
        assertEquals("ALE", sampleStock.getSymbol());
        assertEquals(Stock.Type.COMMON, sampleStock.getType());
        assertEquals(new BigDecimal("23"), sampleStock.getLastDividendInPennies());
        assertNull(sampleStock.getFixedDividendPercent());
        assertEquals(new BigDecimal("60"), sampleStock.getParValueInPennies());
        assertEquals("[Symbol: ALE, Type: COMMON, Last Dividend In Pennies: 23, " +
                "Fixed Dividend Percent: null, Par Value In Pennies: 60]", sampleStock.toString());
    }

}
