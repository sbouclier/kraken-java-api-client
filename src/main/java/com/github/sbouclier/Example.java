package com.github.sbouclier;

import com.github.sbouclier.result.*;

import java.util.Arrays;

public class Example {
    public static void main(String[] args) throws KrakenApiException {
        KrakenAPIClient client = new KrakenAPIClient(
                "x2+8rqaYn3AFOmci9/xlAHAC6kbucKQzbPsUmHGW05OZEXFLt0ut0yLO",
                "C1StO/vIJdDJ0B/oqAUiOVkmivjf/ud58UL1BwxP1bOt+b70dvXv/7MB3siPJDDJza6tFtkg4u3Vf7ZQxaKqfQ==");

        ServerTimeResult serverTimeResult = client.getServerTime();
        System.out.println(serverTimeResult.getResult());

        AssetInformationResult resultAssertInfo = client.getAssetInformation();
        System.out.println("resultAssertInfo:"+resultAssertInfo.getResult());

        AssetPairsResult assetPairsResult = client.getAssetPairs();
        System.out.println(assetPairsResult);

        TickerInformationResult tickerInformationResult = client.getTickerInformation(Arrays.asList("BTCEUR", "ETHEUR"));
        System.out.println(tickerInformationResult);

        OHLCResult resultOHLC = client.getOHLC("XXBTZEUR", KrakenAPIClient.Interval.ONE_DAY);
        System.out.println("resultOHLC:"+resultOHLC);

        OrderBookResult orderBookResult = client.getOrderBook("BTCEUR");
        System.out.println(orderBookResult);

        RecentTradeResult recentTradeResult = client.getRecentTrades("BTCEUR");
        System.out.println(recentTradeResult.getResult());
        System.out.println("last id: "+recentTradeResult.getLastId());

        RecentSpreadResult recentSpreadResult = client.getRecentSpreads("BTCEUR");
        System.out.println(recentSpreadResult.getResult());
        System.out.println("last id: " + recentSpreadResult.getLastId());

        AccountBalanceResult accountBalanceResult = client.getAccountBalance();
        accountBalanceResult.getResult().forEach((currency, balance) -> System.out.println(currency + " = " + balance));
        System.out.println(accountBalanceResult.getResult());

        TradeBalanceResult tradeBalanceResult = client.getTradeBalance();
        System.out.println(tradeBalanceResult.getResult());

        OpenOrdersResult openOrders = client.getOpenOrders();
        System.out.println(openOrders.getResult());

        ClosedOrdersResult closedOrders = client.getClosedOrders();
        System.out.println(closedOrders.getResult());

        OrdersInformationResult ordersInformationResult = client.getOrdersInformation(Arrays.asList("OGRQC4-Q5C5N-2EYZDP"));
        System.out.println(ordersInformationResult.getResult());
        ordersInformationResult.getResult().forEach((txid, order) -> System.out.println(txid + " = " + order.description.type));
    }
}
