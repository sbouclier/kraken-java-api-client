package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result from getTickerInformation
 *
 * @author Stéphane Bouclier
 */
public class TickerInformationResult extends Result<Map<String, TickerInformationResult.TickerInformation>> {

    public static class TickerInformation {

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"price", "wholeLotVolume", "lotVolume"})
        public static class PriceWholeLotVolume {
            public BigDecimal price;
            public Integer wholeLotVolume;
            public BigDecimal lotVolume;

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("price", price)
                        .append("wholeLotVolume", wholeLotVolume)
                        .append("lotVolume", lotVolume)
                        .toString();
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"price", "lotVolume"})
        public static class PriceLotVolume {
            public BigDecimal price;
            public BigDecimal lotVolume;

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("price", price)
                        .append("lotVolume", lotVolume)
                        .toString();
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"today", "last24hours"})
        public static class TodayLast24h {
            public BigDecimal today;
            public BigDecimal last24hours;

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("today", today)
                        .append("last24hours", last24hours)
                        .toString();
            }
        }

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"today", "last24hours"})
        public static class NumberOfTrade {
            public Integer today;
            public Integer last24hours;

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("today", today)
                        .append("last24hours", last24hours)
                        .toString();
            }
        }

        @JsonProperty("a")
        public PriceWholeLotVolume ask;

        @JsonProperty("b")
        public PriceWholeLotVolume bid;

        @JsonProperty("c")
        public PriceLotVolume lastTradeClosed;

        @JsonProperty("v")
        public TodayLast24h volume;

        @JsonProperty("p")
        public TodayLast24h volumeWeightAverage;

        @JsonProperty("t")
        public NumberOfTrade numberOfTrades;

        @JsonProperty("l")
        public TodayLast24h low;

        @JsonProperty("h")
        public TodayLast24h high;

        @JsonProperty("o")
        public BigDecimal todayOpenPrice;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("ask", ask)
                    .append("bid", bid)
                    .append("lastTradeClosed", lastTradeClosed)
                    .append("volume", volume)
                    .append("volumeWeightAverage", volumeWeightAverage)
                    .append("numberOfTrades", numberOfTrades)
                    .append("low", low)
                    .append("high", high)
                    .append("todayOpenPrice", todayOpenPrice)
                    .toString();
        }
    }
}
