package com.egutter.trading.stock;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


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

    public static String[] allStockSymbols() {
        return Stream.concat(Arrays.stream(merval25StockSymbols()), Arrays.stream(altStockSymbols()))
                .toArray(String[]::new);
    }

    public static String[] merval25StockSymbols() {
        return new String[]{
                // MERVAL 25
                "ALUA.BA", // ALUAR ALUMINIO
                "APBR.BA", // PETROBRAS ORDINARIAS
                "BHIP.BA", // BANCO HIPOTECARIO
                "BMA.BA", // BANCO MACRO
                "BPAT.BA", // Banco Patagonia
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
                "TEF.BA",   // Telefonica
                "TGNO4.BA", // TRANSPORT GAS NORTE
                "TGSU2.BA", // Transportadora de Gas Del Sur S.A.
                "TRAN.BA", // CIA.TRANSP.EN.ELECTRICIDAD
                "TS.BA", // TENARIS
                "YPFD.BA", // YPF
                MERVAL_MARKET_INDEX
        };
    }

    public static String[] altStockSymbols() {
        return new String[]{
                // OTRAS
                "AGRO.BA",
                "APSA.BA",
                "AUSO.BA",
                "BOLT.BA",
                "CADO.BA",
                "CAPU.BA",
                "CAPX.BA",
                "CARC.BA",
                "CGPA2.BA",
                "COLO.BA",
                "CRES.BA", // CRESUD
                "CTIO.BA",
                "DYCA.BA",
                "ESME.BA",
                "ESTR.BA",
                "FERR.BA",
                "FIPL.BA",
                "GARO.BA",
                "GBAN.BA",
                "GCLA.BA",
                "GRIM.BA",
                "INTR.BA",
                "INVJ.BA",
                "IRSA.BA",
                "JMIN.BA",
                "LEDE.BA",
                "LONG.BA",
                "METR.BA",
                "MORI.BA",
                "OEST.BA",
                "PATA.BA",
                "PATY.BA",
                "POLL.BA",
                "PSUR.BA",
                "REP.BA",
                "REP.BA",
                "RIGO.BA",
                "ROSE.BA",
                "SAMI.BA",
                "SEMI.BA",
                "STD.BA",
                "TGLT.BA",
        };
    }

}
