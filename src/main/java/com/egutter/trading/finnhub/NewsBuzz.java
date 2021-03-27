package com.egutter.trading.finnhub;

public class NewsBuzz {

    private int articlesInLastWeek;
    private float buzz;
    private float weeklyAverage;

    public float getWeeklyAverage() {
        return weeklyAverage;
    }

    public void setWeeklyAverage(float weeklyAverage) {
        this.weeklyAverage = weeklyAverage;
    }

    public float getBuzz() {
        return buzz;
    }

    public void setBuzz(float buzz) {
        this.buzz = buzz;
    }

    public int getArticlesInLastWeek() {
        return articlesInLastWeek;
    }

    public void setArticlesInLastWeek(int articlesInLastWeek) {
        this.articlesInLastWeek = articlesInLastWeek;
    }
}
