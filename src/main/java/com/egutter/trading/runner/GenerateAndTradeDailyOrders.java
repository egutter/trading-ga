package com.egutter.trading.runner;

import com.studerw.tda.client.HttpTdaClient;
import com.studerw.tda.client.TdaClient;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateAndTradeDailyOrders {

    private final static Logger logger = LoggerFactory.getLogger(GenerateAndTradeDailyOrders.class);
    private final static LocalDate today = LocalDate.now();

    public static void main(String[] args) {
        String baseOrdersPath = "";
        if (args.length > 0){
            baseOrdersPath = args[0];
        }
        LocalTime startTime = LocalTime.now();
        LocalDate tradeOn = today;
        LocalDate lastTradeAt = isMonday(tradeOn) ? tradeOn.minusDays(3) : tradeOn.minusDays(1);

        TdaClient client = new HttpTdaClient();
        DailyOrderGenerator dailyOrderGenerator = new DailyOrderGenerator(client, tradeOn, baseOrdersPath);

        logger.info("Generate orders on "+ tradeOn);
        dailyOrderGenerator.runTraderAndGenerateOrders();

        logger.info("Trading on "+ tradeOn + " last trade at "+ lastTradeAt);
        DailyTrader dailyTrader = new DailyTrader(client, tradeOn, lastTradeAt, baseOrdersPath);
        dailyTrader.tradeGeneratedOrders();

        System.out.println("total time elapsed " + Seconds.secondsBetween(startTime, LocalTime.now()).getSeconds() + " seconds");
    }

    private static boolean isMonday(LocalDate aDate) {
        return (aDate.dayOfWeek().get() == DateTimeConstants.MONDAY);
    }

}
