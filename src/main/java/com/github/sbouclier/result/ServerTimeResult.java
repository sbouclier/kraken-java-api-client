package com.github.sbouclier.result;

import java.util.ArrayList;

/**
 * Result from getServerTime
 *
 * @author Stéphane Bouclier
 */
public class ServerTimeResult {

    private ArrayList<Object> error = new ArrayList<>();
    private Result result;

    public ArrayList<Object> getError() {
        return error;
    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "ServerTimeResult{" +
                "error=" + error +
                ", result=" + result +
                '}';
    }

    public static class Result {

        public Long unixtime;
        public String rfc1123;

        public Long getUnixtime() {
            return unixtime;
        }

        public String getRfc1123() {
            return rfc1123;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "unixtime=" + unixtime +
                    ", rfc1123='" + rfc1123 + '\'' +
                    '}';
        }
    }
}

