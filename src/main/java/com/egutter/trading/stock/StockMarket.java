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

    public static String MERVAL_MARKET_INDEX = "%5EMERV";

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

    public StockPrices getStockPricesFor(String stockName) {
        return stockPrices.stream().filter(stockPrices -> stockPrices.getStockName().equals(stockName)).findFirst().get();
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
                // MERVAL 25
                "ALUA.BA", // ALUAR ALUMINIO
                "APBR.BA", // PETROBRAS ORDINARIAS
                "BHIP.BA", // BANCO HIPOTECARIO
                "BMA.BA", // BANCO MACRO
                "BRIO.BA", // Banco Santander Rio S.A.
                "CECO2.BA", // Endesa Costanera SA
                "CELU.BA", // Celulosa Argentina S.A.
                "CEPU2.BA", // CENTRAL PUERTO
                "COME.BA", // COMERCIAL DEL PLATA
                "EDN.BA", // EDENOR
                "ERAR.BA", // SIDERAR
                "FRAN.BA", // BCO.FRANCES S.A.
                "GGAL.BA", // GRUPO FINANCIERO GALICIA
                "INDU.BA", // SOLVAY INDUPA SAI
                "MIRG.BA", // Mirgor S.A.C.I.F.I.A.
                "MOLI.BA", // MOLINOS
                "PAMP.BA", // FRIGORIFICO LA PAMPA
                "PESA.BA", // PETROBRAS ENERGIA
                "PETR.BA", // Petrolera Pampa S.A.
                "TECO2.BA", // TELECOM ARGENTINA
                "TGNO4.BA", // TRANSPORT GAS NORTE
                "TGSU2.BA", // Transportadora de Gas Del Sur S.A.
                "TRAN.BA", // CIA.TRANSP.EN.ELECTRICIDAD
                "TS.BA", // TENARIS
                "YPFD.BA", // YPF
                // OTRAS
//                "CRES.BA", // CRESUD
//                "IRSA.BA", // IRSA
//                "LEDE.BA", // LEDESMA
//                "STD.BA", // BANCO SANTANDER
//                "TEF.BA", // TELEFONICA
                MERVAL_MARKET_INDEX};
    }

}
