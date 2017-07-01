package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Map;

/**
 * Result from getAssetInformation
 *
 * @author Stéphane Bouclier
 */
public class AssetInformationResult extends Result<Map<String, AssetInformationResult.AssetInformation>> {

    public static class AssetInformation {

        @JsonProperty("altname")
        private String alternateName;

        @JsonProperty("aclass")
        private String assetClass;

        private Byte decimals;

        @JsonProperty("display_decimals")
        private Byte displayDecimals;

        public String getAlternateName() {
            return alternateName;
        }

        public void setAlternateName(String alternateName) {
            this.alternateName = alternateName;
        }

        public String getAssetClass() {
            return assetClass;
        }

        public void setAssetClass(String assetClass) {
            this.assetClass = assetClass;
        }

        public Byte getDecimals() {
            return decimals;
        }

        public void setDecimals(Byte decimals) {
            this.decimals = decimals;
        }

        public Byte getDisplayDecimals() {
            return displayDecimals;
        }

        public void setDisplayDecimals(Byte displayDecimals) {
            this.displayDecimals = displayDecimals;
        }

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
