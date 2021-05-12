package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.order.condition.ConditionalOrderConditionGenerator;
import com.egutter.trading.out.CandidatesFileHandler;
import com.egutter.trading.stock.StockGroup;
import org.uncommons.maths.binary.BitString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.egutter.trading.stock.StockMarket.*;
import static java.util.Arrays.asList;

public class GlobalStockMarketCandidates {

    public static List<BitString> rsiCrossDownChromosomeCandidates() {
        return Arrays.asList(
                new BitString("000010100111000110101000000100000101001"),
                new BitString("010010110101010011100000000101110110001"),
                new BitString("010100101101110000110110110011000111111"),
                new BitString("010011100000011001111110111100111111101"),
                new BitString("000111111101001110111110111110001100101"),
                new BitString("110011101110111111100111100011001001011"),
                new BitString("001011011001001110100000111001000111111"),
                new BitString("100010101010100010000010011110111110010"),
                new BitString("011101101000111010111100000110111010011"),
                new BitString("100101101000101000101111011000111101011"),
                new BitString("011100111111000100101101011111110101100"),
                new BitString("001110111001001101000001100111001101111"),
                new BitString("001100110010001011111000101010000111010"),
                new BitString("001100100101001011000000000101001111001"),
                new BitString("101010110001110100000010000100110011010"),
                new BitString("000000111100101110111101100000000100110"),
                new BitString("011111110011011111100001010110011011111"),
                new BitString("000000101111011011010001111110000010010"),
                new BitString("000110111000101100110110000100001111100"),
                new BitString("001100101011101000111000010110000101010"),
                new BitString("100000110111101111000000100101000111001"),
                new BitString("000100100001110111011110010101010101111"),
                new BitString("010111101110010111010000001001001110110"),
                new BitString("001010101010100011010000111000000111000"),
                new BitString("011110110001000001011110010110001011011"),
                new BitString("001000100010001001110010101010000100111"),
                new BitString("000000101010100010000010110110000110110"),
                new BitString("000000111000101110111101100000000100111"),
                new BitString("000010100101100101110000001001000011000"),
                new BitString("000100110010011011101100110010011101101"),
                new BitString("000100111010011111000011001000000101010"),
                new BitString("000100111111111111001100110110000101101"),
                new BitString("000101110010101011110000101100001110110"),
                new BitString("000101110011001001111111100100000101111"),
                new BitString("000110111000001000100101000100001001101"),
                new BitString("001000110000100001011101101000000001010"),
                new BitString("001001101100000001000000110101001110101"),
                new BitString("001101100111011011011001100100000011111"),
                new BitString("010001111010101110010000100111000100100"),
                new BitString("010010111011111111100011101010000111000"),
                new BitString("010111101100101101100011100101111111100"),
                new BitString("010111101101001011000000000101001111100"),
                new BitString("011001110011000011010011001000000110100"),
                new BitString("011010111000001100000100110111000100111"),
                new BitString("011011111010000000011110100010011001101"),
                new BitString("011101111000101100110110000100001111111"),
                new BitString("011111101111101000111000010110000101010"),
                new BitString("011111110011011111100001010110011011100"),
                new BitString("100000110111101111000000100101000111100"),
                new BitString("110000110100101100011000010110000001111"),
                new BitString("110011110111100111010000000110000100000"),
                new BitString("111001110011001001101100000001000011010"),
                new BitString("001001101100000001000000110101001111100")
                );
//        return newNewerCandidates().stream().map(candidate -> candidate.getChromosome()).collect(Collectors.toList());
    }

    public static List<BitString> chromosomeCandidates() {
        return new CandidatesFileHandler().chromosomesfromJson();
//        return newNewerCandidates().stream().map(candidate -> candidate.getChromosome()).collect(Collectors.toList());
    }

    public static List<Candidate> allNewCandidates() {
        return Stream.concat(newNewerCandidates().stream(), UsaStockMarketCandidates.allCandidates().stream())
                .collect(Collectors.toList());
    }

    public static List<Candidate> spyCandidates() {
        return asList(
                new Candidate("SPY", "011100000100101010111000000011100000000",
                        asList(StochasticOscillatorGenerator.class, ChaikinOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(3/0)", topSpy())
                        )),
                new Candidate("SPY", "000101001100110000001000010000000101111",
                        asList(StochasticOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "001000101010011001111110000011100010100",
                        asList(StochasticOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(7/0)", topSpy())
                        )),
                new Candidate("SPY", "100000100010010010010011100100101011110",
                        asList(StochasticOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(7/0)", topSpy())
                        )),
                new Candidate("SPY", "101001010011011110010001100100101101001",
                        asList(StochasticOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "101101110001000010111011110100000111000",
                        asList(StochasticOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "100000111011011000001010000100100000010",
                        asList(StochasticOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "110110000010100100000000101010100010100",
                        asList(ChaikinOscillatorGenerator.class, MovingAverageConvergenceDivergenceGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "010101100100010000000011100000100010101",
                        asList(ChaikinOscillatorGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "010101101100110101000010010000011001101",
                        asList(ChaikinOscillatorGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "110010000000101001000000010000101011110",
                        asList(ChaikinOscillatorGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "110010000000101001000000010000010101000",
                        asList(ChaikinOscillatorGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "110110000010100100000000100000100010101",
                        asList(ChaikinOscillatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "010101101100110101000010010000000011011",
                        asList(ChaikinOscillatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "010101101100110101000010010000010111001",
                        asList(ChaikinOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "001101001011100000111010110000100111001",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "001101001000100000110000010000000101010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "001101001011100000110111010000100111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "000101001000100000111011110000101110011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "000101001000100000101000010000000111011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "001101001011100000111010110000000110101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "100100100100010111110111110101111000000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","0.91(10/1)", topSpy())
                        )),
                new Candidate("SPY", "111100101001111101101000000011001011110",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "001101011101100000000001010101100011010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "001101011101100000000001010101100011010",
                        asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "010110001100101001110000010011100100011",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "010110001100101001100111000001100110010",
                        asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "111000101010100001010001010100100100000",
                        asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "001101011101100000000001010101100011010",
                        asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "111101011110000110001001010010101110110",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "110000100001000110000011110010000001001",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "111001011100000110111011110011000101100",
                        asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(5/0)", topSpy())
                        )),
                new Candidate("SPY", "000011111111001010010010010010100101000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class),
                        asList(
                                new StockGroup("SPY","0.92(12/1)", topSpy())
                        )),
                new Candidate("SPY", "100001001011101010111111100100000110000",
                        asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        )),
                new Candidate("SPY", "111100101000101001100000000011010010011",
                        asList(BollingerBandsGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","0.91(10/1)", topSpy())
                        )),
                new Candidate("SPY", "011010101111100110101000010101000111011",
                        asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "101000010110110011001000101101000100000",
                        asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "101100100100100100000000100101110011000",
                        asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","0.91(10/1)", topSpy())
                        )),
                new Candidate("SPY", "110001001010101001101001010011010110101",
                        asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(4/0)", topSpy())
                        )),
                new Candidate("SPY", "111100101001101000010000010100001011000",
                        asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(11/0)", topSpy())
                        )),
                new Candidate("SPY", "111000101010100001010001010100101111000",
                        asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class),
                        asList(
                                new StockGroup("SPY","1.00(6/0)", topSpy())
                        ))
        );
    }

    public static List<Candidate> newNewerCandidates() {
        List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators = Arrays.asList(StochasticOscillatorGenerator.class,
                ChaikinOscillatorGenerator.class);

        return asList(
                new Candidate("Tech Sectors", "100001100010001000110010110000100001000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.75", techSector())
                        )),
                new Candidate("Tech Sectors", "100001000111001101111011110000110001001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.64", techSector())
                        )),
                new Candidate("Tech Sectors", "000010001000100010111011001000111111000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("Tech Sectors", "001110001000100110111011110000111111001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("Tech Sectors", "000110100101100011010111101101000010111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "1.00", techSector())
                        )),
                new Candidate("Tech Sectors", "110011001100100100001100010000000101101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.84", techSector())
                        )),
                new Candidate("Tech Sectors", "110011001100100100001100010000000101100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.81", techSector())
                        )),
                new Candidate("Tech Sectors", "111001101001011000111010000010000010011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.88", techSector())
                        )),
                new Candidate("ETF Sectors", "001011110010111011101001000011100110100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("Finance Sectors", "100101000010110010010011110100100100011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(FINANCE_SECTOR, "1.00", financeSector())
                        )),
                new Candidate("Comm Sector", "111100101000101001101001000011100100010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(COMM_SECTOR, "0.94", commSector())
                        )),
                new Candidate("Comm Sector", "111100101000111001101001000011100011011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(COMM_SECTOR, "0.90", commSector())
                        )),
                new Candidate("Disc Sector", "001011110100100011010011100000111110011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.93", consumerDiscSector())
                        )),
                new Candidate("Health Sector", "100001010101110110101010010000111001011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.93", healthSector())
                        )),
                new Candidate("Cons Basic Sector", "111101100010000111110010010111000011011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "1.00", consumerBasicSector())
                        )),
                new Candidate("Biotech Sector", "011110000000101001101001000011100100010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(BIOTECH_SECTOR, "0.91", biotechSector())
                        )),
                new Candidate("Innovation Sector", "110011000111000111100110111010100100000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(INNOVATION_SECTOR, "1.00", innovationSector())
                        )),
                new Candidate("Developed Markets", "000011110001010011001000101101000101100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.92", developedMarkets())
                        )),
                new Candidate("Industrial Sector", "111010001110100010010000010101100111010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00", industrialSector())
                        )),
                new Candidate("Emergent Sector", "001100001011010001011000000001100011111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.93", emergentMarkets())
                        )),
                new Candidate("Metals Sector", "101101011000001000110000110000100001101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(METALS_SECTORS, "1.00", metals())
                        )),
                new Candidate("Develop ADR Sector", "110011001110000100011111001010101110000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("DEVELOP ADR", "1.00", developAdr())
                        )),
                new Candidate("Emergent ADR Sector", "001101010100100011010011110000011110011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("EMERG ADR", "1.00", emergentAdr())
                        )),
                new Candidate("Apple", "001101001000100010010011110000111111011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("AAPL", "1.00", aapl())
                        )),
                new Candidate("Amazon", "111100100000111001101000110000100011001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("AMZN", "1.00", amazon())
                        )),
                new Candidate("Microsoft", "010100101100110100001000010000000101101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("MSFT", "1.00", microsoft())
                        )),
                new Candidate("Google", "110011001000101010111101100001100101010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("GOOGL", "1.00", google())
                        )),
                new Candidate("Netflix", "011010100000110101100101100010000111101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("NFLX", "1.00", netflix())
                        )),
                new Candidate("Tesla", "110010010100001000110000010000100001000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("TSLA", "1.00", tesla())
                        )),
                new Candidate("Salesforce", "101101010010001000110000110000100001100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("CRM", "1.00", salesforce())
                        )),
                new Candidate("Paypal", "101000010011100101010111100000000001000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("PYPL", "1.00", paypal())
                        )),
                new Candidate("Intel", "101100010110100100010000100100100101001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("INTC", "1.00", intel())
                        )),
                new Candidate("Nvidia", "101011000101011110110010010101000010101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("NVDA", "1.00", nvidia())
                        )),
                new Candidate("Electronic Arts", "100001101000100110111011010000100001000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("EA", "1.00", electronicArts())
                        )),
                new Candidate("Square", "001101011010000000110010000000000000001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("SQ", "1.00", square())
                        )),
                new Candidate("Splunk", "101001101010001000110010110000100000111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("SPLK", "1.00", splunk())
                        )),
                new Candidate("Cisco", "011100010100011000010100111110011000101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("CSCO", "1.00", cisco())
                        )),
                new Candidate("VGLT", "010001001111010001011000000001100111000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("VGLT", "1.00", longTermBonds())
                        ))
        );
    }

    public static List<Candidate> newCandidates() {
        List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators = Arrays.asList(StochasticOscillatorGenerator.class,
                ChaikinOscillatorGenerator.class);

        return asList(
                new Candidate("ETF Sectors", "1000010001110011011110111110011100011111111101110001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.87", healthSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.94", smallCap())
                        )),
                new Candidate("ETF Sectors", "0100011100001101101001001111100100001111111111101111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(ETF_SECTORS, "0.94", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.86", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.97", smallCap())
                        )),
                new Candidate("ETF Sectors", "0100001100001001101100010001011011111111111111111110",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.85", smallCap())
                        )),
                new Candidate("Tech", "1100001100000100101110111110111100000001111001110111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.90", smallCap())
                        )),
                new Candidate("Comm", "1110011100001100101011011111100100001011111101111111",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(COMM_SECTOR, "0.87", commSector()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.90", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.85", consumerDiscSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.90", smallCap())
                        )),
                new Candidate("Finance", "0001010100101010101000111110111100001011111101111011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(FINANCE_SECTOR, "0.87", financeSector()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.92", smallCap()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.86", developedMarkets())
                        )),
                new Candidate("Cons Basic", "1010100100001111111011011101001011110001111111111000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(CONS_BASIC_SECTOR, "0.93", consumerBasicSector()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.86", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.93", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.87", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector())
                        )),
                new Candidate("Cons Disc", "0100011100000100101111011111110100000111111111111110",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(CONS_DISC_SECTOR, "0.88", consumerDiscSector()),
                                new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.86", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.86", consumerBasicSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.85", smallCap())
                        )),
                new Candidate("Health", "1000010000100011011100101000100100001111111111110010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(HEALTH_SECTOR, "0.89", healthSector()),
                                new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.90", smallCap()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.86", developedMarkets()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.85", consumerDiscSector())
                        )),
                new Candidate("Industrial", "1111010100000100001011011000011100000111111111110101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(INDUSTRIAL_SECTOR, "0.88", industrialSector()),
                                new StockGroup(HEALTH_SECTOR, "0.88", healthSector()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.88", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.91", consumerBasicSector())
                        )),
                new Candidate("Emergent", "0100001100111001101011011111000100001111111111111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(EMERGENT_SECTORS, "0.87", emergentMarkets()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.87", healthSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.88", smallCap()),
                                new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(FINANCE_SECTOR, "0.86", financeSector())
                        )),
                new Candidate("Green ETF", "0010100100001011111001101111111100001111111111111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(GREEN_SECTORS, "0.87", greenSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.89", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.86", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup(COMM_SECTOR, "0.86", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector())
                        )),
                new Candidate("Small Cap", "0001010000101001101010011011101011101011111111011010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(SMALL_CAP_SECTORS, "0.97", smallCap()),
                                new StockGroup("INTC", "0.88", intel()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.86", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.86", industrialSector()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Develop Markets", "0001010100001010101000011101101011101111111101111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.89", developedMarkets()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(FINANCE_SECTOR, "0.86", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector()),
                                new StockGroup(SMALL_CAP_SECTORS, "0.94", smallCap()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("AAPL", "0100001000101011011010001011101011101011111111111011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("AAPL", "0.88", aapl()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.86", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("AMZN", "0001010000101001111101010000101011001011101001111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("AMZN", "0.88", amazon())
                        )),
                new Candidate("MSFT", "0001010000101001101010011001101011101111111101111000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("MSFT", "0.92", microsoft()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.85", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.85", commSector())
                        )),
                new Candidate("GOOGL", "0001010000101011101011001101111011001101101001110110",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("GOOGL", "0.97", google()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate("AAPL", "0001010100001010101000111101101011101111111101111011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("AAPL", "0.89", aapl()),
                                new StockGroup(DEVELOP_ETF_MARKETS, "0.87", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors())
                        )),
                new Candidate("NFLX", "0001010100001010101000111111101011101111111101111010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("NFLX", "0.89", netflix())
                        )),
                new Candidate("CRM", "0000010101101010101000011101100011110111111111111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("CRM", "0.85", salesforce()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate("PYPL", "0001010000111001101000001101001011101111111101111001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("PYPL", "0.93", paypal()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector())
                        )),
                new Candidate("NVDA", "0001010100001010101010011111101011101111111111111100",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("NVDA", "0.90", nvidia()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.85", commSector())
                        )),
                new Candidate("SQ", "0001010000101001101000001011101011100011111111011010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("SQ", "0.93", square()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate("SPLK", "0001010000101001101000001011101011100011111111011010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("SPLK", "0.86", splunk()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate(METALS_SECTORS, "1111010100000100001011011101111100001111111101111011",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup(METALS_SECTORS, "0.94", metals()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector())
                        )),
                new Candidate("VGLT", "1110011100001100101011011111101011101111111101101101",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("VGLT", "1.00 (9 ops)", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector()),
                                new StockGroup(COMM_SECTOR, "0.87", commSector())
                        )),
                new Candidate("VCLT", "0100001100111001101000010001001011111111111111111110",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("VCLT", "1.00 (22 ops)", corporateBonds()),
                                new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("EA", "1010100100101011011011011101100011010011111101111000",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("EA", "1.00 (9 ops)", electronicArts()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate("CSCO", "1111010100000100001011001101111100101011111111011001",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("CSCO", "1.00 (9 ops)", cisco()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors())
                        )),
                new Candidate("BGRN", "0001010100101100111010001100011011101010111101011010",
                        tradingDecisionGenerators,
                        asList(
                                new StockGroup("BGRN", "1.00 (9 ops)", greenSector())
                        ))
        );
    }
    public static List<Candidate> candidates() {
        List<? extends Class<? extends ConditionalOrderConditionGenerator>> tradingDecisionGenerators = Arrays.asList(StochasticOscillatorGenerator.class,
                ChaikinOscillatorGenerator.class);

        return asList(
                new Candidate("Developed Countries ETF", "1000010001110011011110111110011100011111111101110001",
                        tradingDecisionGenerators,
                        asList(new StockGroup(DEVELOP_ETF_MARKETS, "0.89", developedMarkets()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.84", techSector()),
                                new StockGroup(COMM_SECTOR, "0.88", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.87", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.87", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup("VGLT", "0.92", longTermBonds()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.86", industrialSector())
                        )),
                new Candidate("Apple", "0011000100000011001000011001011100000111111101111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup("AAPL", "0.94", aapl()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Amazon", "0001011100001010101000010010111100000111111111111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup("AMZN", "0.94", amazon()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup("VGLT", "0.88", longTermBonds()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector())
                        )),
                new Candidate("Microsoft", "0100001101100000111000000000111011111111101111111110",
                        tradingDecisionGenerators,
                        asList(new StockGroup("MSFT", "0.94", microsoft()),
                                new StockGroup("VGLT", "0.88", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.87", sectors())
                        )),
                new Candidate("Google", "0011100100001110001000000111101100001111110101111100",
                        tradingDecisionGenerators,
                        asList(new StockGroup("GOOGL", "0.95", google()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup("VGLT", "0.86", longTermBonds()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Netflix", "0000100100001011101010000011011100001011111111110101",
                        tradingDecisionGenerators,
                        asList(new StockGroup("NFLX", "0.91", netflix()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup("VGLT", "0.91", longTermBonds()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector())
                        )),
                new Candidate("Tesla", "0001011100011111010110001111100100010100001100001001",
                        tradingDecisionGenerators,
                        asList(new StockGroup("TSLA", "0.78", tesla())
                        )),
                new Candidate("Salesforce", "0001010100001010101000001001111100010111111111111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup("CRM", "0.88", salesforce()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Paypal", "0010001100011100011000000001011100001111111101110010",
                        tradingDecisionGenerators,
                        asList(new StockGroup("PYPL", "0.95", paypal()),
                                new StockGroup("VGLT", "0.87", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup("BGRN", "1.00 (4 ops)", greenBonds())
                        )),
                new Candidate("Intel", "0001010100101010101000011101011100011111111111111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup("INTC", "0.97", intel()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup("VGLT", "0.89", longTermBonds()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(METALS_SECTORS, "0.86", metals())
                        )),
                new Candidate("Nvidia", "0001010100001010101000010111101011101111111111101101",
                        tradingDecisionGenerators,
                        asList(new StockGroup("NVDA", "0.89", nvidia()),
                                new StockGroup("VGLT", "0.94", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors())
                        )),
                new Candidate("Electronics Arts", "0000011100000001101000011011101010011011101111001001",
                        tradingDecisionGenerators,
                        asList(new StockGroup("EA", "0.80", electronicArts())
                        )),
                new Candidate("Splunk", "0011010100001000011000001011011100010111110001111011",
                        tradingDecisionGenerators,
                        asList(new StockGroup("SPLK", "0.80   ", splunk()),
                                new StockGroup(ETF_SECTORS, "0.86", sectors())
                        )),
                new Candidate("General", "0011010111011111110001111100000011110111100111010001",
                        tradingDecisionGenerators,
                        asList(new StockGroup(TECH_SECTOR, "0.88", techSector()),
                                new StockGroup(FINANCE_SECTOR, "1.00 (8 ops)", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.80 (5 ops)", consumerDiscSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "1.00 (3 ops)", industrialSector()),
                                new StockGroup("VGLT", "1.00 (1 ops)", longTermBonds()),
                                new StockGroup(CONS_BASIC_SECTOR, "1.00 (4 ops)", consumerBasicSector()),
                                new StockGroup(METALS_SECTORS, "1.00 (2 ops)", metals()),
                                new StockGroup(EMERGENT_SECTORS, "1.00 (3 ops)", emergentMarkets())
                        )), // nice 0.82
                new Candidate("Metals",
                        "0001000100011111110101001011100100000001011111011110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(METALS_SECTORS, "0.88", metals()),
                                new StockGroup("VGLT", "0.85", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.88", sectors())
                        )),
                new Candidate("Long-Term bond",
                        "0001000100011111110100010111101011101111111101111001",
                        tradingDecisionGenerators,
                        asList(new StockGroup("VGLT", "0.95", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(METALS_SECTORS, "0.86", metals())
                        )),
                new Candidate("Corporate bond",
                        "0001010000101001101000000001011100000111111011111000",
                        tradingDecisionGenerators,
                        asList(new StockGroup("VCLT", "0.92", corporateBonds()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup("VGLT", "0.88", longTermBonds()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Emergent",
                        "0100001100111001101011011101101100011001110111101101",
                        tradingDecisionGenerators,
                        asList(new StockGroup(EMERGENT_SECTORS, "0.86", emergentMarkets()),
                                new StockGroup(ETF_SECTORS, "0.92", sectors())
                        )),
                new Candidate("Small Cap",
                        "0100011100001101101001001111011100001001111101111001",
                        tradingDecisionGenerators,
                        asList(new StockGroup(SMALL_CAP_SECTORS, "0.97", smallCap()),
                                new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup("VGLT", "0.87", longTermBonds()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector())
                        )),
                new Candidate("Small Cap",
                        "0011000100001010001010010111101011101111111101101011",
                        tradingDecisionGenerators,
                        asList(new StockGroup(SMALL_CAP_SECTORS, "0.95", smallCap()),
                                new StockGroup("VGLT", "0.89", longTermBonds()),
                                new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(METALS_SECTORS, "0.86", metals()),
                                new StockGroup("BGRN", "0.86 (7 ops)", greenBonds())
                        )),
                new Candidate("Green Bond",
                        "0011000101001010111000000111101011001111110111011001",
                        tradingDecisionGenerators,
                        asList(new StockGroup("BGRN", "1.00 (9 ops)", greenBonds()),
                                new StockGroup(ETF_SECTORS, "0.86", sectors())
                        )),
                new Candidate("Green Sector",
                        "0010100100001011111001101111111100001111111111111000",
                        tradingDecisionGenerators,
                        asList(new StockGroup(GREEN_SECTORS, "0.87", greenSector()),
                                new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup("VGLT", "0.88", longTermBonds()),
                                new StockGroup(COMM_SECTOR, "0.86", commSector()),
                                new StockGroup("BGRN", "1.00 (2 ops)", greenBonds())
                        )),
                new Candidate("General",
                        "1110110011100010110111011010010010101101000101000001",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.8", sectors()))),
                new Candidate("General",
                        "1110110011100010110111010101111100000001000101000010",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.81", sectors()))),
                new Candidate("General",
                        "0111111000011100111100101001010010110001000101000111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.86", sectors()))),
                new Candidate("Sectors", "1110110011100010110111010101111100100001000101000010", tradingDecisionGenerators), //sectors
                new Candidate("Sectors",
                        "0111111000001100111000111001011100000101001101000000",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors()))),
                new Candidate("Sector",
                        "0111111000001100111000111001011100000101001101000011",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors())
                        )),
                new Candidate("Sectors",
                        "0100001101100000111000000000011011111111011101110010",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.82", consumerBasicSector())
                                )),
                new Candidate("Tech",
                        "0100011100001001101000000011101011101111101111101000",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.86", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.80", commSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.81", consumerDiscSector()),
                                new StockGroup(FINANCE_SECTOR, "0.83", financeSector()),
                                new StockGroup(HEALTH_SECTOR, "0.80", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.83", consumerBasicSector())
                                        )),
                new Candidate("Tech",
                        "0100011100001001101000101011100100001111111111101100",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.83", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.83", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.82", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector())
                        )),
                new Candidate("Tech",
                        "0100011100001001101000101001001011110001111101101110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.83", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.82    ", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup("VGLT", "0.86", longTermBonds())
                        )),
                new Candidate("Tech", "0101011100001000101000000111101011111111111111101100",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.86", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.83", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup("VGLT", "0.89", longTermBonds())
                        )),
                new Candidate("Tech", "0101011100001000101001011011100100001111101111101100",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.82", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.84", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.81", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.83", consumerBasicSector())
                        )),
                new Candidate("Tech", "0011000100001010001000001001011100001111111101111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.88", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.80", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.80", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.80", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.81", consumerBasicSector()),
                                new StockGroup("VGLT", "0.85", longTermBonds()),
                                new StockGroup(METALS_SECTORS, "0.85", metals())
                        )),
                new Candidate("Finance", "0101011100001000101000010111100100001111111111101111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.83", techSector()),
                                new StockGroup(COMM_SECTOR, "0.82", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.81", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector())
                        )),
                new Candidate("Finance", "0100011100001001101011011111001100011011111111111101",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.83", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.89", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.85", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.87", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector())
                                )),
                new Candidate("Finance", "0100011100001001101000111111001100001001111111111100",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.84", techSector()),
                                new StockGroup(COMM_SECTOR, "0.83", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.89", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.85", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.82", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector())
                        )),
                new Candidate("Finance", "0100011100000001101011011111100100011011101111100100",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.80", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.83", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector())
                        )),
                new Candidate("Communication", "0001010100001010101000000111101100000111111111111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector()),
                                new StockGroup(COMM_SECTOR, "0.83", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.82", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.83", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.82", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup("VGLT", "0.89", longTermBonds())
                        )),
                new Candidate("Communication", "0100011100101101101000101110100100000001111001111111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.87", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.82", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.83", consumerBasicSector()),
                                new StockGroup("VGLT", "0.86", longTermBonds())
                        )),
                new Candidate("Communication", "0110011100101101101000101111100011100111111001111011",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.90", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.87", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.82", consumerBasicSector()),
                                new StockGroup("VGLT", "0.85", longTermBonds())
                        )),
                new Candidate("Communication", "0100011100000100101110101111101100001111111111011110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector()),
                                new StockGroup(COMM_SECTOR, "0.87", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.89", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.84", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup("VGLT", "0.90", longTermBonds())
                        )),
                new Candidate("Health", "0111111000001100011011111111100100001111110101100111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.80", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.85", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.83", consumerBasicSector()),
                                new StockGroup("VGLT", "0.87", longTermBonds())
                        )),
                new Candidate("Health", "1100111100000001101001001111100100001111111111101111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(FINANCE_SECTOR, "0.84", financeSector()),
                                new StockGroup(HEALTH_SECTOR, "0.88", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector())
                        )),
                new Candidate("Consumer disc", "0110011100101101101000001111010100001111111111111011",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.85", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.86", consumerBasicSector()),
                                new StockGroup("TSLA", "0.78", tesla()),
                                new StockGroup("VGLT", "0.85", longTermBonds())
                                )),
                new Candidate("Consumer disc", "0100011100000100101100101000100100000001111101101110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.86", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.83", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.84", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector())
                        )),
                new Candidate("Consumer disc", "0110011100001100101100101001100011100111111101110001",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.89", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.85", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.86", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.83", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector()),
                                new StockGroup(INNOVATION_SECTOR, "0.80", innovationSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup("VGLT", "0.87", longTermBonds())
                        )),
                new Candidate("Consumer basic", "1000010001100011001010111111011100001111111111101110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.87", sectors()),
                                new StockGroup(TECH_SECTOR, "0.83", techSector()),
                                new StockGroup(COMM_SECTOR, "0.87", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.89", healthSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector()),
                                new StockGroup("VGLT", "0.91", longTermBonds())
                        )),
                new Candidate("Consumer basic", "1100111100000001101001001111100100001111111111111110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.86", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.80", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.84", financeSector()),
                                new StockGroup(HEALTH_SECTOR, "0.88", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.81", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.89", consumerBasicSector())
                        )),
                new Candidate("Consumer basic", "0010101001111100010101001111100011111111111111110111",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.87", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.84", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.82", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.86", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.89", consumerBasicSector()),
                                new StockGroup("BGRN", "1.00 (2 ops)", greenBonds())
                        )),
                new Candidate("Consumer basic", "0010101001111100010101011001100100011111111111110110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.92", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.83", financeSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.81", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.84", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.89", consumerBasicSector())
                        )),
                new Candidate("Industrial", "1000111000000011101001011011100100000011111101111110",
                        tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.86", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.84", financeSector()),
                                new StockGroup(HEALTH_SECTOR, "0.82", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.87", industrialSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup("VGLT", "0.86", longTermBonds())
                        )),
                new Candidate("Sectors", "0111111000001100111000111001011100001111111101101111", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.83", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.80", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.84", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.84", industrialSector())
                        )),
                new Candidate("Tech", "1101010100001010101011111101100100101111111101101101", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup(COMM_SECTOR, "0.83", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.86", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup(HEALTH_SECTOR, "0.84", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector())
                        )),
                new Candidate("Comm", "0100011100000100101110101111101100011011111111111101", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.86", sectors()),
                                new StockGroup(TECH_SECTOR, "0.87", techSector()),
                                new StockGroup(COMM_SECTOR, "0.87", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.89", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.83", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.84", industrialSector()),
                                new StockGroup("VGLT", "0.90", longTermBonds())
                        )),
                new Candidate("Finance", "0100011100101001101011011111101100000001111001111111", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.93", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.81", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.89", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.84", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.84", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.83", industrialSector())
                        )),
                new Candidate("Cons Basic", "0010101001111100010101001111101100001111111111011110", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.82", techSector()),
                                new StockGroup(COMM_SECTOR, "0.80", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.82", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.88", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.80", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.85", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.82", industrialSector()),
                                new StockGroup("BGRN", "1.00 (2 ops)", greenBonds())
                        )),
                new Candidate("Cons Disc", "0100011100000100101100101000100100001011111101101100", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.85", techSector()),
                                new StockGroup(COMM_SECTOR, "0.84", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.88", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.85", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.86", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.83", healthSector()),
                                new StockGroup(INDUSTRIAL_SECTOR, "0.85", industrialSector()),
                                new StockGroup("VGLT", "0.87", longTermBonds())
                        )),
                new Candidate("Health", "1000010001100011001000111101000011101111111011101111", tradingDecisionGenerators,
                        asList(new StockGroup(ETF_SECTORS, "0.91", sectors()),
                                new StockGroup(TECH_SECTOR, "0.81", techSector()),
                                new StockGroup(COMM_SECTOR, "0.85", commSector()),
                                new StockGroup(FINANCE_SECTOR, "0.85", financeSector()),
                                new StockGroup(CONS_BASIC_SECTOR, "0.87", consumerBasicSector()),
                                new StockGroup(CONS_DISC_SECTOR, "0.84", consumerDiscSector()),
                                new StockGroup(HEALTH_SECTOR, "0.89", healthSector()),
                                new StockGroup("VGLT", "0.91", longTermBonds())
                        ))
        );
    }
}
