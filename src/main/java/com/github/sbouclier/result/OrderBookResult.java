package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Result from getOrderBook
 *
 * @author Stéphane Bouclier
 */
public class OrderBookResult extends Result<Map<String, OrderBookResult.OrderBook>> {

    public static class OrderBook {
        public List<Market> asks;
        public List<Market> bids;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("asks", asks)
                    .append("bids", bids)
                    .toString();
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"price", "volume", "timestamp"})
    public static class Market {
        public BigDecimal price;
        public BigDecimal volume;
        public Integer timestamp;

        private Market() {}

        public Market(BigDecimal price, BigDecimal volume, Integer timestamp) {
            this.price = price;
            this.volume = volume;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("price", price)
                    .append("volume", volume)
                    .append("timestamp", timestamp)
                    .toString();
        }
    }
}
