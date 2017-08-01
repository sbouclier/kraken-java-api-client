package com.github.sbouclier;

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
}
