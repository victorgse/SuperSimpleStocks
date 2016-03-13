package com.victorgse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    private App() {
        //no explicit instances of this class
    }

    public static void main( String[] args ) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        StockExchange stockExchange = (StockExchange) context.getBean("stockExchange");
        LOGGER.info("StockExchange '" + stockExchange.getName() + "' retrieved from context.");

        Map<String, Stock> stocksListedOnExchange = stockExchange.getListedStocksMap();
        Iterator<String> iteratorOverAllStockSymbolsInIndex = stocksListedOnExchange.keySet().iterator();
        LOGGER.info("Stocks listed on exchange: ");
        while (iteratorOverAllStockSymbolsInIndex.hasNext()) {
            Stock stock = stocksListedOnExchange.get(iteratorOverAllStockSymbolsInIndex.next());
            LOGGER.info(stock.toString());
            BigDecimal dividendYieldAtFiftyPenniesPerShare = stock.calculateDividendYield(new BigDecimal("50"));
            LOGGER.info(String.format("Dividend Yield at 50 pennies per share market price for %s: %s",
                    stock.getSymbol(), dividendYieldAtFiftyPenniesPerShare));
            try {
                BigDecimal priceToEarningsRatioAtFiftyPenniesPerShare = stock.calculatePriceToEarningsRatio(new BigDecimal("50"));
                LOGGER.info(String.format("P/E Ratio at 50 pennies per share market price for %s: %s",
                        stock.getSymbol(), priceToEarningsRatioAtFiftyPenniesPerShare));
            } catch (ArithmeticException ae) {
                LOGGER.error(String.format("Unable to calculate P/E Ratio for %s as division by zero is not permitted",
                        stock.getSymbol()), ae);
            }
            LOGGER.info(stock.recordTrade(new Trade(null, 20, Trade.Type.BUY, new BigDecimal("40"))));
            LOGGER.info(stock.recordTrade(new Trade(null, 10, Trade.Type.SELL, new BigDecimal("50"))));
            LOGGER.info(String.format("Volume Weighted Stock Price for %s based on trades in past 15 minutes: %s",
                    stock.getSymbol(), stock.calculateVolumeWeightedStockPriceOfTradesInLastFifteenMins()));
        }

        LOGGER.info(String.format("All Share Index Geometic Mean for '%s': %s", stockExchange.getName(), stockExchange.calculateAllShareIndexGeometricMean()));
    }

}
