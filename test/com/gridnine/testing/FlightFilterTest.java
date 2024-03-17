package com.gridnine.testing;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlightFilterTest {

    private static final int MAX_HOURS = 2;

    @Test
    @DisplayName("Return filtered flights when using a valid initial flight and a single rule")
    void filter_returnFilteredFlights_WhenUsedValidInitialFlightAndOneRule() {
        List<Flight> flightsInit = createFlightsInit();
        List<Flight> expected = createFlightsExpected();
        FlightFilter flightFilter = new FlightFilter();
        List<Predicate<Flight>> rules = createRules();
        List<Flight> actual = flightFilter.filter(flightsInit, rules.get(0));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Returns an unchanged list when the filtering rules are not met")
    void filter_returnsUnchangedList_whenFilteringRulesNotMet() {
        LocalDateTime dtNow = LocalDateTime.now();
        List<Flight> flightsInit = List.of(
                new Flight(
                        List.of(new Segment(dtNow.minusHours(10), dtNow.minusHours(9)))),
                new Flight(
                        List.of(new Segment(dtNow.plusHours(1), dtNow.plusHours(2)))));

        FlightFilter flightFilter = new FlightFilter();
        List<Predicate<Flight>> rules = createRules();
        List<Flight> actual = flightFilter.filter(flightsInit, rules.get(1));
        Assertions.assertEquals(flightsInit, actual);
    }

    @Test
    @DisplayName("Returns an empty collection when the input collection is empty")
    void filter_returnsEmptyList_whenInputListIsEmpty() {
        List<Flight> flightsInit = new ArrayList<>();
        List<Flight> expected = new ArrayList<>();
        FlightFilter flightFilter = new FlightFilter();
        List<Predicate<Flight>> rules = createRules();
        List<Flight> actual = flightFilter.filter(flightsInit, rules.get(0));
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Throws a NullPointerException if the input list is null")
    void filter_throwsNullPointerExceptionIfInputListIsNull() {
        List<Flight> flightsInit = null;
        FlightFilter flightFilter = new FlightFilter();
        List<Predicate<Flight>> rules = createRules();
        Assertions.assertThrows(NullPointerException.class, () -> flightFilter.filter(flightsInit, rules.get(0)));
    }

    @Test
    @DisplayName("Throws a NullPointerException if the rules is null")
    void filter_throwsNullPointerExceptionIfRulesIsNull() {
        List<Flight> flightsInit = createFlightsInit();
        FlightFilter flightFilter = new FlightFilter();
        List<Predicate<Flight>> rules = null;
        Assertions.assertThrows(NullPointerException.class, () -> flightFilter.filter(flightsInit, rules.get(0)));
    }

    private List<Flight> createFlightsInit() {
        LocalDateTime dtNow = LocalDateTime.now();
        return List.of(
                new Flight(
                        List.of(
                                new Segment(dtNow.minusHours(10), dtNow.minusHours(9)),
                                new Segment(dtNow.minusHours(2), dtNow.minusHours(3)),
                                new Segment(dtNow.minusHours(4), dtNow.minusHours(3))
                        )
                ),
                new Flight(
                        List.of(
                                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                                new Segment(dtNow.plusHours(2), dtNow.plusHours(3)),
                                new Segment(dtNow.plusHours(3), dtNow.plusHours(4))
                        )
                )
        );
    }

    private List<Flight> createFlightsExpected() {
        LocalDateTime dtNow = LocalDateTime.now();
        return List.of(
                new Flight(
                        List.of(
                                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                                new Segment(dtNow.plusHours(2), dtNow.plusHours(3)),
                                new Segment(dtNow.plusHours(3), dtNow.plusHours(4))
                        )
                )
        );
    }

    private List<Predicate<Flight>> createRules() {
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
