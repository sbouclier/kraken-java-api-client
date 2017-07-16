package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Result from getRecentTrades
 *
 * @author Stéphane Bouclier
 */
public class RecentTradeResult extends ResultWithLastId<Map<String, List<RecentTradeResult.RecentTrade>>> {

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"price", "volume", "time", "buySell", "marketLimit", "miscellaneous"})
    public static class RecentTrade {
        public BigDecimal price;
        public BigDecimal volume;
        public BigDecimal time;

        public Object buySell;
        public String marketLimit;
        public String miscellaneous;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("price", price)
                    .append("volume", volume)
                    .append("time", time)
                    .append("buySell", buySell)
                    .append("marketLimit", marketLimit)
                    .append("miscellaneous", miscellaneous)
                    .toString();
        }
    }
}
