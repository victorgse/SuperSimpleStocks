package com.victorgse;

import com.victorgse.stocks.CommonStock;
import com.victorgse.stocks.PreferredStock;
import com.victorgse.stocks.Stock;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StockExchangeTest {

    StockExchange stockExchange;

    @Before
    public void setUp() {
        Map<String, Stock> listedStocksMap = new HashMap<String, Stock>();
        listedStocksMap.put("TEA", new CommonStock("TEA", new BigDecimal("0"), new BigDecimal("100")));
        listedStocksMap.put("POP", new CommonStock("POP", new BigDecimal("8"), new BigDecimal("100")));
        listedStocksMap.put("ALE", new CommonStock("ALE", new BigDecimal("23"), new BigDecimal("60")));
        listedStocksMap.put("GIN", new PreferredStock("GIN", new BigDecimal("8"), new BigDecimal("0.02"), new BigDecimal("100")));
        listedStocksMap.put("JOE", new CommonStock("JOE", new BigDecimal("13"), new BigDecimal("250")));
        stockExchange = new StockExchange("Global Beverage Corporation Exchange", listedStocksMap);
    }

    @Test
    public void testCalculateAllShareIndexGeometricMean() {
        Map<String, Stock> listedStocksMap = stockExchange.getListedStocksMap();
        Iterator<String> iteratorOverAllStockSymbolsInIndex = listedStocksMap.keySet().iterator();
        while (iteratorOverAllStockSymbolsInIndex.hasNext()) {
            Stock stock = listedStocksMap.get(iteratorOverAllStockSymbolsInIndex.next());
            stock.recordTrade(new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("10")));
        }
        assertEquals(new BigDecimal("10.00"), stockExchange.calculateAllShareIndexGeometricMean());
    }

    @Test
    public void testCalculateNthRootOfNumberIsNotCalledWhenNoStocksHaveBeenTradedYet() {
        StockExchange spyOnStockExchange = spy(stockExchange);
        spyOnStockExchange.calculateAllShareIndexGeometricMean();
        verify(spyOnStockExchange, never()).calculateNthRootOfNumber(Mockito.any(BigDecimal.class), anyInt());
    }

    @Test
    public void testCalculateNthRootOfNumberIsCalledWhenStocksHaveBeenTradedAlready() {
        Stock additionalStock = new CommonStock("TEST", new BigDecimal("10"), new BigDecimal("50"));
        Trade additionalTrade = new Trade(new DateTime(), 10, Trade.Type.BUY, new BigDecimal("50"));
        additionalStock.recordTrade(additionalTrade);
        stockExchange.getListedStocksMap().put("TEST", additionalStock);

        StockExchange spyOnStockExchange = spy(stockExchange);
        spyOnStockExchange.calculateAllShareIndexGeometricMean();
        verify(spyOnStockExchange, times(1)).calculateNthRootOfNumber(Mockito.any(BigDecimal.class), anyInt());
    }

    @Test
    public void testStockExchangeCreation() {
        assertNotNull(stockExchange);
        assertEquals("Global Beverage Corporation Exchange", stockExchange.getName());
        Map<String, Stock> listedStocksMap = stockExchange.getListedStocksMap();
        assertNotNull(listedStocksMap);
        assertEquals(5, listedStocksMap.size());
    }

}
