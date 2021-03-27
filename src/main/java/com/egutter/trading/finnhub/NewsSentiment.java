package com.egutter.trading.finnhub;

public class NewsSentiment {
    private NewsBuzz buzz;
    private float companyNewsScore;
    private float sectorAverageBullishPercent;
    private float sectorAverageNewsScore;
    private Sentiment sentiment;
    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public float getSectorAverageNewsScore() {
        return sectorAverageNewsScore;
    }

    public void setSectorAverageNewsScore(float sectorAverageNewsScore) {
        this.sectorAverageNewsScore = sectorAverageNewsScore;
    }

    public NewsBuzz getBuzz() {
        return buzz;
    }

    public void setBuzz(NewsBuzz buzz) {
        this.buzz = buzz;
    }

    public float getCompanyNewsScore() {
        return companyNewsScore;
    }

    public void setCompanyNewsScore(float companyNewsScore) {
        this.companyNewsScore = companyNewsScore;
    }

    public float getSectorAverageBullishPercent() {
        return sectorAverageBullishPercent;
    }

    public void setSectorAverageBullishPercent(float sectorAverageBullishPercent) {
        this.sectorAverageBullishPercent = sectorAverageBullishPercent;
    }
}
