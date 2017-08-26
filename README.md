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
ServerTimeResult serverTimeResult = client.getServerTime();
System.out.println(String.format("timestamp: %d => %s",
    serverTimeResult.getResult().unixtime,
    serverTimeResult.getResult().rfc1123));
```

Print:

```
timestamp: 1503232702 => Sun, 20 Aug 17 12:38:22 +0000
```


## Get assets information

You can retrieve all assets information:

```java
AssetsInformationResult assetsInfoResult = client.getAssetsInformation();
System.out.println(assetsInfoResult.getResult());

// print a map of all AssetsInformationResult
```

Or you can pass argument assets you want:

```java
AssetsInformationResult resultAssertInfo2 = client.getAssetsInformation("ZEUR", "XETH");

AssetsInformationResult.AssetInformation euro = resultAssertInfo2.getResult().get("ZEUR");
AssetsInformationResult.AssetInformation ethereum = resultAssertInfo2.getResult().get("XETH");

System.out.println(String.format("%s: %d decimals, %d dispaly decimals",
    euro.alternateName, euro.decimals, euro.displayDecimals));
System.out.println(String.format("%s: %d decimals, %d dispaly decimals",
    ethereum.alternateName, ethereum.decimals, ethereum.displayDecimals));
```

Print:

```
EUR: 4 decimals, 2 display decimals
ETH: 10 decimals, 5 display decimals
```


## Get tradable asset pairs

You can retrieve all assets pairs:

```java
AssetPairsResult assetPairsResult = client.getAssetPairs();
System.out.println(assetPairsResult.getResult());

// print a map of AssetPair
```

Or you can pass argument assets you want and what informations to retrieve, which can be: ALL, LEVERAGE, FEES, MARGIN.
For example, to retrieve margins for ETH/EUR and XBT/EUR:

```java
AssetPairsResult assetPairsResult = client.getAssetPairs(InfoInput.MARGIN,"XETHZEUR","XXBTZEUR");

AssetPairsResult.AssetPair ethEur = assetPairsResult.getResult().get("XETHZEUR");
AssetPairsResult.AssetPair xbtEUr = assetPairsResult.getResult().get("XXBTZEUR");

System.out.println(String.format("ETH/EUR margins: call %d, level %d",
    ethEur.marginCall, ethEur.marginLevel));
System.out.println(String.format("XBT/EUR margins: call %d, level %d", 
    xbtEUr.marginCall, xbtEUr.marginLevel));
```

Print:

```
ETH/EUR margins: call 80, level 40
XBT/EUR margins: call 80, level 40
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
System.out.println(result.getLastId()); // last id
```

Time frame intervals available are:
- ONE_MINUTE
- FIVE_MINUTES
- FIFTEEN_MINUTES
- THIRTY_MINUTES
- ONE_HOUR
- FOUR_HOURS
- ONE_DAY
- ONE_WEEK
- FIFTEEN_DAYS

If you don't want all data, you can also retrieve OHLC data since given id:

```java
OHLCResult result = client.getOHLC("BTCEUR", Interval.ONE_MINUTE, 1503691200);
...
```


## Get order book

```java
OrderBookResult orderBookResult = client.getOrderBook("BTCEUR");
orderBookResult.getResult().forEach((currency, orders) -> {
    System.out.println(currency + " asks: " + orders.asks);
    System.out.println(currency + " bids: " + orders.bids);
});
```

Print:

```
XXBTZEUR asks: [OrderBookResult.Market[price=3634.71100,volume=0.212,timestamp=1503740681], OrderBookResult.Market[price=3635.16100,volume=0.012,timestamp=1503740699], OrderBookResult.Market[price=3635.16200,volume=0.424,timestamp=1503740705], OrderBookResult.Market[price=3635.16300,volume=0.071,timestamp=1503739532], OrderBookResult.Market[price=3635.16400,volume=0.149,timestamp=1503740677], OrderBookResult.Market[price=3635.41000,volume=0.657,timestamp=1503739442], OrderBookResult.Market[price=3639.66700,volume=1.200,timestamp=1503740701], OrderBookResult.Market[price=3639.99900,volume=0.025,timestamp=1503740704], OrderBookResult.Market[price=3640.00000,volume=0.260,timestamp=1503740625], OrderBookResult.Market[price=3640.00500,volume=0.021,timestamp=1503739312], OrderBookResult.Market[price=3640.89800,volume=0.055,timestamp=1503740613], OrderBookResult.Market[price=3640.90000,volume=0.037,timestamp=1503739943], OrderBookResult.Market[price=3641.00000,volume=7.725,timestamp=1503739407], OrderBookResult.Market[price=3641.10000,volume=3.123,timestamp=1503739990], OrderBookResult.Market[price=3641.10100,volume=0.144,timestamp=1503739132], OrderBookResult.Market[price=3642.06300,volume=1.750,timestamp=1503740676], OrderBookResult.Market[price=3642.06400,volume=0.016,timestamp=1503738979], OrderBookResult.Market[price=3642.06600,volume=0.664,timestamp=1503738953], OrderBookResult.Market[price=3642.06700,volume=0.280,timestamp=1503738885], OrderBookResult.Market[price=3643.18800,volume=0.154,timestamp=1503738955], OrderBookResult.Market[price=3643.18900,volume=0.074,timestamp=1503738821], OrderBookResult.Market[price=3645.99700,volume=0.495,timestamp=1503738834], OrderBookResult.Market[price=3645.99800,volume=0.287,timestamp=1503739407], OrderBookResult.Market[price=3649.00000,volume=0.143,timestamp=1503739948], OrderBookResult.Market[price=3650.00000,volume=0.275,timestamp=1503739793], OrderBookResult.Market[price=3651.80200,volume=0.103,timestamp=1503738779], OrderBookResult.Market[price=3652.25800,volume=0.006,timestamp=1503740482], OrderBookResult.Market[price=3653.88700,volume=0.054,timestamp=1503739396], OrderBookResult.Market[price=3654.00100,volume=0.027,timestamp=1503738517], OrderBookResult.Market[price=3657.29500,volume=0.035,timestamp=1503740139], OrderBookResult.Market[price=3658.60000,volume=0.100,timestamp=1503740633], OrderBookResult.Market[price=3659.09500,volume=34.186,timestamp=1503739641], OrderBookResult.Market[price=3659.57000,volume=24.480,timestamp=1503740670], OrderBookResult.Market[price=3660.00000,volume=1.521,timestamp=1503739321], OrderBookResult.Market[price=3664.99200,volume=0.014,timestamp=1503739004], OrderBookResult.Market[price=3665.61600,volume=0.463,timestamp=1503740690], OrderBookResult.Market[price=3665.94000,volume=0.010,timestamp=1503740657], OrderBookResult.Market[price=3667.10000,volume=2.316,timestamp=1503740674], OrderBookResult.Market[price=3667.99900,volume=0.650,timestamp=1503740678], OrderBookResult.Market[price=3668.00000,volume=2.000,timestamp=1503740408], OrderBookResult.Market[price=3670.00000,volume=0.012,timestamp=1503738537], OrderBookResult.Market[price=3673.99100,volume=17.610,timestamp=1503740678], OrderBookResult.Market[price=3673.99400,volume=3.474,timestamp=1503740667], OrderBookResult.Market[price=3674.00000,volume=0.188,timestamp=1503737195], OrderBookResult.Market[price=3674.45600,volume=40.000,timestamp=1503740687], OrderBookResult.Market[price=3674.99000,volume=0.094,timestamp=1503738297], OrderBookResult.Market[price=3675.01200,volume=2.285,timestamp=1503737294], OrderBookResult.Market[price=3677.73200,volume=0.449,timestamp=1503740578], OrderBookResult.Market[price=3679.00000,volume=0.011,timestamp=1503737464], OrderBookResult.Market[price=3679.14900,volume=3.000,timestamp=1503735402], OrderBookResult.Market[price=3679.62200,volume=0.003,timestamp=1503738771], OrderBookResult.Market[price=3679.99900,volume=1.189,timestamp=1503740455], OrderBookResult.Market[price=3680.00000,volume=19.352,timestamp=1503736725], OrderBookResult.Market[price=3680.12100,volume=6.000,timestamp=1503733715], OrderBookResult.Market[price=3680.75000,volume=0.208,timestamp=1503711691], OrderBookResult.Market[price=3684.00000,volume=0.003,timestamp=1503739029], OrderBookResult.Market[price=3687.67000,volume=0.500,timestamp=1503732888], OrderBookResult.Market[price=3688.90000,volume=0.100,timestamp=1503734176], OrderBookResult.Market[price=3689.89167,volume=0.035,timestamp=1503735488], OrderBookResult.Market[price=3689.98000,volume=0.002,timestamp=1503715965], OrderBookResult.Market[price=3690.00000,volume=0.500,timestamp=1503737652], OrderBookResult.Market[price=3692.00000,volume=0.420,timestamp=1503738601], OrderBookResult.Market[price=3693.30000,volume=0.100,timestamp=1503711780], OrderBookResult.Market[price=3694.25900,volume=0.100,timestamp=1503740654], OrderBookResult.Market[price=3694.69600,volume=13.520,timestamp=1503739700], OrderBookResult.Market[price=3694.69800,volume=1.000,timestamp=1503727503], OrderBookResult.Market[price=3694.99300,volume=0.100,timestamp=1503732156], OrderBookResult.Market[price=3695.00000,volume=4.000,timestamp=1503735456], OrderBookResult.Market[price=3695.34100,volume=0.375,timestamp=1503707900], OrderBookResult.Market[price=3695.51000,volume=0.443,timestamp=1503718223], OrderBookResult.Market[price=3695.99000,volume=0.024,timestamp=1503728993], OrderBookResult.Market[price=3696.02900,volume=0.003,timestamp=1503710369], OrderBookResult.Market[price=3696.34600,volume=0.010,timestamp=1503736276], OrderBookResult.Market[price=3696.57500,volume=0.020,timestamp=1503736039], OrderBookResult.Market[price=3697.00000,volume=0.004,timestamp=1503734806], OrderBookResult.Market[price=3697.33300,volume=0.014,timestamp=1503713696], OrderBookResult.Market[price=3698.99500,volume=24.540,timestamp=1503739677], OrderBookResult.Market[price=3698.99600,volume=34.220,timestamp=1503739637], OrderBookResult.Market[price=3699.00000,volume=1.031,timestamp=1503739052], OrderBookResult.Market[price=3700.00000,volume=42.484,timestamp=1503739875], OrderBookResult.Market[price=3700.13300,volume=2.000,timestamp=1503730548], OrderBookResult.Market[price=3700.70000,volume=0.004,timestamp=1503724248], OrderBookResult.Market[price=3700.99000,volume=8.330,timestamp=1503733151], OrderBookResult.Market[price=3701.00000,volume=0.560,timestamp=1503705570], OrderBookResult.Market[price=3701.42300,volume=0.041,timestamp=1503735167], OrderBookResult.Market[price=3701.50000,volume=0.023,timestamp=1503738535], OrderBookResult.Market[price=3703.33400,volume=1.000,timestamp=1503722157], OrderBookResult.Market[price=3703.65000,volume=0.050,timestamp=1503702213], OrderBookResult.Market[price=3703.70000,volume=0.004,timestamp=1503710594], OrderBookResult.Market[price=3705.00000,volume=0.115,timestamp=1503740019], OrderBookResult.Market[price=3705.07197,volume=0.010,timestamp=1503702745], OrderBookResult.Market[price=3705.55000,volume=0.003,timestamp=1503707507], OrderBookResult.Market[price=3706.80000,volume=0.806,timestamp=1503717869], OrderBookResult.Market[price=3707.26200,volume=0.003,timestamp=1503738103], OrderBookResult.Market[price=3708.50000,volume=0.002,timestamp=1503707524], OrderBookResult.Market[price=3710.00000,volume=3.122,timestamp=1503738942], OrderBookResult.Market[price=3710.00100,volume=0.020,timestamp=1503732277], OrderBookResult.Market[price=3711.00000,volume=0.004,timestamp=1503723342], OrderBookResult.Market[price=3711.62800,volume=0.050,timestamp=1503703887]]
XXBTZEUR bids: [OrderBookResult.Market[price=3626.15600,volume=0.012,timestamp=1503740707], OrderBookResult.Market[price=3626.15500,volume=1.016,timestamp=1503740701], OrderBookResult.Market[price=3626.15400,volume=0.047,timestamp=1503740702], OrderBookResult.Market[price=3626.15000,volume=16.844,timestamp=1503740686], OrderBookResult.Market[price=3626.14900,volume=0.012,timestamp=1503740666], OrderBookResult.Market[price=3626.09800,volume=1.511,timestamp=1503740690], OrderBookResult.Market[price=3626.01000,volume=0.055,timestamp=1503740305], OrderBookResult.Market[price=3626.00000,volume=0.076,timestamp=1503740624], OrderBookResult.Market[price=3625.22200,volume=0.600,timestamp=1503740705], OrderBookResult.Market[price=3625.01000,volume=0.372,timestamp=1503740539], OrderBookResult.Market[price=3625.00000,volume=3.975,timestamp=1503740677], OrderBookResult.Market[price=3623.00700,volume=0.022,timestamp=1503740700], OrderBookResult.Market[price=3622.99900,volume=0.020,timestamp=1503740705], OrderBookResult.Market[price=3622.83000,volume=0.111,timestamp=1503740484], OrderBookResult.Market[price=3622.06600,volume=0.074,timestamp=1503739169], OrderBookResult.Market[price=3622.06300,volume=0.003,timestamp=1503734199], OrderBookResult.Market[price=3621.12300,volume=10.640,timestamp=1503740702], OrderBookResult.Market[price=3621.12000,volume=0.004,timestamp=1503740498], OrderBookResult.Market[price=3620.83200,volume=0.033,timestamp=1503729135], OrderBookResult.Market[price=3620.80000,volume=2.318,timestamp=1503740707], OrderBookResult.Market[price=3620.50100,volume=10.210,timestamp=1503740687], OrderBookResult.Market[price=3620.50000,volume=0.301,timestamp=1503739323], OrderBookResult.Market[price=3620.27100,volume=0.012,timestamp=1503738642], OrderBookResult.Market[price=3620.10000,volume=0.054,timestamp=1503733922], OrderBookResult.Market[price=3620.00000,volume=2.655,timestamp=1503740248], OrderBookResult.Market[price=3618.17000,volume=0.800,timestamp=1503731484], OrderBookResult.Market[price=3616.79200,volume=0.500,timestamp=1503739619], OrderBookResult.Market[price=3616.78800,volume=3.885,timestamp=1503740695], OrderBookResult.Market[price=3616.78700,volume=3.871,timestamp=1503732793], OrderBookResult.Market[price=3616.11300,volume=0.102,timestamp=1503725534], OrderBookResult.Market[price=3616.00100,volume=0.099,timestamp=1503725385], OrderBookResult.Market[price=3616.00000,volume=0.597,timestamp=1503739442], OrderBookResult.Market[price=3615.10000,volume=0.040,timestamp=1503732395], OrderBookResult.Market[price=3615.00000,volume=30.265,timestamp=1503740058], OrderBookResult.Market[price=3613.77000,volume=0.300,timestamp=1503737917], OrderBookResult.Market[price=3613.00000,volume=0.126,timestamp=1503728728], OrderBookResult.Market[price=3612.34500,volume=0.094,timestamp=1503725311], OrderBookResult.Market[price=3612.33500,volume=0.088,timestamp=1503725280], OrderBookResult.Market[price=3612.20000,volume=0.500,timestamp=1503729979], OrderBookResult.Market[price=3612.00100,volume=1.750,timestamp=1503740670], OrderBookResult.Market[price=3612.00000,volume=0.800,timestamp=1503740136], OrderBookResult.Market[price=3611.02700,volume=0.101,timestamp=1503740689], OrderBookResult.Market[price=3611.00000,volume=0.152,timestamp=1503739755], OrderBookResult.Market[price=3610.12300,volume=3.493,timestamp=1503740631], OrderBookResult.Market[price=3610.10000,volume=0.030,timestamp=1503739854], OrderBookResult.Market[price=3610.00000,volume=0.369,timestamp=1503740121], OrderBookResult.Market[price=3607.99000,volume=0.015,timestamp=1503740002], OrderBookResult.Market[price=3606.00000,volume=0.534,timestamp=1503740435], OrderBookResult.Market[price=3605.00000,volume=3.364,timestamp=1503739938], OrderBookResult.Market[price=3604.00000,volume=2.070,timestamp=1503736643], OrderBookResult.Market[price=3603.67000,volume=0.250,timestamp=1503724428], OrderBookResult.Market[price=3603.20000,volume=0.250,timestamp=1503739688], OrderBookResult.Market[price=3602.83000,volume=0.030,timestamp=1503739952], OrderBookResult.Market[price=3602.50000,volume=0.750,timestamp=1503726723], OrderBookResult.Market[price=3602.00000,volume=0.664,timestamp=1503737049], OrderBookResult.Market[price=3601.44700,volume=0.100,timestamp=1503725447], OrderBookResult.Market[price=3601.00000,volume=0.378,timestamp=1503739472], OrderBookResult.Market[price=3600.11300,volume=12.290,timestamp=1503739373], OrderBookResult.Market[price=3600.11200,volume=28.280,timestamp=1503739302], OrderBookResult.Market[price=3600.11100,volume=0.050,timestamp=1503725938], OrderBookResult.Market[price=3600.10000,volume=3.682,timestamp=1503740653], OrderBookResult.Market[price=3600.01000,volume=10.024,timestamp=1503732560], OrderBookResult.Market[price=3600.00000,volume=50.932,timestamp=1503740577], OrderBookResult.Market[price=3599.66700,volume=0.293,timestamp=1503725616], OrderBookResult.Market[price=3597.00000,volume=6.500,timestamp=1503729547], OrderBookResult.Market[price=3596.99900,volume=0.420,timestamp=1503701201], OrderBookResult.Market[price=3596.32300,volume=0.100,timestamp=1503740640], OrderBookResult.Market[price=3596.28200,volume=0.100,timestamp=1503718056], OrderBookResult.Market[price=3596.00000,volume=1.000,timestamp=1503728871], OrderBookResult.Market[price=3595.99900,volume=0.100,timestamp=1503681508], OrderBookResult.Market[price=3595.00000,volume=0.010,timestamp=1503739956], OrderBookResult.Market[price=3594.82400,volume=20.000,timestamp=1503739736], OrderBookResult.Market[price=3594.45100,volume=0.100,timestamp=1503725086], OrderBookResult.Market[price=3591.42100,volume=0.021,timestamp=1503717655], OrderBookResult.Market[price=3591.11000,volume=0.100,timestamp=1503674727], OrderBookResult.Market[price=3591.01100,volume=0.014,timestamp=1503673072], OrderBookResult.Market[price=3590.12000,volume=0.028,timestamp=1503675905], OrderBookResult.Market[price=3590.00400,volume=0.400,timestamp=1503619033], OrderBookResult.Market[price=3590.00200,volume=0.003,timestamp=1503676370], OrderBookResult.Market[price=3590.00000,volume=1.122,timestamp=1503738318], OrderBookResult.Market[price=3589.99900,volume=4.000,timestamp=1503663880], OrderBookResult.Market[price=3589.00000,volume=0.030,timestamp=1503696348], OrderBookResult.Market[price=3588.77800,volume=0.060,timestamp=1503693389], OrderBookResult.Market[price=3588.55100,volume=0.033,timestamp=1503738584], OrderBookResult.Market[price=3588.14500,volume=0.006,timestamp=1503717153], OrderBookResult.Market[price=3588.00000,volume=0.522,timestamp=1503740109], OrderBookResult.Market[price=3587.90000,volume=0.007,timestamp=1503740102], OrderBookResult.Market[price=3587.11100,volume=0.126,timestamp=1503734194], OrderBookResult.Market[price=3585.52000,volume=0.014,timestamp=1503716878], OrderBookResult.Market[price=3585.00100,volume=0.200,timestamp=1503619229], OrderBookResult.Market[price=3585.00000,volume=1.337,timestamp=1503690823], OrderBookResult.Market[price=3584.01000,volume=0.024,timestamp=1503615518], OrderBookResult.Market[price=3583.00000,volume=0.120,timestamp=1503730709], OrderBookResult.Market[price=3582.15000,volume=0.100,timestamp=1503740475], OrderBookResult.Market[price=3581.29700,volume=28.610,timestamp=1503740683], OrderBookResult.Market[price=3581.29600,volume=37.230,timestamp=1503740681], OrderBookResult.Market[price=3581.29400,volume=0.128,timestamp=1503689824], OrderBookResult.Market[price=3581.08300,volume=0.081,timestamp=1503626423], OrderBookResult.Market[price=3581.01200,volume=0.100,timestamp=1503600394], OrderBookResult.Market[price=3580.85000,volume=0.047,timestamp=1503697375]]
```

If you don't want all data, you can set the maximum number of asks/bids you need:

```java
client.getOrderBook("BTCEUR", 2).getResult().forEach((currency, orders) -> {
    System.out.println(currency + " asks: " + orders.asks);
    System.out.println(currency + " bids: " + orders.bids);
});
```

Print:

```
XXBTZEUR asks: [OrderBookResult.Market[price=3634.71100,volume=0.212,timestamp=1503740681]]
XXBTZEUR bids: [OrderBookResult.Market[price=3626.15500,volume=1.016,timestamp=1503740701]]
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