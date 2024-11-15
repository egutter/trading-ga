package com.egutter.trading;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.egutter.trading.out.StatsPrinter;
import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.repository.MarketOrdersRepository;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.runner.TradeOneDayRunner;
import com.egutter.trading.runner.YahooQuoteImporter;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import com.egutter.trading.stock.StockPrices;
import com.google.common.base.Joiner;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.util.component.LifeCycle;
import org.joda.time.LocalDate;

import java.io.PrintWriter;
import java.util.stream.Stream;

public class Main extends HttpServlet {
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        try {
//            if (req.getRequestURI().endsWith("/stats")) {
//                showStats(req, resp);
//            } else if (req.getRequestURI().endsWith("/trade-one-day")) {
//                tradeOneDay(req, resp);
//            } else if (req.getRequestURI().endsWith("/run-alt-stocks-import")) {
//                importAltStocks(req, resp);
//            } else if (req.getRequestURI().endsWith("/buyStock")) {
//                buyStock(req, resp);
//            } else {
//                showHome(req, resp);
//            }
//        } catch (Exception e) {
//            PrintWriter writer = resp.getWriter();
//            writer.print("Error");
//            e.printStackTrace(writer);
//            sendEmail("Error " + e.getMessage() + "<br/>" + "Stacktrace:<br/>" + Joiner.on("<br/>").join(e.getStackTrace()));
//        }
//    }
//
//    private void buyStock(HttpServletRequest req, HttpServletResponse resp) {
//        String key = req.getParameter("key");
//        String stockName = req.getParameter("stockName");
//        MarketOrdersRepository repository = new MarketOrdersRepository();
//        repository.storeOrder(key, stockName);
//    }
//
//    private void importAltStocks(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        YahooQuoteImporter yahooQuoteImporter = new YahooQuoteImporter();
//        LocalDate fromDate = new LocalDate(2014, 1, 1);
//        LocalDate toDate = new LocalDate(2015, 1, 1);
//        yahooQuoteImporter.runImport(fromDate, toDate, StockMarket.altStockSymbols());
//        showHome(req, resp);
//    }
//
//    private void tradeOneDay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        LocalDate fromDate = new LocalDate(2014, 1, 1);
//        LocalDate toDate = LocalDate.now();
//        TradeOneDayRunner runner = new TradeOneDayRunner(fromDate, toDate);
//        runner.setRequestUrl(req.getServerName());
//        runner.run(false);
//        PrintWriter writer = resp.getWriter();
//        writer.print("<h1>Success!</h1>");
//        writer.print(runner.runOutput("<br/>"));
//        sendEmail(runner.runOutput("<br />"));
//    }
//
//    private void sendEmail(String html) {
//        SendGrid sendgrid = new SendGrid(System.getenv().get("SENDGRID_USERNAME"), System.getenv().get("SENDGRID_PASSWORD"));
//        String candidateFactory = System.getenv().get("CANDIDATE_FACTORY");
//        SendGrid.Email email = new SendGrid.Email().
//                addTo("egutter@gmail.com").
//                setFrom("your@trader.com").
//                setSubject("One day trader runner - " + candidateFactory).
//                setHtml(html).
//                setText(html);
//
//        try {
//            SendGrid.Response response = sendgrid.send(email);
//            System.out.println("Email response " + response.getMessage() + " - " + response.getCode() + " - " + response.getStatus());
//        } catch (SendGridException e) {
//            System.out.println(e);
//        }
//    }
//
//    private void showStats(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        LocalDate fromDate = new LocalDate(2014, 1, 1);
//        LocalDate toDate = LocalDate.now();
//        PortfolioRepository portfolioRepository = new PortfolioRepository();
//        StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate);
//        LocalDate lastTradingDay = stockMarket.getLastTradingDay();
//        PrintWriter writer = resp.getWriter();
//        new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates()).htmlStatsAndPortfolioOn(lastTradingDay, writer);
//    }
//
//    private void showHome(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//        PrintWriter writer = resp.getWriter();
//        try {
//            writer.print("<p>Checking DB status</p>");
//            writer.print("<p>Merval collections</p>");
//            writer.print("<ul>");
//            new HistoricPriceRepository().forEachStock(name -> writer.print("<li>" + name + "</li>"));
//            writer.print("</ul>");
//            writer.print("<p>Stats collections</p>");
//            PortfolioRepository portfolioRepository = new PortfolioRepository();
//            writer.print("<ul>");
//            portfolioRepository.forEachStatCollection(name -> writer.print("<li>" + name + "</li>"));
//            writer.print("</ul>");
//            writer.print("<p>Stock collections</p>");
//            writer.print("<ul>");
//            portfolioRepository.forEachStockCollection(name -> writer.print("<li>" + name + "</li>"));
//            writer.print("</ul>");
//
//            writer.print("<p>Market</p>");
//            writer.print("<ul>");
//            LocalDate fromDate = new LocalDate(2014, 1, 1);
//            LocalDate toDate = LocalDate.now();
//            Stream<StockPrices> stockPricesStream = new StockMarketBuilder().build(fromDate, toDate).getStockPrices().stream();
//            stockPricesStream.forEach(stockPrice -> writer.print("<li>" + stockPrice.getStockName() + " - " + stockPrice.getDailyQuotes().size() + "</li>"));
//            writer.print("</ul>");
//        } catch (Exception e) {
//            writer.print("Error");
//            e.printStackTrace(writer);
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        String portString = System.getenv("PORT");
//        int port = portString == null ? 5000 : Integer.valueOf(portString);
//        Server server = new Server(port);
//        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
//        server.setHandler(context);
//        context.addServlet(new ServletHolder(new Main()), "/*");
//        LifeCycle.Listener listener = new LifeCycle.Listener() {
//            @Override
//            public void lifeCycleStarting(LifeCycle lifeCycle) {
//
//            }
//
//            @Override
//            public void lifeCycleStarted(LifeCycle lifeCycle) {
//
//            }
//
//            @Override
//            public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {
//
//            }
//
//            @Override
//            public void lifeCycleStopping(LifeCycle lifeCycle) {
//                System.out.println("Stopping");
//            }
//
//            @Override
//            public void lifeCycleStopped(LifeCycle lifeCycle) {
//                System.out.println("Stopped");
//            }
//        };
//        context.addLifeCycleListener(listener);
//        server.start();
//        server.join();
//    }
}
