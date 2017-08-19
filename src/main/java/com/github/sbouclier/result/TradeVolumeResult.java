package com.github.sbouclier.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Result from getTradeVolume
 *
 * @author Stéphane Bouclier
 */
public class TradeVolumeResult extends Result<TradeVolumeResult.TradeVolume> {

    public static class TradeVolume {

        public String currency;
        public BigDecimal volume;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("currency", currency)
                    .append("volume", volume)
                    .toString();
        }
    }
}
