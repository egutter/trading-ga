package com.egutter.trading;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.egutter.trading.out.StatsPrinter;
import com.egutter.trading.repository.HistoricPriceRepository;
import com.egutter.trading.repository.PortfolioRepository;
import com.egutter.trading.runner.TradeOneDayRunner;
import com.egutter.trading.stock.StockMarket;
import com.egutter.trading.stock.StockMarketBuilder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;
import org.eclipse.jetty.util.component.LifeCycle;
import org.joda.time.LocalDate;

import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;

public class Main extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    if (req.getRequestURI().endsWith("/stats")) {
      showStats(req, resp);
    } else {
      showHome(req,resp);
    }
  }

  private void showStats(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    LocalDate fromDate = new LocalDate(2014, 1, 1);
    LocalDate toDate = LocalDate.now();
    PortfolioRepository portfolioRepository = new PortfolioRepository();
    StockMarket stockMarket = new StockMarketBuilder().build(fromDate, toDate, false, false);
    LocalDate lastTradingDay = stockMarket.getLastTradingDay();
    PrintWriter writer = resp.getWriter();
    new StatsPrinter(portfolioRepository, stockMarket, new TradeOneDayRunner(fromDate, toDate).candidates()).htmlStatsAndPortfolioOn(lastTradingDay, writer);
  }

  private void showHome(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter writer = resp.getWriter();
    try {
      writer.print("<p>Checking DB status</p>");
      writer.print("<p>Merval collections</p>");
      writer.print("<ul>");
      new HistoricPriceRepository().forEachStock(name -> writer.print("<li>" + name + "</li>"));
      writer.print("</ul>");
      writer.print("<p>Stats collections</p>");
      PortfolioRepository portfolioRepository = new PortfolioRepository();
      writer.print("<ul>");
      portfolioRepository.forEachStatCollection(name -> writer.print("<li>" + name + "</li>"));
      writer.print("</ul>");
      writer.print("<p>Stock collections</p>");
      writer.print("<ul>");
      portfolioRepository.forEachStockCollection(name -> writer.print("<li>" + name + "</li>"));
      writer.print("</ul>");
    } catch (Exception e) {
      writer.print("Error");
      e.printStackTrace(writer);
    }
  }

  public static void main(String[] args) throws Exception {
    String portString = System.getenv("PORT");
    int port = portString == null ? 5000 : Integer.valueOf(portString);
    Server server = new Server(port);
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    server.setHandler(context);
    context.addServlet(new ServletHolder(new Main()), "/*");
    LifeCycle.Listener listener = new LifeCycle.Listener() {
      @Override
      public void lifeCycleStarting(LifeCycle lifeCycle) {

      }

      @Override
      public void lifeCycleStarted(LifeCycle lifeCycle) {

      }

      @Override
      public void lifeCycleFailure(LifeCycle lifeCycle, Throwable throwable) {

      }

      @Override
      public void lifeCycleStopping(LifeCycle lifeCycle) {
        System.out.println("Stopping");
      }

      @Override
      public void lifeCycleStopped(LifeCycle lifeCycle) {
        System.out.println("Stopped");
      }
    };
    context.addLifeCycleListener(listener);
    server.start();
    server.join();
  }
}
