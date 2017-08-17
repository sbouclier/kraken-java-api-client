package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result from getTradesInformation
 *
 * @author Stéphane Bouclier
 */
public class TradesInformationResult extends Result<Map<String, TradesInformationResult.TradeInformation>> {

    public static class TradeInformation {

        // TODO extract to OrderDirectionType
        public enum Type {
            BUY("buy"),
            SELL("sell");

            private String value;

            Type(String value) {
                this.value = value;
            }

            @JsonValue
            public String getValue() {
                return value;
            }
        }

        // TODO extract to OrderType
        public enum OrderType {
            MARKET("market"),
            LIMIT("limit"),
            STOP_LOSS("stop-loss"),
            TAKE_PROFIT("take-profit"),
            STOP_LOSS_PROFIT("stop-loss-profit"),
            STOP_LOSS_PROFIT_LIMIT("stop-loss-profit-limit"),
            STOP_LOSS_LIMIT("stop-loss-limit"),
            TAKE_PROFIT_LIMIT("take-profit-limit"),
            TRAILING_STOP("trailing-stop"),
            TRAILING_STOP_LIMIT("trailing-stop-limit"),
            STOP_LOSS_AND_LIMIT("stop-loss-and-limit"),
            SETTLE_POSITION("settle-position");

            private String value;

            OrderType(String value) {
                this.value = value;
            }

            @JsonValue
            public String getValue() {
                return value;
            }
        }

        @JsonProperty("ordertxid")
        public String orderTransactionId;

        @JsonProperty("pair")
        public String assetPair;

        @JsonProperty("time")
        public Long tradeTimestamp;

        public Type type;

        @JsonProperty("ordertype")
        public OrderType orderType;

        public BigDecimal price;

        public BigDecimal cost;

        public BigDecimal fee;

        @JsonProperty("vol")
        public BigDecimal volume;

        public BigDecimal margin;

        @JsonProperty("misc")
        public String miscellaneous;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("orderTransactionId", orderTransactionId)
                    .append("assetPair", assetPair)
                    .append("tradeTimestamp", tradeTimestamp)
                    .append("type", type)
                    .append("orderType", orderType)
                    .append("price", price)
                    .append("cost", cost)
                    .append("fee", fee)
                    .append("volume", volume)
                    .append("margin", margin)
                    .append("miscellaneous", miscellaneous)
                    .toString();
        }
    }
}
