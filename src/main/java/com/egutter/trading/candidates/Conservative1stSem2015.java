package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 7/30/15.
 */
public class Conservative1stSem2015 {

    public static List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 1stSem 2015", "0111111110001111110100001000000010100010010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1101000000100110001100100001001100110110001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100100011110101100101110000101111000110", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110100100001010001100110000110001000111000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100110010101001100100110101111001010010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111001100110001001100101110010110101000001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110100100110011101100101000110011010010110", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111010111101011001100101000000110111101100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1101000110101111101100100100110001001010001", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1001111100101000101110100110010111000111111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110100010000000110100010010001001001111000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110110100010111101100000100110110110001111", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111001110011011100010100100000110011010101", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111111000110010111101010110000110000001101", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0100010011111110001001101101011011001110000", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1101011010101100000101011101001010101101010", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110001111000101110100000001110111010011011", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1011011111011001010101101011100111010111100", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1000110010011010111101010001010110001110001", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0101110001001111111000101101110110111000000", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1011011001111010001000001110111001101010100", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110001101101110100101011110101001001111000", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111001100001101101100011111010010000110000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111010111011101101011100111101101101111010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1011011100011100000110101011111001101110000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111000101110101001011100000110110010110100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110111111111111001100100101011000101101110", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111100111011100101100100001010010111100000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110011011010010010011101111111001001111000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110110100010101001010110001111001001111000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1101011011010101010001011111111010010000000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1100011110001100011110101111011010000100001", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0100011100100101000000101001010110011001001", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0011110001101111001000101111110110110100000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0011011101000110001010111110101001101010100", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1100011001011101001010001110101000101110000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1011000111100011101110101011000110100010100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0101101001000100110001001010011011111010011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110011000100101101110100000110110101011111", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1100010001100101001110011110101100110011110", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0000001110000101101110101110011010001111100", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110101010100011101001001100011001101001011", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0011101001011101010110100110101111001101110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111011001000100010011111010110010000000111", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110110000101111100101101001010011110111101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110101000111000010011100011110001001111100", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100011010011000100001101100100101110101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111100101011111110100110000110101001111101", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0011101000001101010101001111001100110111110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1100011001010101100101100010101011000010110", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110100001101011011011110000001011101111011", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111011000100111100001001110101100101111000", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110101111001011000011111011000100100101011", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1011110000110011111101010101110111111101000", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1000100110011100011110000101111111111000001", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0011101000001101100100010011001011010101001", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0011101000100001100010110111011101100111100", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1011101000100000011111010000011010100000010", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110110010111110101010000011111001001011000", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111100110011000011010110110101010100110111", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100001010011100011101110010101011000100", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0011101001110001110101000101111011010000001", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100001010011100011101110010101011000100", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0011101000011101011001100101100001100001111", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110101000011101011110110001001110000010111", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111011001011101011100111001100001101001110", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110101001001000000111000101011110101111100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1001010111111011101100101111011011011111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1101000101000110101100100100111001001011000", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0010101001000100010001111000001000011110011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111100110000000011100000000101001001100000", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1100011001001101110001010111100001011100101", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0001001101110110011000011001000111011110011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111100011011100111101011011111010000111011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0110001110111011010101000011000111111001011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1001100110100000111100001011110111011011011", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110000101001000111000100101000100100011010", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110110110100111001100110111011000101100110", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110010101010010000101010110011001001011000", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1101010110011101100011100111101001001111000", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0001001111111000011000111111001001101100001", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1010001010011010110000100011101001101001000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0011010011001111001111101011110000101001111", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0011100010000011010110100010000100101110010", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110011111011111101001101001001001001111000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111001100011010010011100101001010101001011", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1001110010100000110100000110011010010101010", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1101110101100111001111100100101011001010101", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1111010110011111001000111001111010001001011", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0000010000011100111111001001101001100101100", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110100010010110010110100110101001001111000", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0000010000111000111010010000101001001110100", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0110011101011010100110010010110111111000011", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111101100010110001100111001110101010001101", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110011000000010100110011110110010111001010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110011001000010100110000011000111110110111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110010011000111101000101011001001001111000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110010110101111100000100101001001001111000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1101110101001110001111100110100101100101111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1111101111001010001010101011011101111000111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1000110000010111101101001101011111110110111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110011001100111111011010001011001001111000", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1101100010101111100101110000101001001111000", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110001001001100001110000000011001001010000", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1001100111001010010100001010011010100100101", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "0110100001111000101111110110111000110010011", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1000011101000010101010110001101001101110101", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1000101100110011101110111000111100101110111", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "0000100001010010001101111011111001010001101", asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1110010100111101001110010101101001001111000", asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "1110111101001110001101010100011001001100000", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "1101011111100101010101101101100011010101110", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "001111000000000111010000111010", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "000111110001100110111001110011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110010111011111010100101100010", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "000010110110000100100111111101", asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111101101110100100110001111101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111100101001011110110010000101", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110101111000101111000100000010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "110101111011010011000100000010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "011101110100000011010001101011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "110110100100010101000111111011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "010010001100001110110011010001", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "110100011101011000101110010010", asList(MovingAverageConvergenceDivergenceGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111110111010001010011100110010", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111110111010111010100010001000", asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111101100100000000110010100101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111101100101110000110010011101", asList(RelativeStrengthIndexGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111101100101010001110000110101", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111101100100000001101101111011", asList(RelativeStrengthIndexGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111110101000101110101010010100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "001110100100000111011001100001", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "001110100001110100001111111100", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "001110100010000111110001000011", asList(RelativeStrengthIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111101001110101111011001111100", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "011010100001110101110000010111", asList(RelativeStrengthIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111000010100111110110001000101", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111000010000001110110000010101", asList(AverageDirectionalIndexGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "000100111010001001011001101000", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111100111111001001010110000001", asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110001000001010011010101101011", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111001011011101011010001111001", asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110111010011001010110101100010", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "010000110011101110110110111000", asList(AverageDirectionalIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110101100010101001001001111000", asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111001101010011001001001010100", asList(AverageDirectionalIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "000110111011011101110101011000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111101100110111100111010011111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111110111101000110101010000100", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "100101100001010010110011000100", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111001100101101011011111111111", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111001100110011010101001011111", asList(BollingerBandsGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111010111100111111001001111000", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111010000101011111001001111000", asList(BollingerBandsGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111111000001000111000111101101", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "011010101101110000111110100011", asList(AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "001000111110001110110110000001", asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "001111111101011100110110101001", asList(AroonOscilatorGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110101011111111001000011100101", asList(AroonOscilatorGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "110110011010010001000110100010", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "100110100011100011010000010010", asList(MoneyFlowIndexGenerator.class, UltimateOscillatorGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111001101101011111011001101101", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111101100011100111001101111000", asList(MoneyFlowIndexGenerator.class, WilliamsRGenerator.class)),

                new Candidate("Conservative 1stSem 2015", "111001001011010011001010000100", asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class)),
                new Candidate("Conservative 1stSem 2015", "111001001100110011001001111000", asList(UltimateOscillatorGenerator.class, WilliamsRGenerator.class))
        );
    }
}
