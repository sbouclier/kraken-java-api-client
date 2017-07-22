package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result from getOpenOrders
 *
 * @author Stéphane Bouclier
 */
public class OpenOrdersResult extends Result<OpenOrdersResult.OpenOrders> {

    public static class OpenOrders {

        @JsonProperty("open")
        public Map<String, OpenOrdersResult.OpenOrder> open;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("open", open)
                    .toString();
        }
    }

    public static class OpenOrder {

        public enum Status {
            PENDING("pending"),
            OPEN("open"),
            CLOSED("closed"),
            CANCELED("canceled"),
            EXPIRED("expired");

            private String value;

            Status(String value) {
                this.value = value;
            }

            @JsonValue
            public String getValue() {
                return value;
            }
        }

        public static class Description {

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

            @JsonProperty("pair")
            public String assetPair;

            public Type type;

            @JsonProperty("ordertype")
            public OrderType orderType;

            public BigDecimal price;

            @JsonProperty("price2")
            public BigDecimal secondaryPrice;

            public String leverage;

            public String order;

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("assetPair", assetPair)
                        .append("type", type)
                        .append("orderType", orderType)
                        .append("price", price)
                        .append("secondaryPrice", secondaryPrice)
                        .append("leverage", leverage)
                        .append("order", order)
                        .toString();
            }
        }

        @JsonProperty("refid")
        public String referralOrderTransactionId;

        @JsonProperty("userref")
        public String userReferenceId;

        public Status status;

        @JsonProperty("opentm")
        public Long openTimestamp;

        @JsonProperty("starttm")
        public Long orderStartTimestamp;

        @JsonProperty("expiretm")
        public Long orderEndTimestamp;

        @JsonProperty("descr")
        public Description description;

        @JsonProperty("vol")
        public BigDecimal volumeOrder;

        @JsonProperty("vol_exec")
        public BigDecimal volumeExecuted;

        public BigDecimal cost;

        public BigDecimal fee;

        @JsonProperty("price")
        public BigDecimal averagePrice;

        @JsonProperty("stopprice")
        public BigDecimal stopPrice;

        @JsonProperty("misc")
        public String miscellaneous;

        @JsonProperty("oflags")
        public String orderFlags;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("referralOrderTransactionId", referralOrderTransactionId)
                    .append("userReferenceId", userReferenceId)
                    .append("status", status)
                    .append("openTimestamp", openTimestamp)
                    .append("orderStartTimestamp", orderStartTimestamp)
                    .append("orderEndTimestamp", orderEndTimestamp)
                    .append("descr", description)
                    .append("volumeOrder", volumeOrder)
                    .append("volumeExecuted", volumeExecuted)
                    .append("cost", cost)
                    .append("fee", fee)
                    .append("averagePrice", averagePrice)
                    .append("stopPrice", stopPrice)
                    .append("miscellaneous", miscellaneous)
                    .append("orderFlags", orderFlags)
                    .toString();
        }
    }
}
