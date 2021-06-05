package com.egutter.trading.finnhub;

import java.util.List;

public class SocialSentiment {
    private List<SocialSentimentScore> reddit;
    private List<SocialSentimentScore> twitter;
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<SocialSentimentScore> getTwitter() {
        return twitter;
    }

    public void setTwitter(List<SocialSentimentScore> twitter) {
        this.twitter = twitter;
    }

    public List<SocialSentimentScore> getReddit() {
        return reddit;
    }
}
