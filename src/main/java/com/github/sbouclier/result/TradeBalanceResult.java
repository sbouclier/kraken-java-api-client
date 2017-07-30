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
    public BigDecimal equivalentBalance;

    @JsonProperty("tb")
    public BigDecimal tradeBalance;

    @JsonProperty("m")
    public BigDecimal marginAmount;

    @JsonProperty("n")
    public BigDecimal unrealizedNetProfitLoss;

    @JsonProperty("c")
    public BigDecimal costBasis;

    @JsonProperty("v")
    public BigDecimal floatingValuation;

    @JsonProperty("e")
    public BigDecimal equity;

    @JsonProperty("mf")
    public BigDecimal freeMargin;

    @JsonProperty("ml")
    public BigDecimal marginLevel;

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
