package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 9/19/16.
 */
public class OnlyFail2014to2016Candidates {
    public static List<Candidate> candidates() {
        return asList(
                new Candidate("OnlyFalls 2014-2016", "0110100010100011010001011100110000111100011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101110010011110001001100011100101000011000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101110010111110101001101001101000110001101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101101111110100101001011101100111100001100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1011100010011111001101000101110101011011110", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101100001111111101001101011101001000010011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1011101101001001010001001010001011011010010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1101011110100101001010001110011011010001100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1001101101001010110001001010001110000000110", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100101101001111001100000110110110111111001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100111001001000001010010111011000101000000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101110010110111101001011111100101000000000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0001111011001110010111100111110111001100100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101111110100101110001010000111010111011011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0001101100101011110110001111100100100010110", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010111011101011010001001010001110100101001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100111111011111010001010111101000001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1110110001110001011110000110000101101011110", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0100010101000100110100101101101001110001110", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100100000100110010001111000101001100110001", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010110011101100101011000110010010101111000", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1000011010101111101111100100100111010010011", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0100101111111111101010100000101000100001010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1111010000110011101001111101111001100110001", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1111111100011000111111011011000111010000000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100101110101101110111110000101000101010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1110111011110101001110001000111001100110001", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0011010101010001010001011110000011001100010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010110101010011111011000010010111010010011", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1111100011110110110001110001001011100011110", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1011110100101100010001111001011000101000000", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101010000101100001100111010010111010010011", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1111011000000100110110101100001110101011111", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101010011001011010011001100011101110111010", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1000110111100011101001110100011010010010001", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0110010101000111010100100010000101001100000", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1011110111011001110001110011110111010110100", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1011100010010100111010000000001101001110001", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101010011100011010010010001010010101111110", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101010010110111011100110100101000011101001", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1101110010101110101010001010101000001010000", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0011100010100100010110111100011001001110001", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0101010111000011001111110000100110000101000", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1110010010011010101010011010111000000100000", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0110000110110110001111001000001010100101001", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010110100101110010001010010100011001001110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1100010100101110111100000101011000001010000", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1110000100011000010111010101100001001111001", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0111100011011000011100100011010100110001111", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010000001110001110100101111111101011111000", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010001001010011001011011011010111010101011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1111110100110010101110101001001001100110001", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "1010100011111111110000001000001111101110000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "0010111110010010110001101000010111110010101", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "101110111000100111000100101000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "101001100111011010100111001000", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111010110001100001011101000010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "100001011000001001000010110110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "110001100000101001001000010110", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "100100101100000011011011110101", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "010110001100000110011011001000", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "000111000010001111010101001111", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "001110011001001110000101110001", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "010110001100000001001010111001", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "010110001011100111001110101110", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "110101010010111101000101011000", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111100011111001011011110110011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "000001101111001101100010000111", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "100101000100011011000101011111", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "101001010000110011011100111000", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "101010001001101000111010001011", asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111110111001111010000110010000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "010110110000111000100110010111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111110111010101101010110011001", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111110111010110011000101110000", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111101110100001001001100110001", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "000010001000111010111101110100", asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "011001111100000001010110000001", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "100010110011011000111110010111", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("OnlyFalls 2014-2016", "111101110011000010111111101000", asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class))
        );
    }
}
