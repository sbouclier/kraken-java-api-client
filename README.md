[![Build Status](https://travis-ci.org/sbouclier/kraken-java-api-client.svg?branch=master)](https://travis-ci.org/sbouclier/kraken-java-api-client)

# kraken-java-api-client
Java client library for use with the kraken.com API.

# Public market data

## Get server time

```java
KrakenAPIClient client = new KrakenAPIClient();
ServerTimeResult result = client.getServerTime();
```

Others methods coming soon...