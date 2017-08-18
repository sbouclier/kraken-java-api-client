package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sbouclier.result.common.OrderDirection;
import com.github.sbouclier.result.common.OrderType;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result from getOpenPositions
 *
 * @author Stéphane Bouclier
 */
public class OpenPositionsResult extends Result<Map<String, OpenPositionsResult.OpenPosition>> {

    public static class OpenPosition {

        @JsonProperty("ordertxid")
        public String orderTransactionId;

        @JsonProperty("posstatus")
        public String positionStatus;

        @JsonProperty("pair")
        public String assetPair;

        @JsonProperty("time")
        public Long tradeTimestamp;

        @JsonProperty("type")
        public OrderDirection orderDirection;

        @JsonProperty("ordertype")
        public OrderType orderType;

        public BigDecimal price;

        public BigDecimal cost;

        public BigDecimal fee;

        @JsonProperty("vol")
        public BigDecimal volume;

        @JsonProperty("vol_closed")
        public BigDecimal volumeCosed;

        public BigDecimal margin;

        public String terms;

        @JsonProperty("rollovertm")
        public Long rolloverTimestamp;

        @JsonProperty("misc")
        public String miscellaneous;

        @JsonProperty("oflags")
        public String orderFlags;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("orderTransactionId", orderTransactionId)
                    .append("positionStatus", positionStatus)
                    .append("assetPair", assetPair)
                    .append("tradeTimestamp", tradeTimestamp)
                    .append("orderDirection", orderDirection)
                    .append("orderType", orderType)
                    .append("price", price)
                    .append("cost", cost)
                    .append("fee", fee)
                    .append("volume", volume)
                    .append("volumeCosed", volumeCosed)
                    .append("margin", margin)
                    .append("terms", terms)
                    .append("rolloverTimestamp", rolloverTimestamp)
                    .append("miscellaneous", miscellaneous)
                    .append("orderFlags", orderFlags)
                    .toString();
        }
    }
}
