package com.github.sbouclier;

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

    private MockInitHelper() {}

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
}
