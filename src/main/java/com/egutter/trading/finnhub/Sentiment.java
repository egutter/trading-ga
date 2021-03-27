package com.egutter.trading.finnhub;

public class Sentiment {

    private float bearishPercent;
    private float bullishPercent;

    public float getBullishPercent() {
        return bullishPercent;
    }

    public void setBullishPercent(float bullishPercent) {
        this.bullishPercent = bullishPercent;
    }

    public float getBearishPercent() {
        return bearishPercent;
    }

    public void setBearishPercent(float bearishPercent) {
        this.bearishPercent = bearishPercent;
    }
}
