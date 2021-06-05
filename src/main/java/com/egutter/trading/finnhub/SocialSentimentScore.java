package com.egutter.trading.finnhub;

import java.util.Date;

public class SocialSentimentScore {
    private Date atTime;
    private Integer mention;
    private Float positiveScore;
    private Float negativeScore;
    private Integer positiveMention;
    private Integer negativeMention;
    private Float score;

    public void setAtTime(Date atTime) {
        this.atTime = atTime;
    }

    public void setMention(Integer mention) {
        this.mention = mention;
    }

    public void setPositiveScore(Float positiveScore) {
        this.positiveScore = positiveScore;
    }

    public void setNegativeScore(Float negativeScore) {
        this.negativeScore = negativeScore;
    }

    public void setPositiveMention(Integer positiveMention) {
        this.positiveMention = positiveMention;
    }

    public void setNegativeMention(Integer negativeMention) {
        this.negativeMention = negativeMention;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getAtTime() {
        return atTime;
    }

    public Integer getMention() {
        return mention;
    }

    public Float getPositiveScore() {
        return positiveScore;
    }

    public Float getNegativeScore() {
        return negativeScore;
    }

    public Integer getPositiveMention() {
        return positiveMention;
    }

    public Integer getNegativeMention() {
        return negativeMention;
    }

    public Float getScore() {
        return score;
    }
}
