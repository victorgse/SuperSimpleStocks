package com.victorgse.stocks;

import com.victorgse.Trade;
import org.joda.time.DateTime;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigDecimal;

public class StockTest {

    @Test
    public void testGetDividendYield() {
        Stock sampleStock = new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("0.02"), new BigDecimal("100"));
        assertEquals(new BigDecimal("0.10"), sampleStock.calculateDividendYield(new BigDecimal("20")));
        assertEquals(new BigDecimal("0.16"), sampleStock.calculateDividendYield(new BigDecimal("12.5")));

        Stock sampleStock2 = new CommonStock("JOE", new BigDecimal("8"), new BigDecimal("250"));
        assertEquals(new BigDecimal("0.16"), sampleStock2.calculateDividendYield(new BigDecimal("50")));
        assertEquals(new BigDecimal("0.08"), sampleStock2.calculateDividendYield(new BigDecimal("100")));

        Stock sampleStock3 = new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100"));
        assertEquals(new BigDecimal("0.00"), sampleStock3.calculateDividendYield(new BigDecimal("30")));
        assertEquals(new BigDecimal("0.00"), sampleStock3.calculateDividendYield(new BigDecimal("60")));
    }

    @Test
    public void testGetPriceToEarningsRatio() {
        Stock sampleStock = new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));
        assertEquals(new BigDecimal("2.00"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("16")));
        assertEquals(new BigDecimal("2.25"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("18")));
        assertEquals(new BigDecimal("2.50"), sampleStock.calculatePriceToEarningsRatio(new BigDecimal("20")));
    }

    @Test
    public void testRecordTrade() {
        Stock sampleStock = new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100"));

        Trade testTrade = new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("50"));
        Trade testTrade2 = new Trade(new DateTime(), 12, Trade.Type.SELL, new BigDecimal("40"));
        sampleStock.recordTrade(testTrade);

        assertEquals(true, sampleStock.getPastTrades().contains(testTrade));
        assertEquals(false, sampleStock.getPastTrades().contains(testTrade2));
    }

    @Test
    public void testGetVolumeWeightedStockPriceOfTradesInLastFifteenMins() {
        Stock sampleStock = new CommonStock("JOE", new BigDecimal("8"), new BigDecimal("250"));

        Trade testTrade = new Trade(new DateTime().minusMinutes(20), 50, Trade.Type.BUY, new BigDecimal("90"));
        Trade testTrade2 = new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("10"));
        Trade testTrade3 = new Trade(new DateTime(), 10, Trade.Type.SELL, new BigDecimal("10"));
        sampleStock.recordTrade(testTrade2);
        sampleStock.recordTrade(testTrade3);

        assertEquals(new BigDecimal("10"), sampleStock.calculateVolumeWeightedStockPriceOfTradesInLastFifteenMins());
    }

    @Test
    public void testStockCreation() {
        CommonStock sampleStock = new CommonStock("ALE", new BigDecimal("23"), new BigDecimal("60"));
        assertEquals("ALE", sampleStock.getSymbol());
        assertEquals(new BigDecimal("23"), sampleStock.getLastDividendInPennies());
        assertEquals(new BigDecimal("60"), sampleStock.getParValueInPennies());
        assertEquals("[Symbol: ALE, Last Dividend In Pennies: 23, " +
                "Par Value In Pennies: 60]", sampleStock.toString());

        PreferredStock sampleStock2 = new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("0.02"), new BigDecimal("100"));
        assertEquals("GIN", sampleStock2.getSymbol());
        assertEquals(new BigDecimal("8"), sampleStock2.getLastDividendInPennies());
        assertEquals(new BigDecimal("0.02"), sampleStock2.getFixedDividendPercent());
        assertEquals(new BigDecimal("100"), sampleStock2.getParValueInPennies());
        assertEquals("[Symbol: GIN, Last Dividend In Pennies: 8, " +
                "Fixed Dividend Percent: 0.02, Par Value In Pennies: 100]", sampleStock2.toString());
    }

}
