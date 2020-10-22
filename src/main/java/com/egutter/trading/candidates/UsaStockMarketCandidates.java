package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;
import com.egutter.trading.stock.StockGroup;

import java.util.List;

import static com.egutter.trading.stock.StockMarket.*;
import static java.util.Arrays.asList;

public class UsaStockMarketCandidates {
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
