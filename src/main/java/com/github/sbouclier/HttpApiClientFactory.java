package com.github.sbouclier;

import com.github.sbouclier.result.*;

/**
 * HttpApiClient factory
 *
 * @author Stéphane Bouclier
 */
public class HttpApiClientFactory {

    public HttpApiClient<? extends Result> getHttpApiClient(KrakenApiMethod method) {
        switch(method) {
            case SERVER_TIME:
                return new HttpApiClient<ServerTimeResult>();
            case ASSET_INFORMATION:
                return new HttpApiClient<AssetInformationResult>();
            case ASSET_PAIRS:
                return new HttpApiClient<AssetPairsResult>();
            case TICKER_INFORMATION:
                return new HttpApiClient<TickerInformationResult>();
            case OHLC:
                return new HttpApiClient<OHLCResult>();
            case ORDER_BOOK:
                return new HttpApiClient<OrderBookResult>();
            case RECENT_TRADES:
                return new HttpApiClient<RecentTradeResult>();
            case RECENT_SPREADS:
                return new HttpApiClient<RecentSpreadResult>();
            default:
                throw new IllegalArgumentException("Unknown Kraken API method");
        }
    }

    public HttpApiClient<? extends Result> getHttpApiClient(String apiKey, String apiSecret, KrakenApiMethod method) {
        switch(method) {
            case ACCOUNT_BALANCE:
                return new HttpApiClient<AccountBalanceResult>(apiKey, apiSecret);
            case TRADE_BALANCE:
                return new HttpApiClient<TradeBalanceResult>(apiKey, apiSecret);
            case OPEN_ORDERS:
                return new HttpApiClient<OpenOrdersResult>(apiKey, apiSecret);
            case CLOSED_ORDERS:
                return new HttpApiClient<ClosedOrdersResult>(apiKey, apiSecret);
            case ORDERS_INFORMATION:
                return new HttpApiClient<OrdersInformationResult>(apiKey, apiSecret);
            case TRADES_HISTORY:
                return new HttpApiClient<TradesHistoryResult>(apiKey, apiSecret);
            case TRADES_INFORMATION:
                return new HttpApiClient<TradesInformationResult>(apiKey, apiSecret);
            default:
                throw new IllegalArgumentException("Unknown Kraken API method");
        }
    }
}
