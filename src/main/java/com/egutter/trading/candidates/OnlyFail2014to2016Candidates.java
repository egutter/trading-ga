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
