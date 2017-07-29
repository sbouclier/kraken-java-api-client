package com.github.sbouclier.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Result from getServerTime
 *
 * @author Stéphane Bouclier
 */
public class ServerTimeResult extends Result<ServerTimeResult.ServerTime> {

    public static class ServerTime {
        public Long unixtime;
        public String rfc1123;

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("unixtime", unixtime)
                    .append("rfc1123", rfc1123)
                    .toString();
        }
    }
}
