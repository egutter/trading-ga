package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.out.adapters.BitStringGsonAdapter;
import com.egutter.trading.out.adapters.ClassTypeAdapterFactory;
import com.egutter.trading.out.adapters.JodaLocalDateGsonAdapter;
import com.egutter.trading.stock.StockGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.joda.time.LocalDate;
import org.uncommons.maths.binary.BitString;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.egutter.trading.stock.StockMarket.*;
import static java.util.Arrays.asList;

public class UsaStockMarketCandidates {

    public static void main(String[] args) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder().setPrettyPrinting();
        gsonBuilder.registerTypeAdapterFactory(new ClassTypeAdapterFactory());
        gsonBuilder.registerTypeAdapter(BitString.class, new BitStringGsonAdapter());
        gsonBuilder.registerTypeAdapter(LocalDate.class, new JodaLocalDateGsonAdapter());
        Gson gson = gsonBuilder.create();

        Reader reader = Files.newBufferedReader(Paths.get("2020-12-06_all_candidates.json"));
//        String jsonString = gson.toJson(etfSectorCandidates());
//        System.out.println(jsonString);

        Type listType = new TypeToken<ArrayList<Candidate>>(){}.getType();
        List<Candidate> list = gson.fromJson(reader, listType);
        System.out.println(list);
    }
    public static List<Candidate> allCandidates() {
        return Stream.of(etfSectorCandidates(),
                techCandidates(),
                commCandidates(),
                financeCandidates(),
                greenBondCandidates(),
                consDiscCandidates(),
                consBasicCandidates(),
                developAdrCandidates(),
                emergentAdrCandidates(),
                healthCandidates(),
                biotechCandidates(),
                aaplCandidates(),
                amazonCandidates(),
                microsoftCandidates(),
                googleCandidates(),
                netflixCandidates(),
                teslaCandidates(),
                salesforceCandidates(),
                paypalCandidates(),
                intelCandidates(),
                nvidiaCandidates(),
                electronicArtsCandidates(),
                squareCandidates(),
                cloudfareCandidates(),
                splunkCandidates(),
                ciscoCandidates(),
                industrialCandidates(),
                innovationCandidates(),
                longtermBondsCandidates(),
                corporateBondsCandidates(),
                metalsCandidates(),
                metalsCandidates(),
                emergentCandidates(),
                greenCandidates(),
                smallCapCandidates(),
                developEtfCandidates())
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
    }

    public static List<Candidate> industrialCandidates() {
        return asList(
                new Candidate("INDUSTRIAL_SECTOR", "111010001101100010010000010101100111010",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "000110100101100011010111101101000010101",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(2/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111010001110100011011100000100100111010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(8/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "101010000110001110101100010101100100000",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.96(25/1)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "101011001100100011010000110101100111010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(2/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "101011000101011110110010010101000011010",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(2/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "000011110101100010010111110011110110001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.94(16/1)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111001111001011000111011000010000010001",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110110101101010101110000010001100110000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111001111001011000111011000010000001011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111001101001111010110110000010000010011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111001111101011000111011000011010011011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111001111101011000111010010010000000011",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "011011110101010010001000100101000100001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.93(14/1)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "000010001110100001010001010101100110000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110100001010111001101001000011100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100010111100110000010001110101100101010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "001011100101011110100010010101000010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110111111111101010110111101100100111011",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110001011110111110110100100101101101111",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.92(44/4)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100110010100101001011100000011001111110",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.95(37/2)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110001001011101010111110010011100111110",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(8/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110000011000001001110111100011100001111",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.91(10/1)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100001100010001000110010110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100001100010001000110010110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100001100010001000110010110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "100001100010001000110110110011000001100",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(4/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110010001101101010100000110101000111000",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.94(30/2)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111010001110100010010000010101100111011",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(2/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "110010001110100000000000010101100011011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(8/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111101011110000110110001010101000010100",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(6/0)", industrialSector())
                        )),
                new Candidate("INDUSTRIAL_SECTOR", "111010001110100010010001010101100111011",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00(2/0)", industrialSector())
                        ))
        );
    }

    public static List<Candidate> innovationCandidates() {
        return asList(
                new Candidate("INNOVATION_SECTOR", "110011000111000111100110111010100100000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "000111100101100011010011101101000010111",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.91(10/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111000001110101010010000010100100011110",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(14/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "011110000000101001101001000011100100010",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111010001110110000010010110101110111010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "101100010111110100010000000100100001000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.91(20/2)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001101110110100011000011100000001111011",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.91(21/2)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "011010001001011000010011100000000010010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.92(11/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "011110110100011011010001111101111011111",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(13/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111111001000110100000101101101000010011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(6/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111001100110000111010000110011001111011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.91(10/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111001011001010000111010010010000000011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110011100100110010000111000011000010011",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(6/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "101011010001010011000110110011000010101",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(13/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001111110010111010101001000011000010000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(14/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "011110000000011010000011011011111110010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.94(15/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001011110010111010111011010011100110111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(14/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001011110010010010100110000000011110001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(14/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "100101001010110010010011110100100110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001011110010111010101001000011000110000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.93(14/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "000011110001010010010000010101100110010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.92(12/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001100100111110101111000010100011111101",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(2/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111100001000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.94(15/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111000001000001001101001000011100110010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.94(15/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111100001000101001101011000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.94(15/1)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111000101100011010101101100111110110110",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "0.91(32/3)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111010010100001000110000010000100000010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(2/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110000010111001000110000110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(6/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111010001010100010010000010101100111010",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110010010100001000110000010000100001000",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(2/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110010010100001000110000010000001101000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(2/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111010001110100010010000010101100111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110011001011100010010010100101010111010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111010001110100010010000010101100111010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "110111001111100010010010010101000101010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "001010101111000010010010010101101110010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(8/0)", innovationSector())
                        )),
                new Candidate("INNOVATION_SECTOR", "111100101000101011101000000011000100010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00(4/0)", innovationSector())
                        ))
        );
    }

    public static List<Candidate> longtermBondsCandidates() {
        return asList(
                new Candidate("VGLT", "010001001111010001011000000001100111000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(6/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "000101010001010001011010110010100001100",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.91(10/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "101010001110110011010100010100100111010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "011011100000011101111101101010010111101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.93(14/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "101010001011100000010000010101100011010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "111111111010110010010010010011000111010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101100100001110001010011101010011111001",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "011101001110100011100100000111010011101",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.93(13/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "001100001101010110001011011110100110010",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "100001101000100100111011010010100001000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101001101000000100101011010000100011000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "100101000010110010010011110100100100010",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "100101011000100110101011010010100100010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "011110101101100011010111101101100010111",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(6/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "001110011000011000100100111111100011100",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "011000011011100100000111010000100100000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(10/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010001101111010001011000100001100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010001001111010001011000000001100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010001001111010001011000000011100111100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010001001110110001011000000001100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010001001111010001011010000001101111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "011001101001010001011000000001100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010000001110100100111000010111100110010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.92(12/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "100110100110100010010100010101001111010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(8/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010110000010101001001101100011100101011",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "010110001000001001100001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(6/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101001001101000010011000010101000101010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.91(10/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "101110110110100000011010000101001110010",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(8/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "001001111100100010000000000100100101001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.94(16/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "101010101000100010110001010100110110011",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(8/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101001001100101011011101100101101111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(8/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "100101001000100010101001001001111110101",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(2/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "001010001010000010010000000101000110000",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(14/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101001000010111100100110100100101001011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.91(10/1)", longTermBonds())
                        )),
                new Candidate("VGLT", "001010011010100100010100000100110100000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.91(21/2)", longTermBonds())
                        )),
                new Candidate("VGLT", "111000001110101010000010110101001011100",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(4/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "100001000111101100110000110000110001000",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VGLT", "1.00(6/0)", longTermBonds())
                        )),
                new Candidate("VGLT", "101001000111001010111010110000111000001",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VGLT", "0.92(12/1)", longTermBonds())
                        ))
        );
    }

    public static List<Candidate> corporateBondsCandidates() {
        return asList(
                new Candidate("VCLT", "110010010100111001101000110000100011001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101100010010100010111101000011100010100",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011001000101010110111000001100101010",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "111010000110100010010000011100000011010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "011001010110111010010110010100100011010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.92(12/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "111100100000101111110001000100001101101",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.94(15/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011001011100100000111100000000001101",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101000001001000001010011100010000011111",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(8/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "100000000100100001010111100000000001100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(8/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "011000101000100110111011010001100001101",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "011100011000100110111011100000000100111",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(8/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101000001000100010010100000000010111000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(8/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "010101001000100110111010010001100001001",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101101011001001000110010110000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101101001000001000110000110000100001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101101011000001000110000110000100001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110111000111000111100110110000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.91(10/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011000111000111110000110000100001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.91(10/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "101101011000001000110000110000100001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101101011000001000110000110000100001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "010100100100100001101010000011100000100",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110000001100000010010100010101010101010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.91(10/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "111000001110100110110110010111000111001",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(10/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011000111000111100110111010100100000",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011101101100100011111001010100110000",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "111101111110111100000000000110011001010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "101000010110100100010000100100100101000",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.91(10/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "111010011110100001000000000111100000010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "011011011110100011001000010101100111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(8/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "010011010110000111100110010100111110001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "111000001110001010101100100101000110000",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "0.91(10/1)", corporateBonds())
                        )),
                new Candidate("VCLT", "010111001110100011110000010101100101010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "000010110001110011001000101100100101001",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(6/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "000101100001010011001000101101000101100",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011001001001010111101100001100110000",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "110011001000101010111101100001100101010",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(4/0)", corporateBonds())
                        )),
                new Candidate("VCLT", "011110000000101001101001000010100100010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("VCLT", "1.00(2/0)", corporateBonds())
                        ))
        );
    }

    public static List<Candidate> metalsCandidates() {
        return asList(
                new Candidate("METALS", "110011001100100100001100010000000101101",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "0.91(10/1)", metals())
                        )),
                new Candidate("METALS", "001000101011110000100101010100100101001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(12/0)", metals())
                        )),
                new Candidate("METALS", "001001110010111010101001000011100000100",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(4/0)", metals())
                        )),
                new Candidate("METALS", "111011100110100111001010000101110111000",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(4/0)", metals())
                        )),
                new Candidate("METALS", "010010101011100110011110010100100001010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "011100010100011000010100111110000000001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "110111101101100110001001010000000101000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "110011001100100100001000011110001000100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "110011001100100100001000010000100101101",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "110111101100100110001001010010110001101",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "001110001000000000111000000000111111000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "001110001000100001111000010000000101000",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(8/0)", metals())
                        )),
                new Candidate("METALS", "001111001000100110111011110000100111000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(4/0)", metals())
                        )),
                new Candidate("METALS", "111100110000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111100100000111001101000110000101010001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111101100000111001101010000000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111101100000111001101000110000100010001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111000100000111001100101100000110000100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111100100000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111100110000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "000010000011000000111101001000100101101",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(14/0)", metals())
                        )),
                new Candidate("METALS", "010111000110101001101000000001100100110",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(8/0)", metals())
                        )),
                new Candidate("METALS", "011111000100001001111011100011101000011",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "011110000000001001101001000011100110010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "011110000100101001101111000011100100110",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(4/0)", metals())
                        )),
                new Candidate("METALS", "011100000100101001101101010111100100010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(8/0)", metals())
                        )),
                new Candidate("METALS", "100001110010001000010000110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "100001100010001000110000110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "100101000010110010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "101101010010001000110010110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "101101000010001000110010110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111011101110001011110110010100001010011",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(6/0)", metals())
                        )),
                new Candidate("METALS", "011100001100101000010000100100101011010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(12/0)", metals())
                        )),
                new Candidate("METALS", "001011000110100010010100010101101110110",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(10/0)", metals())
                        )),
                new Candidate("METALS", "100101001010110010011011100100100100011",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(4/0)", metals())
                        )),
                new Candidate("METALS", "110011001000101010111101100001100101010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        )),
                new Candidate("METALS", "111011001000101010111101100001100101010",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00(2/0)", metals())
                        ))
        );
    }

    public static List<Candidate> emergentCandidates() {
        return asList(
                new Candidate("EMERGENT", "001100001011010001011000000001100011001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.94(15/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111110100011000001001011010100001011100",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "010001001101111010010111010100101010000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.91(10/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101010101110110010010000010100000111010",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111110001110010010011001010101100110010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(8/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111011101110001110011001010110100111000",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(6/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111101000010010111110010010101000010101",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(6/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100001010101011110110010010101000010000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(6/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111010011011100010010110000111011011011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.93(14/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111100110011100101010111100000000001010",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(6/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000001000100101010000110000101100010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.92(12/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101100011011000100011101100000000001000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.93(14/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101100010100101000101011000010110001011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.91(10/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000011011100101011101100000001001100",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(12/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000001011101001100111000011100100011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.92(12/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000011010000100011101010000000001000",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.94(16/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000011011100101011100110000000001100",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.92(12/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100110110010111001010100101100011011100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(6/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101010001111100000010111010101100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101011000101011110110010010101000010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "110110011111100010000000010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.92(12/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "011111101110100000010000010111100111111",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100001001101011110110011110101000010101",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111000001110101010000000010000100110010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101101010010001000110010110000100001001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100001111000001000100000110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101001110010001000110010110000100001000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "110001010010001000110000010000100000001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101001100010001000110000110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111010011111101010011011010100100101010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.92(12/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101011000101011110110010010101000010101",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "111010101110101011010001010101010111110",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.93(14/1)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "010001001111010001011000000001100111000",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "010001001011010001011000100001100010101",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "010101011001010001111010000001100111000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(2/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100000000111001100111011110001100111001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "100001000111001101110011000001100111000",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "1.00(4/0)", emergentMarkets())
                        )),
                new Candidate("EMERGENT", "101000001000101001101001000011111000010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.91(10/1)", emergentMarkets())
                        ))
        );
    }

    public static List<Candidate> greenCandidates() {
        return asList(
                new Candidate("GREEN ETF", "000110100101100011010011110000011110011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(4/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011110000000101011101001000011100100010",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.91(21/2)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100011010100100110000110100101101001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(4/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111100001010111001001001100011100011001",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.91(10/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "000110010000101110111111011110000111000",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.96(25/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111010000110100000010101110101100111011",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100000110110010010000100100100101001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "110101110001000110000011110010100101010",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "100001100000101001100101100011000101000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101001101010001000111011010000100001110",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(4/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101011100010001000100101000011000100111",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(8/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101001110000110101000010110000000010111",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "100011100010100111100101100010000110101",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(8/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "100001100010001011100011100010010001101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(8/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "100001100010010001100100110010100100000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "010000001111110001011000000001100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011000000111010001001000000000100111101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "010001001111011001011000000001100101001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(4/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "010000001111010001011010000001110111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011000001101010001011000000001101111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "010000001111011001011000000001100110000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011000001111010001011000000001100110000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(10/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011110010001001001001000000001100100010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "000010000000100010111011001001100000000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(16/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100010110100100000000100100100101001",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111110000100100011101000000111100100110",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.94(15/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "011100001000100001010000110101100101010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.93(13/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111101100000100010111011001000111111000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "110000100000001000111100100010000100111",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100010110111110110010000100100011001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111100001110000001010010100111100100001",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.91(20/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "000010000001000011010011001001111111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(16/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100010110100100010000100100100101001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "111010001010111111011101000101100011011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(6/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100010100100001000000100100110101001",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "100011010101010000010111000010001010000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(2/0)", greenSector())
                        )),
                new Candidate("GREEN ETF", "101100010110100100010000100100100101001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.92(11/1)", greenSector())
                        )),
                new Candidate("GREEN ETF", "010100101100110100001000010000000101101",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(GREEN_SECTORS, "1.00(2/0)", greenSector())
                        ))
        );
    }

    public static List<Candidate> smallCapCandidates() {
        return asList(
                new Candidate("SMALL CAP", "101100010110110010010000100100100101001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "010100101100110000001000010000000001001",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.93(14/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100101000010110010010011110100100100010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "110100101110100010010000011001000111000",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "010010000011100010010000000101100011010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000101011101010010110101110110011",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(30/3)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111110011110110010010000010101000110010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001010100101100010000100100100111000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111010011110111010010001010101101111000",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.92(11/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "110111001100100100001110010000001101101",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101011111100101100000100010000000101111",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "001011101110100010010000010000111100011",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "000011110100100010010000010000000110011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101111110100100011000011000000111110011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(10/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101011110100100011000011000010111010011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(10/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101010000111101000000100101111001100001",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(8/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "001010101110100010010000010100100111000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000111001101111001010000110011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000111000001010111010000100001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(10/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111110100000011001101010000000100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(8/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101000010011100101111111110000110001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.92(11/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000111001101111000110000110011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000110001101011111110000110001001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000111001101010010110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "011010001010101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "011110000000001000101000010111100100111",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(8/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "011110000011001001101000010011100000010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(4/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101110000110100100010000100100100101001",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(12/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111100011010000101110000010110001011011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.93(13/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "011010011110100010010000010101100110010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "100001000110100010000100110111000001110",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(20/2)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111010100010100010110000010101100101010",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.94(16/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "110100001010001010010000011110000110000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.92(12/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "101101010110100100010000100100100101000",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.91(10/1)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111111100011000011110010010111000011011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(8/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "001100001011010001011000000001100011111",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "001100001011010001011000000001100011111",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(2/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "010100101100110101011000110100000101011",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(6/0)", smallCap())
                        )),
                new Candidate("SMALL CAP", "111100101000000111110010010111000011111",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "1.00(10/0)", smallCap())
                        ))
        );
    }

    public static List<Candidate> greenBondCandidates() {
        return asList(
                new Candidate("BGRN ", "101100011110100001010000100001100101001",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101100011110100100011100101100100101001",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101000011110100011111000011111100111010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101100011100100010101011111100010011000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "100001001011000100100110010000111111001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101100010111100100010000010010110001001",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "001110100001100010111001110000111100001",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "001110001001000100110011110000111011001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "001110001000100110111011110000111111001",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101100010111100100010100110010100001001",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101100000100100100010011100100101101001",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "011000001110101010010010010001000111011",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "001110100011001001101001000011100100110",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101000010111100101111111100000000001000",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101000010011100101010111100000000001000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "000001011011100101010111100100001001000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "0.91", greenBonds())
                        )),
                new Candidate("BGRN ", "100001001110111100110001100100000100111",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101001001100100111000101000100010001010",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        )),
                new Candidate("BGRN ", "101001001010100010010001110100101011010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("BGRN", "1.00", greenBonds())
                        ))
        );
    }

    public static List<Candidate> developEtfCandidates() {
        return asList(
                new Candidate("DEVELOP ETF MARKETS", "000011110000010011001000101101000101100",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.92", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111011011110100000110100010000100110000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111100001000111001001001000011100011001",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.92", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "110100001110010111011011110101100101010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.91", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "001110001111010110110101011001100111010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111010000110101100010001010101100111110",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101011000111111100001000110101000111000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101000110000100010001011110010101011101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.91", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111000001000000110010110000100101111000",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "001011000010111001010011110100000110010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.94", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "011010000110100010110001010101100100000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101010001100100010010000010101100101110",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "100001110011001000111000110000100001010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101101010010001000110010110001100001001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101101010010001000110000110000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101100011011000111110010010111010001011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.90", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "100001100010001000110000110000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "100011000010001000110000110000100010100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "100001100010001000110010110000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "001011110010111011101001000011100110100",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111010001110100011010000010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "000010001000000010111011001000111111000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "110110011110100111010010010101101101011",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.90", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101011000101011110110010000101000010101",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111010001110100010010000010101100111010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "110010000010110001010000010001100111000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.92", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111110011110100000010000010101100111010",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101011000101011110110010010101010010101",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "110100000110100111111000010011010111000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.94", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "000010001000101010101011001000111111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111000001100001010011100000101101111000",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "100011000101011110110010010101000010101",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "111011111110111000110000010101101110010",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "000011000010110010001010100001000010111",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101011000101011110110010010101000010101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        )),
                new Candidate("DEVELOP ETF MARKETS", "101011010000101100110100000111100000111",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "1.00", developedMarkets())
                        ))
        );
    }

    public static List<Candidate> microsoftCandidates() {
        return asList(
                new Candidate("MSFT", "010100101100110100001000010000000101100",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111100101000101001101001001010101110000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "111100101000100000101001000011100100010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.94", microsoft())
                        )),
                new Candidate("MSFT", "110010010100001000110000010000100000000",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "111100101000101001101001000011100100001",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.93", microsoft())
                        )),
                new Candidate("MSFT", "110010000100001000110000010000100001000",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.93", microsoft())
                        )),
                new Candidate("MSFT", "110010010100001000110000010000100001000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "111100100000100010101010010011100101000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.90", microsoft())
                        )),
                new Candidate("MSFT", "110010000100001000110000010000100001000",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.93", microsoft())
                        )),
                new Candidate("MSFT", "010101101100110101000010010000000101101",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010000111110100100001000001101000101000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010100101100110100001000010000000101100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010100101010100100001000000000100001011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "000100111101111100001011010001110110011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010100101100110100001000010010000101100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "000100111100110101001000101101000101101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010100101000100100001000000001000100101",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "101011000101011110110010010101000010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111110010100000111011110100001101111110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "101011000101011110110010010101000010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "011101000110100110011001000101000100000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.94", microsoft())
                        )),
                new Candidate("MSFT", "101011000101011110110010010101000010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111110000110100000010000010101111111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111010000101101010010000110001100101010",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "101010100101011110110010010101000001111",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.90", microsoft())
                        )),
                new Candidate("MSFT", "011010000100001001100001000011100101110",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "011110000100001001101111000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010110001100101001000001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "011100101100101001100011000011100100011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111000000101111100110110010101000010001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.93", microsoft())
                        )),
                new Candidate("MSFT", "001000001110100010001001001010011110011",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "101011000010110010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "110010001010000001010000010101111011010",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "011100000101011110110010010101000010101",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "111100101000101001101001010001100100010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "111100101000101001101001010011010110110",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "110000101000111011100010000011001001001",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.90", microsoft())
                        )),
                new Candidate("MSFT", "111100101000111001111000000011001011011",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft())
                        )),
                new Candidate("MSFT", "111100101001111101101000000011100011110",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.90", microsoft())
                        )),
                new Candidate("MSFT", "100100111100110100001000010000000101101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "010100101100110100000010010000000101101",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("MSFT", "110010001010100001010001010100100010010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("MSFT", "0.91", microsoft())
                        ))
        );
    }

    public static List<Candidate> googleCandidates() {
        return asList(
                new Candidate("GOOGL", "110011001000101010111101100001100101010",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "001011110100100011010011100000111110011",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "011100001110010000010001010100101101010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "111001010001100100011011001001101111001",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "101010000101011110110010010101000000100",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "111011000011100011100110110011000000001",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "100110100111100100101111110100000111011",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.93", google())
                        )),
                new Candidate("GOOGL", "010000101101010000010001010100111110111",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "101101101010001011110100010100000111011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "110011001100100100001100000000000111100",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001100100100001111100001000001101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001100100000001110010000000001100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001100100100010000100000100101000",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "111011001100100001001110110000000101000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001100100000001111100000000101100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "101101011100100100001100000000001101100",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001100100100010000100100001111001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110010010100001000110001010000100101000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110010010100001000110000010000101001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110010010100001000100100010000100001010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110010000100101000110001010000100001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "100010010100000001100000010000100001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.93", google())
                        )),
                new Candidate("GOOGL", "110010010100001000110000010000100001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "011100011010010111110010010111000011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "011100001110101010011000000001110001010",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "101010010110100110010000010101000111010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "110011001110000100011111001010101110000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "111000001110100100000000010100100111010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "000100010001100110111001110100111100000",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.90", google())
                        )),
                new Candidate("GOOGL", "110101100010000110110010000101010011011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "011001001100100010011101010101000111010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.91", google())
                        )),
                new Candidate("GOOGL", "110011001010000111000110011100100100000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "011100001111010111000001110101110111010",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "011010001010100010010010000100100111010",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "100100010110000100011000100100100100001",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "110011001110000100011111001010101110000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "001001000011001010010010011110100111001",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "101010100111101010111111000010000111001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.93", google())
                        )),
                new Candidate("GOOGL", "111101100010000100110010010101011011011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "011010100000110101100101100010000111101",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "100110100111101100111111110100000111010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "101110100111100100111111110110001111101",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.93", google())
                        )),
                new Candidate("GOOGL", "101010001110000010010000010100100111010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("GOOGL", "111101100011000010110010010101000011011",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "0.92", google())
                        )),
                new Candidate("GOOGL", "011101101010000111110010010111000011001",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        ))
        );
    }

    public static List<Candidate> netflixCandidates() {
        return asList(
                new Candidate("NFLX", "011010100000110101100101100010000111101",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "001110001000100101010111100000000001000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.90", netflix())
                        )),
                new Candidate("NFLX", "100100000100111010001111000011100110001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.93", netflix())
                        )),
                new Candidate("NFLX", "110000101010000101110011010000110110111",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "111010011100101001011010010101000111010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111100100110010000111001000010100011001",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "110011000111000111100110111010100100000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110000100110001001101111110001100011110",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.93", netflix())
                        )),
                new Candidate("NFLX", "111101100010001001101001000011100001000",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "110011000111000111100110101010100110000",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110000011100101001101000100001100100010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110010000100001011100000111010100100000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "000011110001010011001001000011100001100",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111001001100101001101001000011100010000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "000011110001010011001000110001100101010",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110000010100101000101001000000100101010",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110010000000001000110000000001100011000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "110101100110100010110000011101100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "101100100110110111111010100100101111111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "101000001111111010011001110101000111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "001000111000010101010000110101011000011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "101010001010100010110000011101100111111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111100011100111000010000110101101111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "111100101000111001101001000011100011011",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "001000111001101001100001100101000111010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.91", netflix())
                        )),
                new Candidate("NFLX", "111011000111000010010000010100101110000",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "000011111011001011000110000000000100001",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111000100111100000011000010100101101010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "101101110000000010111011110100100011110",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "101000010110000110111101110000100001001",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.91", netflix())
                        )),
                new Candidate("NFLX", "101001000010001000110110000011110001001",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "100000110010000110111011110000110000001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "101101001110100010010010010100100100001",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.95", netflix())
                        )),
                new Candidate("NFLX", "111100101000101001101001000011100101010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.92", netflix())
                        )),
                new Candidate("NFLX", "110111001011000011100110110101000111000",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111001100001011000101010100000000111110",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111101001100100010010000010101100111010",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "111111001110000010010000010100100101000",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "101010001011101110110111001101100111010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111001001100100000010011010101100111011",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "111010011110101010000000000000110100001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("NFLX", "111001001101100011110010010100100111110",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.94", netflix())
                        )),
                new Candidate("NFLX", "101000001000100010010000010101100011010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NFLX", "0.91", netflix())
                        ))
        );
    }

    public static List<Candidate> teslaCandidates() {
        return asList(
                new Candidate("TSLA", "110010010100001000110000110000100000110",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000101001100110100001000010000000101011",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "001000001111100011010000110101101111011",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "100010000001010100010010110101010000101",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "011100100001011101111010111101100001101",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.91", tesla())
                        )),
                new Candidate("TSLA", "111110100101101100010101011100111111111",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.91", tesla())
                        )),
                new Candidate("TSLA", "010011001010101001010000010100100011001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "110001011110000111110010010101010011000",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "110011001100100100001100010000000101101",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "001100001001010101001000000001100101100",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "110011001100100000001100010000000101101",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000000001011000000001101000010001011101",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "001100001011000000001110010001100101100",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "110011001100100100001100000001000101100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "001000001111111001001000110000100111111",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "110111001100100100001100010010000101101",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "011001101100111001111100110011111110110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "101010001110100000010000010101001111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "010000110011010110101000101011011001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "111110100111100000011000000000100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.91", tesla())
                        )),
                new Candidate("TSLA", "011101110010000111111011010101001011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.90", tesla())
                        )),
                new Candidate("TSLA", "000000110010001011010110010010101100111",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "101011001110100010010000010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000010001000100010111011101000111111000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "110010010110001000110000010000100001100",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "001110100101000011010111101101000010111",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "100010000101111110110010010101001000000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "110010010100001000110000010000100001000",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "100100000010110010010011110100101000010",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.93", tesla())
                        )),
                new Candidate("TSLA", "100000001110001010010010100000011010000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.92", tesla())
                        )),
                new Candidate("TSLA", "100011001111011110100010010101000011010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000010001000100010111111001000111111010",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "101110001110100011010001010100100111000",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "101110100000011101010001010100101101011",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "0.90", tesla())
                        )),
                new Candidate("TSLA", "111100101001101001000001000011100100010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000010001000100010111001101010111111100",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("TSLA", "000010001000100110001011001000101101000",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        ))
        );
    }

    public static List<Candidate> salesforceCandidates() {
        return asList(
                new Candidate("CRM", "101101010010001000110000110000100001100",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011111000000111001101000010011100110010",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.94", salesforce())
                        )),
                new Candidate("CRM", "000101000110110100010101110100100110011",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.93", salesforce())
                        )),
                new Candidate("CRM", "011110000000101001111100101111010010001",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.92", salesforce())
                        )),
                new Candidate("CRM", "101100111011011010001101001110010011001",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.93", salesforce())
                        )),
                new Candidate("CRM", "101011000101011110110011110101100010101",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011010011110110101111101110100010100110",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.94", salesforce())
                        )),
                new Candidate("CRM", "100001100010110010010011110000100100011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.91", salesforce())
                        )),
                new Candidate("CRM", "100000101000100110111011010000100001000",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011001101010001000010000110000100101011",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100000101000100100110000010000111000111",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100000111000100000110000000011101111010",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "101111001111101100111000000000000011001",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100001101001101000000100001110100000100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100000101000000001011011010000100001001",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100110010000100100000001011111110011000",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011110000000100010010011110000111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011100001000100010010011110000111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100101000000100010010011110000110101011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011110000000100010010011110001111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "001101001000100010010011110000110111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011100001000100010010011110010111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "111100101110010001001001000011100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "000010000100100010111011001000111011000",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "101010011101100010001000010101100111110",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100010001011011100111001110111111000101",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011010001010101010010000010001101111010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011010011111100010101001010101000110011",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "100001100010100010010010010100101001000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.93", salesforce())
                        )),
                new Candidate("CRM", "000100111011010001011000000001100011111",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "001100001011010001011000000001100011111",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "001000011011110001111000000001110011111",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "001100111011010001010000000001100011111",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "110011001110000100011111001010101110000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011010101111100110101000110101000111011",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "111101110110110010111000111010100000010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011011001110110011000001111101101011000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "000011110001010011001000101110000101100",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("CRM", "011111000000101001101001000011100100010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.92", salesforce())
                        )),
                new Candidate("CRM", "010110010000101001101001000011100100010",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "0.92", salesforce())
                        )),
                new Candidate("CRM", "101010010001111111111110111000111001100",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        ))
        );
    }

    public static List<Candidate> paypalCandidates() {
        return asList(
                new Candidate("PYPL", "101000010011100101010111100000000001000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "001000011010101101011111100000000001000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "000101100000100010010111110001101010111",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.92", paypal())
                        )),
                new Candidate("PYPL", "000110000101100011010011111101110001111",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "000110100101100010010111101110000010111",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.90", paypal())
                        )),
                new Candidate("PYPL", "000011100110001000110010110000100000000",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.93", paypal())
                        )),
                new Candidate("PYPL", "000110000101000011010111110010000011100",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "000110100101100010110010110000110000000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.90", paypal())
                        )),
                new Candidate("PYPL", "000110000101100010110011110000100000011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001000111111011010010001111111011101110",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "011100001100100010011000010001101110111",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "111010101010101010001001010001111111110",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "011110011110100010000100010001000111000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "001101011010100110111011110000110000011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001110001000100000110010000000000110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001101011000100000110010000000111100001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001100011010000110111011010101110000001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.92", paypal())
                        )),
                new Candidate("PYPL", "001110001010100110111011110000111110001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001110001000100110110010110000000000001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "001101011010000000110010110000000111001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "101100010000111011111001100010110100010",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "001100100110001001101000000010100000010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "001100010010101001101111000010100100010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.93", paypal())
                        )),
                new Candidate("PYPL", "001010000001101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "001001000000101001101001000001100000010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.94", paypal())
                        )),
                new Candidate("PYPL", "001101000000001001101001000011100100111",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.94", paypal())
                        )),
                new Candidate("PYPL", "010111110111011110101110000101001001011",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.91", paypal())
                        )),
                new Candidate("PYPL", "111010111110100010000001110001100000010",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "101100010010100101010000100000100110001",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "011010100100011010001011001011111100111",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "101100000010100100010000100100100100100",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "110000001011101010010110000101100111010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "011000001010100011111000010101100100000",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "111100001010100000010000010100110111000",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "111000100111110111010000011100100101010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "111100001110100010000000110100101111000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "100001010101110110101010010000111001011",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "100001010101110110101010010000111001011",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("PYPL", "101110001110100010010000010101100101010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("PYPL", "0.00", paypal())
                        ))
        );
    }

    public static List<Candidate> intelCandidates() {
        return asList(
                new Candidate("INTC", "101100010110100100010000100100100101001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "111100100000111001101001110000010000101",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.90", intel())
                        )),
                new Candidate("INTC", "100010100101111100011111110100000111010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "010000000101110100001101001011001111100",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.93", intel())
                        )),
                new Candidate("INTC", "100110100101011110110010110100000110110",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.92", intel())
                        )),
                new Candidate("INTC", "100100000010000011011011110000110001001",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.92", intel())
                        )),
                new Candidate("INTC", "101100100111100100111011110000000111010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.91", intel())
                        )),
                new Candidate("INTC", "100110101110011100011111110100000111010",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "110100100111011110110100110100000011011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.93", intel())
                        )),
                new Candidate("INTC", "000010100001010101000001111010011001001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100001001011101100011010110001100001111",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100011001010101000110000000000100001100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100101100110000000111011110010100001001",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101011111011100001111011110000100001001",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100101110111001000111000110000100011010",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101110001110001100011010110000101000101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100001100010001000110010110000000101001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100010111111000000010000000101000110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "110011000111000111100110111010100100000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100100100100010111110111010110001011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "001011110010111011101001000011100110100",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100110101010111010010000010101001001110",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "011000101111100110010110010101101011001",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "011100010011111010010000101011111111000",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.93", intel())
                        )),
                new Candidate("INTC", "101011010101011110010000010100001010111",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.90", intel())
                        )),
                new Candidate("INTC", "101100010110100100010000100100100101001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.93", intel())
                        )),
                new Candidate("INTC", "100001000110110010010011010100100100000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101100010110100100010000100100100101001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101111010111011110110110010101000011000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.90", intel())
                        )),
                new Candidate("INTC", "100111011000101010100000100001111011010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101000110110001010010000001101100110110",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.95", intel())
                        )),
                new Candidate("INTC", "100010100111001010010010010010100101000",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "101011010101000110110010010101000010001",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.95", intel())
                        )),
                new Candidate("INTC", "011010100000110111110100110010000101101",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.92", intel())
                        )),
                new Candidate("INTC", "011010100010010111100100100010000111101",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.92", intel())
                        )),
                new Candidate("INTC", "101011010101011110110000010011000010101",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.90", intel())
                        )),
                new Candidate("INTC", "001100010100001110100100000011110100000",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("INTC", "100111010111011101111011010100000011111",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC", "0.90", intel())
                        )),
                new Candidate("INTC", "011100010100011000001100111110011110001",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("INTC (20/2)", "0.91", intel())
                        ))
        );
    }

    public static List<Candidate> nvidiaCandidates() {
        return asList(
                new Candidate("NVDA", "101011000101011110110010010101000010101",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "010100101100110101010111100000000001000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.91", nvidia())
                        )),
                new Candidate("NVDA", "110011000000101001111101100001100101010",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "101011000101011100100010010101000010101",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "110100000110001100100010001110111101111",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.91", nvidia())
                        )),
                new Candidate("NVDA", "100000101010100100110111101111000100011",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.91", nvidia())
                        )),
                new Candidate("NVDA", "001100011110100100010000100100110001011",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "100101000000110110010011110100100100111",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.90", nvidia())
                        )),
                new Candidate("NVDA", "010100101100010100001000000000000000001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "010001001111011000001000010000010001100",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "011100001100110100001000010000000101100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "011100001100110100001000010010011000100",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "010001001100110100001000010000000100110",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "011100001100110100001000010000000101100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "010001001100110111001001000011001010100",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "010001001101110100001000010100100101101",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "111011001110100010010000011101110111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "111100101000111001101001000011100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "011000010101100111001000110101100000011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "111011000110100010011000000101100101010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "001000100111110010101011001111000010001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "110010011110000010011000111111110100011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "101011000001010110100010110101100010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.92", nvidia())
                        )),
                new Candidate("NVDA", "101101000111100010010011000101000110010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.92", nvidia())
                        )),
                new Candidate("NVDA", "101011000101011110110011000101010110101",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "101010001110101000011100010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "101101000010100111100010010110000011011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "001101011011000000000001010101111111111",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.93", nvidia())
                        )),
                new Candidate("NVDA", "111010001111100010010001110111000010011",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "100101000010110010010011100100101011101",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "110010001110100011100010010101100011010",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "111010011100101010000000010101010111010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "100011000101011110110010010101000010101",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "100110001110000111000000010101001011001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "111101100010000111110000000111000011011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "001100001011010001011101000000100011111",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "000100011011010001011001000001100110111",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "100101000010111110011001110100100110000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.94", nvidia())
                        )),
                new Candidate("NVDA", "011010100000110101100101100010000111101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("NVDA", "011010100010000111110010010110000011111",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "0.92", nvidia())
                        )),
                new Candidate("NVDA", "111101101000101001101001000001110101010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        ))
        );
    }

    public static List<Candidate> electronicArtsCandidates() {
        return asList(
                new Candidate("EA", "100001101010000110111011010000100001000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "100000111000001010111011010000100001010",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "110011001000101010111101100001100101010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101001111101011100110111101111100111101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "110010001010101010111100110000100101110",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "101000101001011000110010100000010010010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "100010100111101010111101110100000111000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111000001000111010111101100001100101010",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "111010001110100010010000010101000111010",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "110011001110000100011111001010101110000",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "001101001000100011000011110000001110010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "001101011100100110111011110000111111001",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100011000100010010011110100111111001",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100001010100001010001110000111111011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "001101001000000011000011110000011111111",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100001100100010010011110000011101101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100011000100000010110010000011011011",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111100000000111001101001000011100001001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "110011000111000111100110111010100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101011000101011110110010010101000010011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111100100000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111100000000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111100000101111001111000000011100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101011000101011110110010010101000010011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101001000101011111100110010101010010101",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "111010101010100010010000010101000111011",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100010110100100010000100100100101001",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100010110111110010001100100100000001",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111010000110100111010000010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "101101000101011010110010110101000010101",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "111011001101110010000000010001100111011",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111010001110100010010000010101100111010",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "101100001110000100011000100100101101001",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101100010110100101110000100100100101001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111010001110100010010000010101100111010",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "101100010110100100010000100100100111001",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "101101010110100100000000100100100101001",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "110010000111111111111010000101100010101",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "101100000110100100010000100100100101001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("EA", "111010001110100010010000010101100111011",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.91", electronicArts())
                        )),
                new Candidate("EA", "111010000110100010011000010100100010010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EA", "0.92", electronicArts())
                        ))
        );
    }

    public static List<Candidate> squareCandidates() {
        return asList(
                new Candidate("SQ", "001101011010000000110010000000000000001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.92", square())
                        )),
                new Candidate("SQ", "101001101010001000101010110000000111000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "100001010101110110101010010000000001001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "100001010100010110101010010000111001100",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "100001010101110110101010010000011001000",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "000011100010110000001000110000000000111",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "000110100101100100001111101101000010111",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.93", square())
                        )),
                new Candidate("SQ", "100001010101110110101010010000100000001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "100001010101110110101010010000111001001",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "001101010100100011010011110000011110011",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001101010100100011010011110000011110011",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "101101010110100011000100100000111110011",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001101010100100011010011110000011110011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001101010100100011010011110100010110011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001101010100100001000010110010011110011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "100101010110100001000011010000011110011",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001101010101100011000010010000011110011",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "101111111000000000111101010000110001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "011101110011000111011011110000100001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "110011010111000000011100000001110101011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "011001100000000000110000010000100101011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.90", square())
                        )),
                new Candidate("SQ", "110011010100000000010001010000100111001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "100010010100000000011001101100110011000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "111111100110000111110010010101001010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001110111011100010011010100101000011000",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.92", square())
                        )),
                new Candidate("SQ", "111101100010000111110010010111000101011",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "101000001101111001001001110110000010001",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "001010010110101010010000010000000011011",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "101010000101011110110010010101000010101",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "111011100100000011110110110100100110110",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "101100100101000101011011011101110111001",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "000101000001010011010011110000000100011",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.93", square())
                        )),
                new Candidate("SQ", "001010000111011110100010110111000010100",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.94", square())
                        )),
                new Candidate("SQ", "001001101000101011101101000011100100010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "110101000001001001110111111111110001001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.91", square())
                        )),
                new Candidate("SQ", "001110000100111111101010110111010111111",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.95", square())
                        )),
                new Candidate("SQ", "111100101000101000110010000011100011011",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "111001101000011000010010000010000010011",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("SQ", "100001000000111110110010010101000010111",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "0.92", square())
                        )),
                new Candidate("SQ", "101010000101011110110011110101000010100",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        ))
        );
    }

    public static List<Candidate> cloudfareCandidates() {
        return asList(
                new Candidate("NET", "011100010111011001011100101111101001100",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001101000100010010010110011101111001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101001101000100001010011000000110101101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000000001111010010011100000011111010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001101000100010010011100000111111010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101001110010111011101000000010111101011",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001101000100010011001000000100100100",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110010111011101000100000100111010",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "111010001110100011000100010001100111110",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101111101110000010010001010101100110010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110001110110101010010001101011000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101001100101111110101000011011111001111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000100101111110111000000001111011010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101001110001110101100011110000000111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "010001001111010001011000000001101111001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110000010000010000110000000111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110001110110101110010000100101011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000101111100000010000010000101111110",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101111001111100010010100010001100101010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100111001101111001101110100001100111101",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101111001100100110101010011101100111011",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000010110100100010000100100100101000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "111010101110100000010001110101100111000",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "111010001100100010011000000011111111100",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000100110100010000010011101100111110",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101111001110100000010000010000000011010",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "000100101110100010010000010111101111000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110000100001001111111011100001001",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "110010101111100010010000010101100111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "011000100110101010000011011111110111010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "001100011000100100010000100110100101001",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100001110101111010010100111110101000101",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101001110001011000011100111110110000101",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "100101110000011010101000111100111000101",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "000010110111001010000110110001100111001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101000010110100110010000100100100101000",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        )),
                new Candidate("NET", "101101110010011000010100111110011001101",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("NET", "1.00", cloudfare())
                        ))
        );
    }

    public static List<Candidate> splunkCandidates() {
        return asList(
                new Candidate("SPLK", "101001111010001000110010110000100000111",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111100100000111010101001000011111001000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011100010101111111110101101101111110010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.95", splunk())
                        )),
                new Candidate("SPLK", "011010100001100111100101100010000111101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111101000110101000010100110100001111010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.92", splunk())
                        )),
                new Candidate("SPLK", "010101000010110010010011110100100110011",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.92", splunk())
                        )),
                new Candidate("SPLK", "111100101000101001101001000011100100010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.91", splunk())
                        )),
                new Candidate("SPLK", "001000010100100100010000100100100101001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100010110100100010000100100100101010",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.94", splunk())
                        )),
                new Candidate("SPLK", "011010111000010010000100011110111110000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111010101110000010010101010001100010010",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "010001001111000000001100000010000111000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "110110100110100000110000011101100111010",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011010001011100010010000010001001111110",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100100000111001101000100011100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111100000000111001100000110000100011000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111010110110111000001010000100100000010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.92", splunk())
                        )),
                new Candidate("SPLK", "111100000000111001101000100000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100100000011001101001010000011011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "100100100000111001101001000011100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100100000111001101001000011100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011110001111100011001100010101100101010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011010000000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011110000000101001101001000011100100000",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100010110100100010000100100111101001",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "011101000000111111011101110111010100011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.94", splunk())
                        )),
                new Candidate("SPLK", "111010001110000000011010010101100011010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "001010010111010111011111011010011110100",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100010110100000010000100100100111001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "100000101000010111001100010101110111000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.90", splunk())
                        )),
                new Candidate("SPLK", "111110100000001001111101000001100110000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111101000001101001101001000011100010000",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.91", splunk())
                        )),
                new Candidate("SPLK", "101100010110100100010000100100100100001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111011101001011000011000000010000010011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111010001011100010010010100100101011001",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100010110100100010000100100100101001",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111100101000101001101001000011100100010",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "101100010110100100010000100100100000001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("SPLK", "111101000000100001111001000011110100010",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.93", splunk())
                        )),
                new Candidate("SPLK", "010010001111100110000000000111100111000",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPLK", "0.91", splunk())
                        ))
        );
    }

    public static List<Candidate> ciscoCandidates() {
        return asList(
                new Candidate("CSCO", "010010010000100110111011010000100001000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "110000010010100110011010110011000101110",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "010011000110110101111110001101101001111",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100111100010100010111000011101110111001",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "0.93", cisco())
                        )),
                new Candidate("CSCO", "111001011110100010010000010100100111000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "110000001001100010010000010101100111010",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101000010110000000110010000000000000001",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "001101001010000010110010100000101010111",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "001101001000100010110010000000000011011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100100011110100010010000100101100011001",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101100010110100000110001110000100000111",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "001101001011000010110010000000110100010",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101100001110100011010000010100101111010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "011010101110011111000010010100100111101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100010001111110000110000010111100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101100000110011001111110000011000110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "011110001111110100001001100101100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "010011011011000010001011100101100101001",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CSCO", "0.90", cisco())
                        )),
                new Candidate("CSCO", "101010001010000011010010010101011011010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100000001110000110010000110100100001010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101100001100001001111101010000000111111",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "010001000011000110111000101101011100001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "111010000110110010110001000101100111010",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "101010111010100000000100011001000111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "110110100000101011110110110010100010010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100010001100100010111000010101000111010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "111110001011001110001110100001111000011",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "010100101100010100001001110000000101101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "010100101100110100001000010000000101101",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("CSCO", "100010001011100111010000101001100001110",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        ))
        );
    }

    public static List<Candidate> biotechCandidates() {
        return asList(
                new Candidate(BIOTECH_SECTOR, "011110000000101001101001000011100100010",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.91(10/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "111010000110100011010000010001000011010",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(6/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "110000001100111010010000010101000111010",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(2/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "111010011100101010110000000110000111010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(2/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101010001110100000010000010101100111010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(11/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100001010101010010101010101000111001010",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(6/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101000010111110110101000010000111100001",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.93(14/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100001110101110010101010010000101001011",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(12/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100000011100110110101001010000101001010",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.94(16/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101001010101110100101010010001111001011",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(12/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100000110101010110101000110000111011011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.95(18/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100001010101110010101010000010000010010",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(12/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100001010101110101101000110010101000010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(12/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110101100010100011100000010110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.91(10/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011100000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011100000011110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011100000011111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011010000111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011110000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "001011110100100011010011100000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "111000001010100110010000010101000110110",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(2/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "110100001110101010111001010001101110010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(11/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100000110011110101010011000010010101111",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(2/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101011000101011110110010010101000010100",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100110001110001010110000000111001111000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.93(14/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "011100000100101010111000000011100000000",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101111000101011111110000010101100010000",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(8/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "111101100010000111110010010110000010001",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(8/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "110000000010001010110010010101010110010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.92(24/2)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100101101000000110010010010100111000011",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.95(18/1)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101001001100001100000000110101111101100",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "100000000010110010010111110100100100001",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(8/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101011000101011110110010010101000011100",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "101011000101011110110000010101000010100",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        )),
                new Candidate(BIOTECH_SECTOR, "000001001001001010010101110001010010010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "1.00(4/0)", biotechSector())
                        ))
        );
    }

    public static List<Candidate> healthCandidates() {
        return asList(
                new Candidate("HEALTH", "101101010100000011011011010000011110110",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.92(12/1)", healthSector())
                        )),
                new Candidate("HEALTH", "010001011111010001110000100001000111000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(6/0)", healthSector())
                        )),
                new Candidate("HEALTH", "011110000110100100100011100101001010000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "011010110110111001010010010101101111000",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.96(23/1)", healthSector())
                        )),
                new Candidate("HEALTH", "111001001111001000111101101001101011111",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000101101011011000001000000111000111100",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "001010110001111001100000001111000101100",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.93(13/1)", healthSector())
                        )),
                new Candidate("HEALTH", "001110000000111111010001111111001111101",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.94(16/1)", healthSector())
                        )),
                new Candidate("HEALTH", "111010011110100010010100010101000111000",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.91(10/1)", healthSector())
                        )),
                new Candidate("HEALTH", "000110100101100101010111101101001010101",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.92(22/2)", healthSector())
                        )),
                new Candidate("HEALTH", "010010011110000000010000110101100111000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(6/0)", healthSector())
                        )),
                new Candidate("HEALTH", "101010001110100110011000010101100101110",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.91(10/1)", healthSector())
                        )),
                new Candidate("HEALTH", "000110100101010011001011101101100101100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000110100101010011101000101101000010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "100101000010110010010011110100100100011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.92(11/1)", healthSector())
                        )),
                new Candidate("HEALTH", "000110100111110011001001000011100111110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000110111001111011001000101101000101100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        )),
                new Candidate("HEALTH", "111010001110100010010011110101100100010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(8/0)", healthSector())
                        )),
                new Candidate("HEALTH", "111101000100101000010111010111111111010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.91(29/3)", healthSector())
                        )),
                new Candidate("HEALTH", "000000010010111110111110111010001001111",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "100110011111101001010000101111110100110",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.93(14/1)", healthSector())
                        )),
                new Candidate("HEALTH", "110011000111000111101110111010100100000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "110011000011100111100110111011000100000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(6/0)", healthSector())
                        )),
                new Candidate("HEALTH", "101000111110100111010101010101100111011",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(6/0)", healthSector())
                        )),
                new Candidate("HEALTH", "010001001111010001011000000001100111011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "010001001101000000011000000101100111000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(6/0)", healthSector())
                        )),
                new Candidate("HEALTH", "010001001111010001011000000001100111000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000011110001010011001000101101000101100",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000011110001010011001010101100000001100",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(2/0)", healthSector())
                        )),
                new Candidate("HEALTH", "000111000001100101010101101000100001101",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(HEALTH_SECTOR, "1.00(4/0)", healthSector())
                        ))
        );
    }

    public static List<Candidate> amazonCandidates() {
        return asList(
                new Candidate("AMZN", "001101001010000000110010000000000000001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "101111011010011000110010111000110001001",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.93", amazon())
                        )),
                new Candidate("AMZN", "111100100000111001101000110000000011011",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "100110100100011000001100110000100011001",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        )),
                new Candidate("AMZN", "100110100111111011101001010010000111101",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        )),
                new Candidate("AMZN", "111100100000111001101000110000100011000",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "100110100111101000110010110000100000101",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        )),
                new Candidate("AMZN", "101110100100111001101001000011100001010",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        )),
                new Candidate("AMZN", "100001110100111001101001000011100011011",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.92", amazon())
                        )),
                new Candidate("AMZN", "111010001100100010000010010001101111010",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100110000100010000000011101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100100000100010001000100000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100100000100010000000101110",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100100000100010000000101101",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100000001100010000000000100",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110110000010100100000010100010110100100",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "110011001100100100000100110000001000101",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "100010100110000001011000000000100001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "111010001111000111100110011010100101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "010100101100110100001000010000000101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "010000100110100010010000010000000111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.92", amazon())
                        )),
                new Candidate("AMZN", "011000100010100111000110110010111101100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "010100100000000001001001110000010101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "010100101100110100001000010000000101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "011000011100000110010001010101010111011",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "101010000000011100110010011100101111110",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "101000001110100010010000010100100110000",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "001001001110100010110000101100011110101",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "101001110100000110011000000100010110011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.90", amazon())
                        )),
                new Candidate("AMZN", "100100001010010110111011100100100100011",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.90", amazon())
                        )),
                new Candidate("AMZN", "110011000010010010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "101101111110110011010010110100000101111",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.93", amazon())
                        )),
                new Candidate("AMZN", "110010100110110011101000010100010011100",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "100101110110111110010011110100001100011",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.90", amazon())
                        )),
                new Candidate("AMZN", "110001010100101001111100000001000100100",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "111100101000101001101100000001100100010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.93", amazon())
                        )),
                new Candidate("AMZN", "111100101000101001100000000011010100011",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.93", amazon())
                        )),
                new Candidate("AMZN", "111100100000101001101000000011100100000",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.94", amazon())
                        )),
                new Candidate("AMZN", "011000100001110101100101100010100111101",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        )),
                new Candidate("AMZN", "011010100000110101100101100000000111101",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "101101110101111111101101010011101111011",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.90", amazon())
                        )),
                new Candidate("AMZN", "010100101101110100001000010000000101101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "010100101100110100001000010000000101101",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("AMZN", "101010111110100010011010110100000110110",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AMZN", "0.91", amazon())
                        ))
        );
    }

    public static List<Candidate> aaplCandidates() {
        return asList(
                new Candidate("AAPL", "001101001000100010010011110000011110011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011110000000101001010111100000000001001",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.91", aapl())
                        )),
                new Candidate("AAPL", "001000011111100010010010000101001001001",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.90", aapl())
                        )),
                new Candidate("AAPL", "011010000010111111111111100000001010101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.92", aapl())
                        )),
                new Candidate("AAPL", "001111110001110011001010101100011111111",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011110111110100010010010010100100111010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "100100001110001101110111111110000100010",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.93", aapl())
                        )),
                new Candidate("AAPL", "011010000110010010010110010100110101110",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.93", aapl())
                        )),
                new Candidate("AAPL", "111010001100100011000001011100100111000",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001110010100011000010100110000100111011",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001110010001011001010100111110011011001",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000110100101100010111100110000011001001",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000110100101100011010111101101111111001",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000110100101100011010111101101000000101",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000101001000100000111011110001110010011",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001110001000100100111001000010000010001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "010110110101101111100001111111001000100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011101110111001111100110000000111001011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001011100101011110111011010101000010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.90", aapl())
                        )),
                new Candidate("AAPL", "011110000110110111011100010111101100000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "010011101011100000111101000001100101000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "100111000101010111110010010101010010101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011100100100111000010110111110011000101",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011100010100011000010100111110001000101",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "010100100100011000010101111110011000101",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011100100100011000010100111110011000101",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "011100110110111000010100111100011000101",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "010000100101011000010100111110011000101",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "101100010010100001010000100100100101001",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.90", aapl())
                        )),
                new Candidate("AAPL", "100011001110000100111101000010101110100",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.91", aapl())
                        )),
                new Candidate("AAPL", "101000000010011101011011001100001010111",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.91", aapl())
                        )),
                new Candidate("AAPL", "011010001111110011100001100101101110011",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.90", aapl())
                        )),
                new Candidate("AAPL", "101100110100011110010101011010100111101",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "100000011101101010100110001101110110010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "0.95", aapl())
                        )),
                new Candidate("AAPL", "011100001011110011011000000001100011111",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001100001011010001011000010011100001010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "001100001011010001011000000001100011111",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000011110001010011001000101101000101100",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "000011110001010011001000101101000101100",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("AAPL", "100111111001111101101110100110111010101",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        ))
        );
    }

    public static List<Candidate> emergentAdrCandidates() {
        return asList(
                new Candidate("EMERG ADR", "110000000001111001101000110000100011001",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "000100001001010001011001000001100011111",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "110100011111011000010011110100100111011",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "011110000000101001101001000011100100111",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "010110110110101010011000010101000111010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.90", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "101001110100001000011111100101000100010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.92", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111100001110110010110000010101100100001",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001010100100100110110101110100101010000",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.90", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111111101110100010010001010101100111110",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001011111001000001001001001000010000011",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.93", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001001001000100010111010110000100010001",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001110001000100110111010110000100111001",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001101001011100000111010110000101110001",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.91", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001100001001100010111111010000111111000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.92", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "000100101000100010111011010010011100011",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001100101000100010111110101101110101101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001100100101100000111011100010101111110",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "000011011010111010010101101001010000100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111010001100100011010100010101100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "101100110001000111100100110011011101011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.92", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111010010110000000010000000101110111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001000001111101010010000110000010100111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111010001110100010010000000100110101010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111100101000101001111001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111100101000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111100101110100010010000110101100101010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111100101000101001101001000011100000010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "101001101000101001101110000111100100011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "001100000011010001011000110001101011111",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "110110001010110011101001000111011101010",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "100101000010110010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.91", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "100101000010110010010011100100100100010",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "100101000010110010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.90", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "110000011110100010010010010101101111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111001001110100010010000010101100110010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111011000011101010010011010100100100010",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.94", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111010011100001010101010001100110111010",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "0.91", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "110010000000101001000000000100110111000",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("EMERG ADR", "111010001110101010000000000101000111000",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        ))
        );
    }

    public static List<Candidate> developAdrCandidates() {
        return asList(
                new Candidate("DEVELOP ADR", "001111010101001001010111000011100010000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "011010000010100000010100010000101101000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "001011001110000010010000010101100111000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101001001110100110111001010101100111110",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "0.91", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111100001110100110011010010111100101010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "0.93", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111111101011101001101010000100101010000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "0.92", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101100001110010010010010110100100000011",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "0.91", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "010011100011101011010110001011101010011",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "100000101111111001111101000100000100110",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "000100101011110001011001000001100011100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "100101100111101100111111110100000111011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111010011110100010000001110111100111111",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111010000111100010010000011011100101110",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "100110110011100100111111110100001111010",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "100101100111101100111111110100000111010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101110110000111001111000100000100011011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111100100101110001110000110000100010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101001101010001000110010110000100001111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101001101010001000110010110000100000111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111000100100111000100000110100110010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101001101010001000111000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111100000100111001101000000010111000110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "110011001000101010111101100001100001000",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "011110000000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "110011001000101010111101100001100101000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "110011001000101010010001100011100101010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "011110000000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "110011001000101010111001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101100001100001000110010110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101100001111001000110000110000100000001",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "100001100011001000110000110000100001100",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101100000010001000110010010000100001100",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101100000110001000110110110000100101000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "000010001000000110111011001000111111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "101010001111101010010000100101100111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111010011110100000010000110101000011010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "011011001100100010110010010101110011110",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "001010001101100000000001010101101001010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "011110110111001111001111001111101111010",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "0.93", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "110010101100001010010000010100000111000",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("DEVELOP ADR", "111100101000101001110101000011100100010",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        ))
        );
    }

    public static List<Candidate> consBasicCandidates() {
        return asList(
                new Candidate("CONS BASIC", "111101100010000010100010010110000011011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.91(10/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "000001111001000111011001010000111001011",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.90(9/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "111010100100100010010100010110101010010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.93(14/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "101110101101000001010101010101100110011",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(2/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "100111111110011010100110101011001000110",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "001011010110100011111001000011100110100",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(6/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "011010010111100100010110100100100101000",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(8/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "100000011101010111010101001010111101000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.93(14/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "110010010100001000110000010000100001000",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(2/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "001001101110000111011000001011000010010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.90(9/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "110011001100100100110000010000100001000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "110001010100001000011000010000100001000",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.90(9/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "100101000011110010010000110000100001100",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.94(15/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "111111011111000111010000010101000111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "111000001110100010010110010101101111110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "100101000010110010010011110100100100011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "101001001110100010010100011100100100010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "100000001111110100110000010101100001011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(2/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "111110111110100010010000010101110010110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "111010001010101010010000011101100100010",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.91(10/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "011111010011101001110000010101100110011",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.94(16/1)", consumerBasicSector())
                        )),
                new Candidate("CONS BASIC", "000000110110101100011000010010101110011",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00(4/0)", consumerBasicSector())
                        ))
        );
    }

    public static List<Candidate> consDiscCandidates() {
        return asList(
                new Candidate("CONS DISC", "001011110100100011010011100000111110011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(13/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "100011000010101010111101100000101101010",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(38/3)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "101010011110110010010000010101000111000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(6/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111010001110110011111000000101000001010",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.92(12/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111000000110100110110100010101000101110",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "010000101000100010111010100011100011001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(14/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "000001010000111011101011010011100100010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(14/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "110011101110000100011101001010100110000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.92(11/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "001101010010111011101011000011001010100",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(14/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "001101010010111011101011000011100110100",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(14/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "000000110010111010101011000001100111000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.94(16/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "001001100010111011101001000011100110101",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(12/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "001000110110111011101001000011100110100",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(14/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111010000110110010010001010101001111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.92(22/2)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111010001100000010110000001111100101100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.91(20/2)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111010011111100010010000010011100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93(14/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "110010001110110001010010010100100110010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(6/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111000001111101110010100000100100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(4/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "000010001000000010111011001000111111000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "101010001110111000110000010101110111110",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "101011001011001010000010000101100010111",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.92(11/1)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "000010001000100010111011001000111111000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "011100010111000001110111010110100001100",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(6/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "000010001000100010110011001000111111000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "110010001111100010010100010101100110011",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(6/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "101001010111101000100101010101011010001",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(4/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "110010011111000001010000010101100111011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(6/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "110000001110100010010000010101100111000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "111010101010011010001100010111101010010",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.91(48/5)", consumerDiscSector())
                        )),
                new Candidate("CONS DISC", "010100101100110100001000010000000101101",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "1.00(2/0)", consumerDiscSector())
                        ))
        );
    }

    public static List<Candidate> financeCandidates() {
        return asList(
                new Candidate("FINANCE ", "100101000010110010011011110100100100011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.94", financeSector())
                        )),
                new Candidate("FINANCE ", "100001010101110110111010010000110111011",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "011010001110000000110110010101001011011",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "001111001110111000111101100100010011101",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "011110101000010100011011100110000110111",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "111101001110000111110010010111100011011",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.90", financeSector())
                        )),
                new Candidate("FINANCE ", "110000001010100011010001010101100111000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "101011101111000110100001000101011011101",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "101001101111101100111111100000001000001",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "100110100111101100111111110100000111010",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.91", financeSector())
                        )),
                new Candidate("FINANCE ", "011001101000010100100000001100101110010",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.92", financeSector())
                        )),
                new Candidate("FINANCE ", "111011011010100010010000110101100111011",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "100110000101001100111111110000100111010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.91", financeSector())
                        )),
                new Candidate("FINANCE ", "100110100111100110010111110100000111010",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.94", financeSector())
                        )),
                new Candidate("FINANCE ", "100110100111101100111111110100000111010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.91", financeSector())
                        )),
                new Candidate("FINANCE ", "001101001000100010010000101101000010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "001101001000100010000011110000001111111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "001101001000100010010011110000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("FINANCE ", "001101101010100010010011110001111111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.93", financeSector())
                        ))
        );
    }

    public static List<Candidate> commCandidates() {
        return asList(
                new Candidate("COMM", "111100101000111001101001000011100011011",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.90", commSector())
                        )),
                new Candidate("COMM", "011001000101010011011001000001100111000",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "111011011011100011011010100101100001010",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "111110001101000000001100010100011110011",
                        asList(StochasticOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "100100100111100101010101111000111001010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "111010001110100010010000010101100101000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "101101010110100100010000100100100101001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "111101100100000111110110010111000011000",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.90", commSector())
                        )),
                new Candidate("COMM", "110001000111000100001000010000000101011",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "110101110100110001001000010010001010101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "110010011110000100011110001010101110000",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "100001001000101000101010010000111000010",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "110011001101000100110000010000111100010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "100001001000101000101010010000111001010",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "111100111000001001101101000001110110010",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "111101100101000000101110000001000110110",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "100011011011111111100011110100101111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "010111001111000010010010000101100111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "100000111011011010111111001111011010111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.90", commSector())
                        )),
                new Candidate("COMM", "111010001110100000010000010101100111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "001101010010111000101001100101010111001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "111111001010000111100010010111000011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.90", commSector())
                        )),
                new Candidate("COMM", "110011001000101001101001000011100100110",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "101101111000101001111000000001100100010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.91", commSector())
                        )),
                new Candidate("COMM", "100011101000101001101000000001100101010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.95", commSector())
                        )),
                new Candidate("COMM", "110011001000101001101001000011100100010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "110011001000101001101001000011100010010",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "101101010010001000110000010000100001110",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "101101010010001000110010010000100001000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "101101010010001000110000010000100001100",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.92", commSector())
                        )),
                new Candidate("COMM", "101101000010001000110010010000100001100",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.93", commSector())
                        )),
                new Candidate("COMM", "100001100010001000110010010000100001000",
                        asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "100110100111101100111111110100000111010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "100110100111101100111111110100000111010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "100110100111101100111111110100000111010",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "101110100111001100000001110101000111010",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "011011001110100010000000010110100111010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "110011010000100010010000010100100111010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("COMM", "001100011011110001001011011010000011110",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        )),
                new Candidate("COMM", "101000110000100000011101100101010011001",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(COMM_SECTOR, "1.00", commSector())
                        ))
        );
    }

    public static List<Candidate> techCandidates() {
        return asList(
                new Candidate("TECH", "100110100111101100111111110100000111010",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.91", techSector())
                        )),
                new Candidate("TECH", "010001001111010001011100100001000101001",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.92", techSector())
                        )),
                new Candidate("TECH", "101011001101110110110010010101000010000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.92", techSector())
                        )),
                new Candidate("TECH", "111011101110100010011000110101100101010",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "101010001110010000010101000111000111110",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "011100010100100110011011110110000001111",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100111000010010010010011110100110100001",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.91", techSector())
                        )),
                new Candidate("TECH", "100001101000111001101001010011100001000",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100101101000000000110000110010100001101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.93", techSector())
                        )),
                new Candidate("TECH", "101000101010000000110000110000100001100",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.91", techSector())
                        )),
                new Candidate("TECH", "101110010100001000101100110010000001101",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "101101011010000000110000110000100001100",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100111001010000000110000100000000011000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.90", techSector())
                        )),
                new Candidate("TECH", "101101011000000010110000110010100001111",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.93", techSector())
                        )),
                new Candidate("TECH", "101011000100101011100000100011100100010",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.93", techSector())
                        )),
                new Candidate("TECH", "001011100101100110111010010101011110100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.94", techSector())
                        )),
                new Candidate("TECH", "001001111100100010010110110010111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "001011111100100011011011110000110110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "001011111100100011010010100000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "001011111101100011010011100000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "001011111100100011010111101110111110111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "000011111100100011010011100000111110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "010111101110101010000010010001100100000",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "111011101010001001101010000001010011011",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.94", techSector())
                        )),
                new Candidate("TECH", "111010001010101010010000000101101111000",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100010100100101010010100001101100110010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.92", techSector())
                        )),
                new Candidate("TECH", "111001001101100010011100010100100100110",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100001010101110110101010010000111001011",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "111010001111100011010000110101100111010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.92", techSector())
                        )),
                new Candidate("TECH", "101010001010110010010001010111100111010",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.90", techSector())
                        )),
                new Candidate("TECH", "111010001110110010000011010101110111000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "100101011010110010010011110100100100011",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.94", techSector())
                        )),
                new Candidate("TECH", "110000110100101001010001110111000110001",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.91", techSector())
                        )),
                new Candidate("TECH", "111010101110101011001000010100100111001",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.94", techSector())
                        )),
                new Candidate("TECH", "100010111111000011010001010100100101101",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "0.93", techSector())
                        )),
                new Candidate("TECH", "001001111110100011110110111001111100100",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "111010100110100000000010000101010111010",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "000111110110010011001000101111010101100",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("TECH", "000011111001010011001000101101000101100",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        ))
        );
    }

    public static List<Candidate> etfSectorCandidates() {
        return asList(
                new Candidate("SECTORS ETF", "001011110010111011101001000011100110100",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "110100110100011101100010110011111011001",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "110100010101110110110111000010001010000",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "111010001101100110101101110101001010110",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "111010000111100010010001110110100101010",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "101011000101011110110010010101000010101",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "101011000101011110110010010101000010101",
                        asList(StochasticOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "110101100010011101110000110101010011001",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "011100010100011000010100111110011000101",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "001100000101100011010000111111000111101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "000100100100100011010010100000011111011",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.94", sectors())
                        )),
                new Candidate("SECTORS ETF", "001110111101100011010010000001100001101",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.94", sectors())
                        )),
                new Candidate("SECTORS ETF", "000000000110100011010011000000110010010",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "001100100100100011010011100000000001000",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.94", sectors())
                        )),
                new Candidate("SECTORS ETF", "000110101001010011010010100000011111111",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "000000101111000010010010100000100010001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "111100100000111001101000110000100011111",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111100100000111001000100001001011001101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "111000100000111001101000110010100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "000100110000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "111100100000111001101000110000100011001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111100100010111001101000110000000101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111101100111000111110101010110000011000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("SECTORS ETF", "111110101110101011011000001100111110000",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111010011010100010010100010101000110000",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "110010001000101000010010010110110111010",
                        asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "111011001110100010010000010101100111010",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "110011000111000111100110111010100100000",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111101100000100011110010010111000011000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "111010001110100010111100010111110011000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111011001111100010010000010101100111010",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "101100010110100100010000100100100101001",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "110010011000101010111110000000011101010",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("SECTORS ETF", "110011001000101010011100100000100101010",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "110011001000101010111101100001100010101",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.94", sectors())
                        )),
                new Candidate("SECTORS ETF", "110011001010101010111100000011010111011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.92", sectors())
                        )),
                new Candidate("SECTORS ETF", "111010011110100010010011010101100111010",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "101011000101011110110010010101000100000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "111101100010000010101010010111000011000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "111011000111000111100110111010100100000",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "1.00", sectors())
                        )),
                new Candidate("SECTORS ETF", "111001100010000011111010010111000011000",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors())
                        )),
                new Candidate("SECTORS ETF", "111101101101000001110010000111000011001",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        ))
        );
    }
}
