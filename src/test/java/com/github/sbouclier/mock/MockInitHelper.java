package com.github.sbouclier.mock;

import com.github.sbouclier.result.OHLCResult;
import com.github.sbouclier.result.RecentSpreadResult;
import com.github.sbouclier.result.RecentTradeResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper for building {@link com.github.sbouclier.result.Result} mocks
 *
 * @author Stéphane Bouclier
 */
public class MockInitHelper {

    private MockInitHelper() {
    }

    public static RecentTradeResult buildRecentTradeResult() {
        RecentTradeResult mockResult = new RecentTradeResult();
        Map<String, List<RecentTradeResult.RecentTrade>> map = new HashMap<>();

        List<RecentTradeResult.RecentTrade> trades = new ArrayList<>();
        RecentTradeResult.RecentTrade trade1 = new RecentTradeResult.RecentTrade();
        trade1.price = BigDecimal.TEN;
        trade1.volume = BigDecimal.ONE;

        RecentTradeResult.RecentTrade trade2 = new RecentTradeResult.RecentTrade();
        trade2.price = BigDecimal.valueOf(20);
        trade2.volume = BigDecimal.valueOf(2);

        trades.add(trade1);
        trades.add(trade2);

        map.put("XXBTZEUR", trades);
        mockResult.setResult(map);
        mockResult.setLastId(123456L);

        return mockResult;
    }

    public static RecentSpreadResult buildRecentSpreadResult() {
        RecentSpreadResult mockResult = new RecentSpreadResult();
        Map<String, List<RecentSpreadResult.Spread>> map = new HashMap<>();

        List<RecentSpreadResult.Spread> spreads = new ArrayList<>();
        RecentSpreadResult.Spread spread1 = new RecentSpreadResult.Spread(1, BigDecimal.valueOf(10), BigDecimal.valueOf(11));
        RecentSpreadResult.Spread spread2 = new RecentSpreadResult.Spread(2, BigDecimal.valueOf(20), BigDecimal.valueOf(21));

        spreads.add(spread1);
        spreads.add(spread2);

        map.put("XXBTZEUR", spreads);
        mockResult.setResult(map);
        mockResult.setLastId(123456L);

        return mockResult;
    }

    public static OHLCResult buildOHLCResult() {
        OHLCResult mockResult = new OHLCResult();
        Map<String, List<OHLCResult.OHLC>> map = new HashMap<>();

        List<OHLCResult.OHLC> ohlcs = new ArrayList<>();

        OHLCResult.OHLC ohlc1 = new OHLCResult.OHLC();
        ohlc1.time = 1000;
        ohlc1.low = BigDecimal.valueOf(2000);
        ohlc1.high = BigDecimal.valueOf(2500);

        OHLCResult.OHLC ohlc2 = new OHLCResult.OHLC();
        ohlc2.time = 2000;
        ohlc2.low = BigDecimal.valueOf(1800);
        ohlc2.high = BigDecimal.valueOf(2100);

        ohlcs.add(ohlc1);
        ohlcs.add(ohlc2);

        map.put("XXBTZEUR", ohlcs);
        mockResult.setResult(map);
        mockResult.setLastId(23456L);

        return mockResult;
    }
}
