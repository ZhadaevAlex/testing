package com.gridnine.testing;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.entity.Segment;
import com.gridnine.testing.utils.RulesBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class RulesBuilderTest {

    @Test
    void createRules_createdRule1IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightForTrue();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertTrue(rules.get(0).test(flight));
    }

    @Test
    void createRules_createdRule2IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightForTrue();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertTrue(rules.get(1).test(flight));
    }

    @Test
    void createRules_createdRule3IsCorrectWhenConditionIsTrue() {
        Flight flight = createFlightForTrue();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertTrue(rules.get(2).test(flight));
    }

    @Test
    void createRules_createdRule1IsCorrectWhenConditionIsFalse() {
        Flight flight = createFlightForFalse();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertFalse(rules.get(0).test(flight));
    }

    @Test
    void createRules_createdRule2IsCorrectWhenConditionIsFalse() {
        Flight flight = createFlightForFalse();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertFalse(rules.get(1).test(flight));
    }

    @Test
    void createRules_createdRule3IsCorrectWhenConditionIsFalse() {
        Flight flight = createFlightForFalse();
        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        assertFalse(rules.get(2).test(flight));
    }

    private Flight createFlightForTrue() {
        LocalDateTime dtNow = LocalDateTime.now();
        List<Segment> segments = List.of(
                new Segment(dtNow.minusHours(10), dtNow.minusHours(9)),
                new Segment(dtNow.minusHours(2), dtNow.minusHours(3)),
                new Segment(dtNow.minusHours(4), dtNow.minusHours(3))
        );

        return new Flight(segments);
    }

    private Flight createFlightForFalse() {
        LocalDateTime dtNow = LocalDateTime.now();
        List<Segment> segments = List.of(
                new Segment(dtNow.plusHours(1), dtNow.plusHours(2)),
                new Segment(dtNow.plusHours(2), dtNow.plusHours(3)),
                new Segment(dtNow.plusHours(3), dtNow.plusHours(4))
        );

        return new Flight(segments);
    }
}
