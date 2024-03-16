package com.gridnine.testing;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.utils.RulesBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class FlightFilter {
    public List<Flight> filter(List<Flight> flights, Predicate<Flight>... rules) {
        List<Flight> result = new LinkedList<>();

        for (Flight flight : flights) {
            for (Predicate<Flight> rule : rules) {
                if (rule.test(flight)) {
                    break;
                }

                result.add(flight);
            }
        }

        return result;
    }
}
