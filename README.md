[![Build Status](https://travis-ci.org/sbouclier/kraken-java-api-client.svg?branch=master)](https://travis-ci.org/sbouclier/kraken-java-api-client)
[![Coverage Status](https://coveralls.io/repos/github/sbouclier/kraken-java-api-client/badge.svg?branch=master)](https://coveralls.io/github/sbouclier/kraken-java-api-client?branch=master)

# kraken-java-api-client
Java client library for use with the kraken.com API.

# Public market data

## Get server time

```java
KrakenAPIClient client = new KrakenAPIClient();
ServerTimeResult result = client.getServerTime();
```

## Get asset information

```java
KrakenAPIClient client = new KrakenAPIClient();
AssetInfoResult result = client.getAssetInformation();
```

Others methods coming soon...