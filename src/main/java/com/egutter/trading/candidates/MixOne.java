package com.egutter.trading.candidates;

import com.egutter.trading.decision.Candidate;
import com.egutter.trading.decision.generator.*;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by egutter on 7/30/15.
 */
public class MixOne {
    public static List<Candidate> candidates() {
        return asList(
                new Candidate("Conservative 2012-2014", "110011100011001000111101100101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "100000100000001110101010011101", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "110100011001000010110010010100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010100010011110110100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110010011111001010011010111100", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "011100111001111110100110101011", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111000101001000000111011010100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "101111010110010000110110001111", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",      "100011101011010111000010110101", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110001100010101100100010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "110000010111101100010010000100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "000001011001010110000001110011", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "110001100001001100000110111100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "010101100001100110000110101100", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "110001011100010011100001011000", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111110110111011000100110010010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111110110000101000101001111010", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "110001110101011110010010011100", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101110101101010000110010011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100111001111110010111110", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "111010101001011100000110100011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "101110011011001100010010110101", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014", "111001100000101100111111101000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2012-2014", "100100100010000000111111101110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",      "110000100001010101000101111010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",      "110001100010100011000001010010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "111001100000011010100010100101", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",   "011101011001000100000110010001", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",   "010000101010111110110110001110", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "000111010111001010000010111010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",  "000100001100101110100011000010", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2012-2014",   "010100111000100110110010100100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2012-2014",   "111010101101110010110110011011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",        "101011111111001111011100000011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",        "101111100111011100110011110011", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "101101100001111000100010101010", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "110000011101000011101101111000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "110001100011000111111001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "000010011111110010100001010000", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("90 over market 2013-2014", "101111001010000101011100111100", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014", "00100101011111111", asList(MovingAverageConvergenceDivergenceGenerator.class)),

                new Candidate("Conservative 2012-2014", "11110111110010011", asList(BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",      "00000110001011001", asList(BollingerBandsGenerator.class)),
                new Candidate("Conservative 2014",      "10010110001100111", asList(BollingerBandsGenerator.class)),

                new Candidate("Conservative 2014",      "00000111011110101", asList(RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",      "00000111011110110", asList(RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2014",    "10011000000011101", asList(MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",    "10001000001011101", asList(MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "110001111110010111010001000011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "111101100100101110101110100111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2014",        "101101100011011110101001110111", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100110011110000010111011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "001101100011011110011001110011", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "101101101001101110000110110101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "111001001101110111010101010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2014",        "101010111100100100011010010111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative lastQ2014",   "001110111000001000100010110100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100100101110011110101111", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101100110001110001010010011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "010001100011011110010001101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100101011110011110011011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "010001100100001110010101101001", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "110001101110111110000110000100", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("Conservative 2012-2014",   "111101110101010110011111010010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2014",        "100101100100111110100110011010", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101011100000101000111110111", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Less Consv 2013-2014",     "111101001100101101000011100100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv LogE 2013-2014",     "100001000011110001000010101110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "011101000101110011000011010110", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Consv Log10 2013-2014",    "100101001010110111001100000100", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("90 over market 2013-2014", "101010111101000100100111111000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2012-2014", "11110111110100110", asList(AroonOscilatorGenerator.class)),


                new Candidate("3 Decisions <75 filter 2013-2014", "1100001111000100101010011001010010010110100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1011011000111000100011010110000000010011110",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100011000101110100011000010000011010001100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011001000111100101101010110001110101101",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011000101111100011010000100100010010011",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1110011001101011100010001011010001110010011",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1111100010111111100010100100000011111111111",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100111101000001101100010101000100010001100",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100000101001111101100010110000100010001100",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions <75 filter 2013-2014", "1100000101001000001100001100110000101011000",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("90 over market 2013-2014", "1011101000000001001111000101110011110110101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100010101101111111011011011000100010010100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("90 over market 2013-2014", "1011001100100010110001010100010100110110101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100011011001011100100000111010001110011100",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011011010001011101000000011000011010101101",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1100011011000011100101001011000000110011100",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1111111001111100001110101001010100001000100",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011011010010111111111101001110001010101101",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("90 over market 2013-2014", "1011101111101101010110000111100010110000101",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),

                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011110101100101001110111101110000110110101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110010111110000010001101000100100010000011",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011000011010001110011010011110100010101101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110011001001001001000100010110110111110101",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1101011010100011111010010010110001110101101",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1111011010010011111011110000100000101111011",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1100110010111010101100000111110001110110100",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1110011011111011111000001100000000110101101",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011011011110011110111000101010100010110100",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("3 Decisions Cons w/Commision 2013-2014", "1011001001010100001011111011101100000010000",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Cons w/Commision 2013-2014", "111100011111010000111110100000", asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111100010110010100111110101101", asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "101101101000011110001010101101", asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111001101010001110000110101011", asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "111110010011011100001010110011", asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "101101011111110101100010000111", asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "001101011111111101100001110000", asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Cons w/Commision 2013-2014", "110101011110001111001110100000", asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class)),

                new Candidate("Conservative 2015", "1101000111100111101100100111001110011100100",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111000110001110101100101110001011101001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "0011101001000010110110011100000110111011101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "0001110111111100110110011010000010000111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111111001111000000110010010101001101111000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1101000010100111000110010100010111110101010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "0110110001111001101010101001011100010100110",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "0001111011111001110100101010000011110011000",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111100110111001111101011011100111111110001",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110001010011011110010011111110111010010011",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "0001110001011110011010010010100110010101011",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110110000111110001000110100010101101011001",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111110111101101101100101000001110111111001",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111111001000110101100100000000001111000011",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110011000100010111010100100001111101000000",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1000110000001111100001101100110011001101001",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110001001111011101001101010100110010001000",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "0101011000001110000010110100100110011000000",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "1101110000101000101100001110110011010000100",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2015", "111110110100111110100110110010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "111111100111011010100110011010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "110101100001011010001000000111",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "111101100010111010001010111111",
                        asList(BollingerBandsGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "111101100100000001001100111011",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "111110111011111010100001010000",
                        asList(RelativeStrengthIndexGenerator.class, AverageDirectionalIndexGenerator.class)),
                new Candidate("Conservative 2015", "111101101011110111001100101000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "000100111000010001001000111000",
                        asList(AverageDirectionalIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "101100111001101001011010000000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2015", "000100111110011001011001100000",
                        asList(AverageDirectionalIndexGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2015", "01100100101101111",
                        asList(AverageDirectionalIndexGenerator.class)),

                new Candidate("Conservative 2015", "1101001111111011001001000100101011101101000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111011011001011101001100010110111010111010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110011100111010011010101000111001101001000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1100101101001111101010100110110110111001010",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "0000101011011101000011010100000111010100011",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1111001110101111011011010000110110010001000",
                        asList(BollingerBandsGenerator.class, AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "0011110011100001010100010000111100000001110",
                        asList(AroonOscilatorGenerator.class, RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "1101101110010100000100100011011011101100000",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "0001001101011010111110100010110111001111011",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "1110011001011110101000100011000111010111100",
                        asList(BollingerBandsGenerator.class, MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2015", "001110010010011111010001011000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "110001111000011001001101110000",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "111111000101010110110010110100",
                        asList(BollingerBandsGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "111110011011001110110010011000",
                        asList(BollingerBandsGenerator.class, RelativeStrengthIndexGenerator.class)),
                new Candidate("Conservative 2015", "110001011011000111100101110110",
                        asList(RelativeStrengthIndexGenerator.class, MoneyFlowIndexGenerator.class)),
                new Candidate("Conservative 2015", "111101100000111100110010100101",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, BollingerBandsGenerator.class)),
                new Candidate("Conservative 2015", "110101000011011001000111001010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class)),
                new Candidate("Conservative 2015", "110101000011011001000111001010",
                        asList(MovingAverageConvergenceDivergenceGenerator.class, AroonOscilatorGenerator.class))
        );
    }
}