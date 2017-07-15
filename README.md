[![Build Status](https://travis-ci.org/sbouclier/kraken-java-api-client.svg?branch=master)](https://travis-ci.org/sbouclier/kraken-java-api-client)
[![Coverage Status](https://coveralls.io/repos/github/sbouclier/kraken-java-api-client/badge.svg?branch=master)](https://coveralls.io/github/sbouclier/kraken-java-api-client?branch=master)

# kraken-java-api-client
Java client library for use with the kraken.com API.

# Public market data

You can initialize your client without any arguments:

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
System.out.println(result.getOHLCData()); // OHLC data
System.out.println(result.getLast()); // last id
```

## Get order book

```java
OrderBookResult result = client.getOrderBook("BTCEUR");
System.out.println(result);
```

Others methods coming soon...