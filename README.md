[![Build Status](https://travis-ci.org/sbouclier/kraken-java-api-client.svg?branch=master)](https://travis-ci.org/sbouclier/kraken-java-api-client)
[![Coverage Status](https://coveralls.io/repos/github/sbouclier/kraken-java-api-client/badge.svg?branch=master)](https://coveralls.io/github/sbouclier/kraken-java-api-client?branch=master)

# kraken-java-api-client
Java client library for use with the kraken.com API. Unlike others libraries which only provide JSON result, this library unmarshal the JSON encoded data to the corresponding POJO.

# Public market data

If you only need public data, you can initialize your client without any arguments:

```java
KrakenAPIClient client = new KrakenAPIClient();
```

## Get server time

```java
ServerTimeResult result = client.getServerTime();
System.out.println(result.getResult());
// print ServerTimeResult.ServerTime[unixtime=1498933144,rfc1123=Sat,  1 Jul 17 18:19:04 +0000]
```

## Get asset information

```java
AssetInfoResult result = client.getAssetInformation();
System.out.println(result.getResult());
// print a map of AssetInformation, example: {XETC=AssetInformationResult.AssetInformation[alternateName=ETC,assetClass=currency,decimals=10,displayDecimals=5], XETH=AssetInformationResult.AssetInformation[alternateName=ETH,assetClass=currency,decimals=10,displayDecimals=5],...}
```

## Get tradable asset pairs

```java
AssetPairsResult result = client.getAssetPairs();
System.out.println(result.getResult());
// print a map of AssetPair
```

## Get ticker information

```java
TickerInformationResult result = client.getTickerInformation(Arrays.asList("BTCEUR","ETHEUR"));
System.out.println(result.getResult());
// print a map of TickerInformation
```

## Get OHLC

```java
OHLCResult result = client.getOHLC("BTCEUR", Interval.ONE_MINUTE);
System.out.println(result.getResult()); // OHLC data
System.out.println(result.getLast()); // last id
```

## Get order book

```java
OrderBookResult result = client.getOrderBook("BTCEUR");
System.out.println(result);
```

## Get recent trades

```java
RecentTradeResult result = client.getRecentTrades("BTCEUR");
System.out.println(result.getResult());
System.out.println("last id: "+result.getLastId());
```

## Get recent spreads

```java
RecentSpreadResult result = client.getRecentSpreads("BTCEUR");
System.out.println(result.getResult());
System.out.println("last id: "+result.getLastId());
```

# Private user data

You must initialize your client with your API key and API secret:

```java
KrakenAPIClient client = new KrakenAPIClient("API KEY", "API SECRET");
```

## Get account balance

```java
AccountBalanceResult result = client.getAccountBalance();
result.getResult().forEach((currency, balance) -> System.out.println(currency + " = " + balance));

// print
ZEUR = 437.0389
XXBT = 3.0702043520
XXRP = 1500.00000000
...
```

## Get trade balance

```java
TradeBalanceResult result = client.getTradeBalance();
System.out.println(result.getResult());
```

## Get open orders

```java
OpenOrdersResult openOrders = client.getOpenOrders();
System.out.println(openOrders.getResult());
```

## Get closed orders

```java
ClosedOrdersResult closedOrders = client.getClosedOrders();
System.out.println(closedOrders.getResult());
```

## Get orders information

```java
OrdersInformationResult ordersInformationResult = client.getOrdersInformation(Arrays.asList("OGRQD4-Q5C5N-2EYZDP","OC7Z5B-NLAHB-6MQNLA"));
ordersInformationResult.getResult().forEach((txid, order) -> System.out.println(txid + " = " + order.description.type));

// print
OGRQD4-Q5C5N-2EYZDP = SELL
OC7Z5B-NLAHB-6MQNLA = BUY
```

## Get trades history

```java
TradesHistoryResult result = client.getTradesHistory();
System.out.println(result.getResult());
```

## Get trades information

```java
TradesInformationResult result = client.getTradesInformation(Arrays.asList("TBKW74-IIBSM-LPZRWW", "TW2JUT-MIK3P-RML5VC"));
System.out.println(result.getResult());
```

## Get open positions

```java
OpenPositionsResult result = client.getOpenPositions(Arrays.asList("TY3TFI-KXBN3-LEICZJ"));
System.out.println(result.getResult());
```

## Get ledgers information

```java
LedgersInformationResult result = client.getLedgersInformation();
System.out.println(result.getResult());
```

## Get trade volume

```java
TradeVolumeResult result = client.getTradeVolume();
System.out.println(String.format("trade volume %s %s",
    result.getResult().volume, result.getResult().currency));
    
// print
trade volume 773.2808 ZUSD
```

Others methods coming soon...