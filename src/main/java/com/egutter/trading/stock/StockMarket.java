package com.egutter.trading.stock;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

import java.util.List;


/**
 * Created by egutter on 2/12/14.
 */
public class StockMarket {

    private List<StockPrices> stockPrices;

    private StockPrices marketIndexPrices;

    private static String MERVAL_MARKET_INDEX = "%5EMERV";

    public StockMarket(List<StockPrices> stockPrices, StockPrices marketIndexPrices) {
        this.stockPrices = stockPrices;
        this.marketIndexPrices = marketIndexPrices;
    }

    public List<LocalDate> getTradingDays() {
        return marketIndexPrices.getTradingDates();
    }

    public List<StockPrices> getStockPrices() {
        return stockPrices;
    }
    public LocalDate getLastTradingDay() {
        List<LocalDate> tradingDays = Lists.transform(stockPrices, new Function<StockPrices, LocalDate>() {
            @Override
            public LocalDate apply(StockPrices stockPrices) {
                return stockPrices.getLastTradingDate();
            }
        });
        return Ordering.natural().max(tradingDays);
    }

    public StockPrices getMarketIndexPrices() {
        return marketIndexPrices;
    }

    public static String[] stockSymbols() {
        return new String[]{
                "ALUA.BA", // ALUAR ALUMINIO
                "APBR.BA", // PETROBRAS ORDINARIAS
                "BHIP.BA", // BANCO HIPOTECARIO
                "BMA.BA", // BANCO MACRO
                "CEPU2.BA", // CENTRAL PUERTO
                "COME.BA", // COMERCIAL DEL PLATA
                "CRES.BA", // CRESUD
                "EDN.BA", // EDENOR
                "ERAR.BA", // SIDERAR
                "FRAN.BA", // BCO.FRANCES S.A.
                "GGAL.BA", // GRUPO FINANCIERO GALICIA
                "INDU.BA", // SOLVAY INDUPA SAI
                "IRSA.BA", // IRSA
                "LEDE.BA", // LEDESMA
                "MIRG.BA", // MIRGOR
                "MOLI.BA", // MOLINOS
                "PAMP.BA", // FRIGORIFICO LA PAMPA
                "PESA.BA", // PETROBRAS ENERGIA
                "STD.BA", // BANCO SANTANDER
                "TECO2.BA", // TELECOM ARGENTINA
                "TEF.BA", // TELEFONICA
                "TGNO4.BA", // TRANSPORT GAS NORTE
                "TRAN.BA", // CIA.TRANSP.EN.ELECTRICIDAD
                "TS.BA", // TENARIS
                "YPFD.BA", // YPF
                MERVAL_MARKET_INDEX};
    }

}
