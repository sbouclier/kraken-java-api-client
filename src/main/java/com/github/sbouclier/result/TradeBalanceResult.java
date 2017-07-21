package com.github.sbouclier.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * Result from getTradeBalance
 *
 * @author Stéphane Bouclier
 */
public class TradeBalanceResult extends Result<TradeBalanceResult> {

    @JsonProperty("eb")
    private BigDecimal equivalentBalance;

    @JsonProperty("tb")
    private BigDecimal tradeBalance;

    @JsonProperty("m")
    private BigDecimal marginAmount;

    @JsonProperty("n")
    private BigDecimal unrealizedNetProfitLoss;

    @JsonProperty("c")
    private BigDecimal costBasis;

    @JsonProperty("v")
    private BigDecimal floatingValuation;

    @JsonProperty("e")
    private BigDecimal equity;

    @JsonProperty("mf")
    private BigDecimal freeMargin;

    @JsonProperty("ml")
    private BigDecimal marginLevel;

    public BigDecimal getEquivalentBalance() {
        return equivalentBalance;
    }

    public BigDecimal getTradeBalance() {
        return tradeBalance;
    }

    public BigDecimal getMarginAmount() {
        return marginAmount;
    }

    public BigDecimal getUnrealizedNetProfitLoss() {
        return unrealizedNetProfitLoss;
    }

    public BigDecimal getCostBasis() {
        return costBasis;
    }

    public BigDecimal getFloatingValuation() {
        return floatingValuation;
    }

    public BigDecimal getEquity() {
        return equity;
    }

    public BigDecimal getFreeMargin() {
        return freeMargin;
    }

    public BigDecimal getMarginLevel() {
        return marginLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("equivalentBalance", equivalentBalance)
                .append("tradeBalance", tradeBalance)
                .append("marginAmount", marginAmount)
                .append("unrealizedNetProfitLoss", unrealizedNetProfitLoss)
                .append("costBasis", costBasis)
                .append("floatingValuation", floatingValuation)
                .append("equity", equity)
                .append("freeMargin", freeMargin)
                .append("marginLevel", marginLevel)
                .toString();
    }
}
