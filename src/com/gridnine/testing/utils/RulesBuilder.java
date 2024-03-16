package com.gridnine.testing.utils;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

public class RulesBuilder {
    private static final int MAX_HOURS = 2;

    public List<Predicate<Flight>> createRules() {
        return List.of(
                flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isBefore(LocalDateTime.now())),

                flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())),

                flight -> {
                    List<Segment> segments = flight.getSegments();
                    Segment segmentPrev = null;
                    Duration landTimeSum = Duration.ZERO;
                    for (Segment segment : segments) {
                        if (segmentPrev != null) {
                            Duration landTime = Duration.between(segmentPrev.getArrivalDate(), segment.getDepartureDate());
                            landTimeSum = landTimeSum.plus(landTime);
                        }
                        segmentPrev = segment;
                    }

                    return landTimeSum.toHours() > MAX_HOURS;
                });
    }
}
