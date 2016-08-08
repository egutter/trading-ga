package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 8/7/16.
 */
public class BestCandidates {

    private static Set<Candidate> ALL_CANDIDATES = new HashSet<>(candidates());

    public static void main(String[] args) {
        System.out.println("ALL " + ALL_CANDIDATES.size());
        System.out.println("all " + (candidates2014().size() + candidates2015().size()));
    }

    public static List<Candidate> candidates() {
        List<Candidate> all = new ArrayList<>(candidates2015());
        all.addAll(candidates2012());
        all.addAll(candidates2014());
        return all;
    }

    public static List<Candidate> candidates2012() {
        return asList(
                new Candidate("2012-2016", "000100010111111111010001011111", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2012-2016", "1010001001001100011101010001100110101010001", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2012-2016", "010101011110011110100111110110", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2012-2016", "000100110001000100110110011010", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class))
        );
    }
    public static List<Candidate> candidates2014() {
        return asList(
                new Candidate("2014-2016", "1011101111100010010000000001011000101110010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0110001110101010101101110111011001000100100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1100000011111011100111100110110110001001001", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2014-2016", "1011000110101011010001001110011000100111100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0111101000110101001111101100101000101000101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1001010000101011001110101110001001111010100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1111100010110100101100101001000110011101000", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("2014-2016", "1011011010110111000000100101001000100111101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1010100010010001100101111110001000000101011", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0111011000101111000010010111011000101110100", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0011011101010111101000111010101001101101111", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0110010011100111010110110011111001001111101", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1001011011110011010000110101111001001100101", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1001100010101101001100100110010010001100011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1010000101110011110010000100001000000111100", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1011001000100010110100011100011001000110100", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1000011101100011101101100111101000101001101", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "0111100111100101011010000001011000101011100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1111110000101001010000010100110011111000111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "1100100101010100010000110101101000100001100", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "100001010000101101000001011100", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "111101110111011011000101101100", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "010010001001011001000101100101", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "011010101000010101000001011101", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2014-2016", "101001100100110011000110101101", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class))
        );
    }
    public static List<Candidate> candidates2015() {
        return asList(
                new Candidate("2015-2016", "1011000110100011001111010101111001000111101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1101011011111111110101011010011000010100101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1100010001101011001110001101001001101011101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0111011100000110010011000110111001101011101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1100111000010000010000010110101010001011100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1011110100110010010010110100011010001111100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1101101001010010110010101011010101000101101", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2015-2016", "0001111000100011110000111001001001101111111", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2015-2016", "0001011001010110010001101000111001001110110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2015-2016", "1000000011000101101011101110001000101100101", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0100100100000111000101101010111110100010010", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("2015-2016", "0101011000100101001100111100111110000110010", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("2015-2016", "0011101111001011001101010101011000000111101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1011100100100111001100100001011010001010101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1111011100111110010000110010011000110010100", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1101100100100010110000011010101001000111100", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1111101100010111110001001000111001101010010", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0011111110100110001110101000111000011001101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0101011000010101011011100100001010100000111", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110110011000011010111000001010111111000100", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("2015-2016", "1110111010100101101101011001111000001010100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1000100001010111111100001101001010001110101", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0100101000110111000111110001101001111101111", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1100011000001111001000111101011001001010011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1011010011000101111001011101101000000100011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1001100101010110111100111011001010001101100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1010100111000110101010101101011010000110100", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110101110100010101111011100011001001111101", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110101001111011101101100100011001000101111", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0110010100100011111000111000100110011001111", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("2015-2016", "1100000100100110110000010111011000001100100", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1011110010111000110001011000101010001001101", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0111010110011011010010010111011001000110110", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1111001101100011110011111001000110110010000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2015-2016", "0001011001000000010101111111001000100111101", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0000100110110101101111100001001000011100100", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1001100111100010011011111011011010000111100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110011010100100111110111111001001101110100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1001010111010000111011100100001000101000101", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0110100001000110010011100111001001001101110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0011100001000011010100111000101001101101111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0000011101010101110011011111011000011101101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1100101100011001011110110011001000101101101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1111100001101011011101001000101001001100110", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1010011110011001000110110111001001100110110", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1000011110100110010010111010001010000111100", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110101000111000011001100001011010000101100", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1101100101110001100011000011011010001011010", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "0110101011000110110001011101111001101000111", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1101101101000000101100100001101000000101100", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "1110010110101110101001011110011000101011101", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "110110011100100101011111000111", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("2015-2016", "011010100100011110010110000111", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("2015-2016", "001110001010000101110010001011", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "000101011011101011000110010101", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "110001101111011111010001101010", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "101001110010100001001000111101", asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "010010111101111110110001010111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("2015-2016", "001101101111010101001101010110", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "100110100101000111010100000111", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "100101111001001011001001111100", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "101101111011011001010011101100", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "100001101010101011000010110101", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "110001111011010011000001101000", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("2015-2016", "110001100010000001001000111101", asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class))
        );
    }
}
