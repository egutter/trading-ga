package com.egutter.trading.tda;

public class TooExpensiveOrder extends Exception {
    public TooExpensiveOrder(String message) {
        super(message);
    }
}
