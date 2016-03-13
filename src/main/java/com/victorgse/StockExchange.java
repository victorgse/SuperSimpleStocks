package com.victorgse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class StockExchange {

    private String name;
    private Map<String, Stock> listedStocksMap;

    public StockExchange(String name, Map<String, Stock> listedStocksMap) {
        this.name = name;
        this.listedStocksMap = listedStocksMap;
    }

    public BigDecimal calculateAllShareIndexGeometricMean() {
        Iterator<String> iteratorOverAllStockSymbolsInIndex = this.listedStocksMap.keySet().iterator();
        int numberOfStocksTradedOnIndex = 0;
        BigDecimal productOfLastPricesOfStocksInIndex = new BigDecimal("0");
        while (iteratorOverAllStockSymbolsInIndex.hasNext()) {
            Stock stock = this.listedStocksMap.get(iteratorOverAllStockSymbolsInIndex.next());
            List<Trade> pastTradesOfStock = stock.getPastTrades();
            if (!pastTradesOfStock.isEmpty()) {
                Trade lastTradeOfStock = pastTradesOfStock.get(pastTradesOfStock.size() - 1);
                numberOfStocksTradedOnIndex++;
                if (productOfLastPricesOfStocksInIndex.signum() > 0) {
                    productOfLastPricesOfStocksInIndex = productOfLastPricesOfStocksInIndex.multiply(lastTradeOfStock.getTradePricePerShareInPennies());
                } else {
                    productOfLastPricesOfStocksInIndex = lastTradeOfStock.getTradePricePerShareInPennies();
                }
            }
        }
        BigDecimal allShareIndex;
        if (productOfLastPricesOfStocksInIndex.signum() <= 0 || numberOfStocksTradedOnIndex <= 0) {
            allShareIndex = new BigDecimal("0");
        } else {
            allShareIndex = calculateNthRootOfNumber(productOfLastPricesOfStocksInIndex, numberOfStocksTradedOnIndex).setScale(2, RoundingMode.HALF_UP);
        }
        return allShareIndex;
    }

    public BigDecimal calculateNthRootOfNumber(BigDecimal number, int degree) {
        return BigDecimal.valueOf(Math.pow(number.doubleValue(), 1.00/degree)).setScale(2, RoundingMode.HALF_UP);
    }

    public String getName() {
        return name;
    }

    public Map<String, Stock> getListedStocksMap() {
        return listedStocksMap;
    }

}
