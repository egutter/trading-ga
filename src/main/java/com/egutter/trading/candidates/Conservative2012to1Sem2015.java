package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 8/15/15.
 */
public class Conservative2012to1Sem2015 {
    public static List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 2012-1stSem2015", "0011000100111010010101111001001110101101010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110100011001010010011111001011111001010011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011111001010001101111111001111101101010011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110001111000000110011111000000000001110010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011001101101011110010011000110010110000001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011000000110011010100001011101011010110111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1000001110110100010111001011100110011010101", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011001110110101001001110001101000011100111", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0110011110001110010011000010011000110011111", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1010000000110111101100001110011101111101110", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110110010111110110111001011001101110010010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110000001010111101011110110001011101100011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1000000001010010101100110011101101101101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1101001011001011001111110011111100000000011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110011111111010010111100111000110100101110", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110110000011100001101111011110110011010011", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0000011110101100000010111011001101000101111", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110010101111101111000001100001011010001001", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1111101000011100001000100111110111101010110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1111011100111010011011111100001111101011011", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0110010110100110001010101110011011101111010", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011101010100001111110101111100110100100100", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011101011000110001001110000110000110110110", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110010110111101000011010011001011001001001", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0111101011101111010111101110110110111101110", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0110011110011001111010010011110100001100101", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011101011110110000100010011000101001001110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0011011001000100111001000010101101000010111", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1100100110001101001011110001101010011101110", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0010011001010101001101010100111101010110111", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0001101011101110110111001010011011100111011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110011110000000011011010101010101011111100", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0111001100110001101010001101010111101010000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "0001100010010101010100100000110101111011101", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1111011001010101100001011010111010110010100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1011101111011001000011000001000011011010011", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1000011000111000010110110011111101110101101", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1110101111011100101000011111100010110000111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1001011010010101111000010000010111111110100", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "1101101111001100000110101101111100101110101", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "100100000011011011010001101111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "110000000000100000100101111110", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "010110101000111110111101001011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "111110010110111111010000100110", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "000010001110000101011111110110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "001110000011001110101001111011", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "110001110011110111101101100011", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "101001101010010111011101011001", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "110101111010111011101000000011", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "101000100010101100111111111011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "001101111101010101000001100110", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "111101100000000000110110100101", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "111001110101100111101010111110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "101001100001010111010000111011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2012-1stSem2015", "111010111010100100011000100110", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 2012-2015", "1101101001001000001001100100110101110101101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 2012-2015", "010101111001000011101010010011", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-2015", "0100110000110100001101101010110101111010111", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class))
        );
    };
}
