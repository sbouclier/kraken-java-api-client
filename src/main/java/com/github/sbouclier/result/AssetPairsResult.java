package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Map;

/**
 * Result from getAssetPairs
 *
 * @author Stéphane Bouclier
 */
public class AssetPairsResult extends Result<Map<String, AssetPairsResult.AssetPair>> {

    public static class AssetPair {

        @JsonFormat(shape = JsonFormat.Shape.ARRAY)
        @JsonPropertyOrder({"volume", "percent"})
        public static class Fee {

            private Integer volume;
            private Float percent;

            private Fee() {
            }

            public Fee(Integer volume, Float percent) {
                this.volume = volume;
                this.percent = percent;
            }

            public Integer getVolume() {
                return volume;
            }

            public Float getPercent() {
                return percent;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Fee fee = (Fee) o;

                return new EqualsBuilder()
                        .append(volume, fee.volume)
                        .append(percent, fee.percent)
                        .isEquals();
            }

            @Override
            public int hashCode() {
                return new HashCodeBuilder(17, 37).
                        append(volume).
                        append(percent).
                        toHashCode();
            }

            @Override
            public String toString() {
                return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                        .append("volume", volume)
                        .append("percent", percent)
                        .toString();
            }
        }

        @JsonProperty("altname")
        private String alternatePairName;

        @JsonProperty("aclass_base")
        private String baseAssetClass;

        @JsonProperty("base")
        private String baseAssetId;

        @JsonProperty("aclass_quote")
        private String quoteAssetClass;

        @JsonProperty("quote")
        private String quoteAssetId;

        private String lot;

        @JsonProperty("pair_decimals")
        private Integer pairDecimals;

        @JsonProperty("lot_decimals")
        private Integer lotDecimals;

        @JsonProperty("lot_multiplier")
        private Integer lotMultiplier;

        @JsonProperty("leverage_buy")
        private ArrayList<Integer> leverageBuy;

        @JsonProperty("leverage_sell")
        private ArrayList<Integer> leverageSell;

        private ArrayList<Fee> fees;

        @JsonProperty("fees_maker")
        private ArrayList<Fee> feesMaker;

        @JsonProperty("fee_volume_currency")
        private String feeVolumeCurrency;

        @JsonProperty("margin_call")
        private Integer marginCall;

        @JsonProperty("margin_stop")
        private Integer marginStop;

        public String getAlternatePairName() {
            return alternatePairName;
        }

        public void setAlternatePairName(String alternatePairName) {
            this.alternatePairName = alternatePairName;
        }

        public String getBaseAssetClass() {
            return baseAssetClass;
        }

        public void setBaseAssetClass(String baseAssetClass) {
            this.baseAssetClass = baseAssetClass;
        }

        public String getBaseAssetId() {
            return baseAssetId;
        }

        public void setBaseAssetId(String baseAssetId) {
            this.baseAssetId = baseAssetId;
        }

        public String getQuoteAssetClass() {
            return quoteAssetClass;
        }

        public void setQuoteAssetClass(String quoteAssetClass) {
            this.quoteAssetClass = quoteAssetClass;
        }

        public String getQuoteAssetId() {
            return quoteAssetId;
        }

        public void setQuoteAssetId(String quoteAssetId) {
            this.quoteAssetId = quoteAssetId;
        }

        public String getLot() {
            return lot;
        }

        public void setLot(String lot) {
            this.lot = lot;
        }

        public Integer getPairDecimals() {
            return pairDecimals;
        }

        public void setPairDecimals(Integer pairDecimals) {
            this.pairDecimals = pairDecimals;
        }

        public Integer getLotDecimals() {
            return lotDecimals;
        }

        public void setLotDecimals(Integer lotDecimals) {
            this.lotDecimals = lotDecimals;
        }

        public Integer getLotMultiplier() {
            return lotMultiplier;
        }

        public void setLotMultiplier(Integer lotMultiplier) {
            this.lotMultiplier = lotMultiplier;
        }

        public ArrayList<Integer> getLeverageBuy() {
            return leverageBuy;
        }

        public void setLeverageBuy(ArrayList<Integer> leverageBuy) {
            this.leverageBuy = leverageBuy;
        }

        public ArrayList<Integer> getLeverageSell() {
            return leverageSell;
        }

        public void setLeverageSell(ArrayList<Integer> leverageSell) {
            this.leverageSell = leverageSell;
        }

        public ArrayList<Fee> getFees() {
            return fees;
        }

        public void setFees(ArrayList<Fee> fees) {
            this.fees = fees;
        }

        public ArrayList<Fee> getFeesMaker() {
            return feesMaker;
        }

        public void setFeesMaker(ArrayList<Fee> feesMaker) {
            this.feesMaker = feesMaker;
        }

        public String getFeeVolumeCurrency() {
            return feeVolumeCurrency;
        }

        public void setFeeVolumeCurrency(String feeVolumeCurrency) {
            this.feeVolumeCurrency = feeVolumeCurrency;
        }

        public Integer getMarginCall() {
            return marginCall;
        }

        public void setMarginCall(Integer marginCall) {
            this.marginCall = marginCall;
        }

        public Integer getMarginStop() {
            return marginStop;
        }

        public void setMarginStop(Integer marginStop) {
            this.marginStop = marginStop;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("alternatePairName", alternatePairName)
                    .append("baseAssetClass", baseAssetClass)
                    .append("baseAssetId", baseAssetId)
                    .append("quoteAssetClass", quoteAssetClass)
                    .append("quoteAssetId", quoteAssetId)
                    .append("lot", lot)
                    .append("pairDecimals", pairDecimals)
                    .append("lotDecimals", lotDecimals)
                    .append("lotMultiplier", lotMultiplier)
                    .append("leverageBuy", leverageBuy)
                    .append("leverageSell", leverageSell)
                    .append("fees", fees)
                    .append("feesMaker", feesMaker)
                    .append("feeVolumeCurrency", feeVolumeCurrency)
                    .append("marginCall", marginCall)
                    .append("marginStop", marginStop)
                    .toString();
        }
    }
}
