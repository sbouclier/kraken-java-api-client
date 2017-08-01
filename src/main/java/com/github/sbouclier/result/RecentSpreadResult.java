package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Result from getRecentSpread
 *
 * @author Stéphane Bouclier
 */
public class RecentSpreadResult extends ResultWithLastId<Map<String, List<RecentSpreadResult.Spread>>> {

    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    @JsonPropertyOrder({"time", "bid", "ask"})
    public static class Spread {
        public Integer time;
        public BigDecimal bid;
        public BigDecimal ask;

        private Spread() {}

        public Spread(Integer time, BigDecimal bid, BigDecimal ask) {
            this.time = time;
            this.bid = bid;
            this.ask = ask;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("time", time)
                    .append("bid", bid)
                    .append("ask", ask)
                    .toString();
        }
    }
}
