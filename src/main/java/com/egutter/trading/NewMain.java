package com.egutter.trading;

import com.egutter.trading.out.StatsPrinter;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.runner.TradeOneDayRunner;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.google.gson.Gson;
import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static spark.Spark.*;

/**
 * Created by egutter on 1/3/16.
 */
public class NewMain {

    public static void main2(String[] args) {
        Gson gson = new Gson();
        get("/candidate-stats", (req, res) -> buildCandidateStats(), gson::toJson);
        get("/stock-stats", (req, res) -> buildStockStats(), gson::toJson);
    }

    private static Map buildCandidateStats() {
        return statsCollector().collectCandidateStats();
    }

    private static Map buildStockStats() {
        return statsCollector().collectStockStats();
    }

    private static StatsPrinter statsCollector() {
        LocalDate fromDate = new LocalDate(2014, 1, 1);
        LocalDate toDate = LocalDate.now();
        PortfolioRepository portfolioRepository = new PortfolioRepository();
        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);

        return new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates());
    }


}
