package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * Result from getAssetsInformation
 *
 * @author Stéphane Bouclier
 */
public class AssetsInformationResult extends Result<Map<String, AssetsInformationResult.AssetInformation>> {

    public static class AssetInformation {

        @JsonProperty("altname")
        public String alternateName;

        @JsonProperty("aclass")
        public String assetClass;

        public Byte decimals;

        @JsonProperty("display_decimals")
        public Byte displayDecimals;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("alternateName", alternateName)
                    .append("assetClass", assetClass)
                    .append("decimals", decimals)
                    .append("displayDecimals", displayDecimals)
                    .toString();
        }
    }
}
