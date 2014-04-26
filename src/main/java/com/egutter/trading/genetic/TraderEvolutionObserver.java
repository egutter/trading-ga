package com.egutter.trading.genetic;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;

/**
 * Created by egutter on 4/12/14.
 */
public class TraderEvolutionObserver implements EvolutionObserver {

    @Override
    public void populationUpdate(PopulationData data) {
        System.out.println("Number of generation " + data.getGenerationNumber());
    }
}
