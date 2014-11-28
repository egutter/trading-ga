package com.egutter.trading.genetic;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;

/**
 * Created by egutter on 4/12/14.
 */
public class TraderEvolutionObserver implements IslandEvolutionObserver, EvolutionObserver {

    @Override
    public void populationUpdate(PopulationData data) {
        System.out.println("Number of generation " + data.getGenerationNumber());
    }

    @Override
    public void islandPopulationUpdate(int islandIndex, PopulationData data) {
        System.out.println("Island Number "+ islandIndex + " Number of epoch " + data.getGenerationNumber());
    }
}
