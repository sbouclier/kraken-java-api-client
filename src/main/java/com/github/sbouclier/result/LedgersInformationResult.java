package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.sbouclier.result.common.LedgerInformation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * Result from getLedgersInformation
 *
 * @author Stéphane Bouclier
 */
public class LedgersInformationResult extends Result<LedgersInformationResult.LedgersInformation> {

    public static class LedgersInformation {

        @JsonProperty("ledger")
        public Map<String, LedgerInformation> ledger;

        public Long count;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("ledger", ledger)
                    .append("count", count)
                    .toString();
        }
    }
}
