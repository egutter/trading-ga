package com.egutter.trading.order;

import com.egutter.trading.stock.DailyQuote;
import com.egutter.trading.stock.Portfolio;
import com.egutter.trading.stock.TimeFrameQuote;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BuyConditionalOrder extends ConditionalOrder {
    private final DailyQuote dailyQuote;
    private final BigDecimal amountToInvest;
    public static int EXPIRE_IN_DAYS = 3;
    private ConditionalSellOrderFactory conditionalSellOrderFactory;
    private int expiresInDays;

    public BuyConditionalOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest, ConditionalSellOrderFactory conditionalSellOrderFactory, int expiresInDays) {
        super(stockName);
        this.dailyQuote = dailyQuote;
        this.amountToInvest = amountToInvest;
        this.conditionalSellOrderFactory = conditionalSellOrderFactory;
        this.expiresInDays = expiresInDays;
        expiresOn(expirationDate(dailyQuote.getTradingDate()));

    }
    public BuyConditionalOrder(String stockName, DailyQuote dailyQuote, BigDecimal amountToInvest, ConditionalSellOrderFactory conditionalSellOrderFactory) {
        this(stockName, dailyQuote, amountToInvest, conditionalSellOrderFactory, EXPIRE_IN_DAYS);
    }

    @Override
    protected void executeOrder(Portfolio portfolio, LocalDate tradingDate, TimeFrameQuote timeFrameQuote, OrderBook orderBook) {
        BigDecimal price = pricePaid(timeFrameQuote);
        int shares = sharesBought(price);
        BigDecimal amount = price.multiply(BigDecimal.valueOf(shares), new MathContext(2, RoundingMode.HALF_DOWN));

        BuyOrder buyOrder = buildConfirmedOrder(timeFrameQuote, shares, price);
        portfolio.buyStock(this.stockName, amount, buyOrder);

        // Add buy Order
        orderBook.add(buyOrder);
        // ADD Sell Orders
        conditionalSellOrderFactory.addSellOrdersTo(orderBook, buyOrder, shares);
    }

    private BuyOrder buildConfirmedOrder(TimeFrameQuote timeFrameQuote, int shares, BigDecimal price) {
        return new BuyOrder(this.stockName, timeFrameQuote.getQuoteAtDay(), shares, price);
    }

    private BigDecimal pricePaid(TimeFrameQuote timeFrameQuote) {
        return BigDecimal.valueOf(timeFrameQuote.getQuoteAtNextDay().getOpenPrice());
    }
    private int sharesBought(BigDecimal price) {
        return this.amountToInvest.divide(price, RoundingMode.HALF_EVEN).intValue();
    }

    private LocalDate expirationDate(LocalDate tradingDate) {
        int daysInAdvance = (tradingDate.getDayOfWeek() > 2) ? expiresInDays + 2 : expiresInDays;
        return tradingDate.plusDays(daysInAdvance);
    }

}
