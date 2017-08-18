package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Result from getLedgers
 *
 * @author Stéphane Bouclier
 */
public class LedgersResult extends Result<Map<String, LedgersResult.LedgerInformation>> {

    public static class LedgerInformation {

        @JsonProperty("refid")
        public String referenceId;

        @JsonProperty("time")
        public Long timestamp;

        public String type;

        @JsonProperty("aclass")
        public String assetClass;

        public String asset;

        public BigDecimal amount;

        public BigDecimal fee;

        public BigDecimal balance;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("referenceId", referenceId)
                    .append("timestamp", timestamp)
                    .append("type", type)
                    .append("assetClass", assetClass)
                    .append("asset", asset)
                    .append("amount", amount)
                    .append("fee", fee)
                    .append("balance", balance)
                    .toString();
        }
    }
}
