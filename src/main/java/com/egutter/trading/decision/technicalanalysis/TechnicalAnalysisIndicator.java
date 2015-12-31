package com.egutter.trading.decision.technicalanalysis;

import org.joda.time.LocalDate;

import java.util.Optional;

/**
 * Created by egutter on 12/26/15.
 */
public interface TechnicalAnalysisIndicator {
    Optional<Double> getIndexAtDate(LocalDate tradingDate);
}
