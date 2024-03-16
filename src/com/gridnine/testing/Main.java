package com.gridnine.testing;

import com.gridnine.testing.entity.Flight;
import com.gridnine.testing.utils.FlightBuilder;
import com.gridnine.testing.utils.RulesBuilder;

import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        System.out.printf("Initial flights: \t\t%s%n", flights);

        RulesBuilder rulesBuilder = new RulesBuilder();
        List<Predicate<Flight>> rules = rulesBuilder.createRules();

        FlightFilter flightFilter = new FlightFilter();

        List<Flight> filtered1 = flightFilter.filter(flights, rules.get(0));
        System.out.printf("Filtered flights 1: \t%s%n", filtered1);

        List<Flight> filtered2 = flightFilter.filter(flights, rules.get(1));
        System.out.printf("Filtered flights 2: \t%s%n", filtered2);

        List<Flight> filtered3 = flightFilter.filter(flights, rules.get(2));
        System.out.printf("Filtered flights 3: \t%s%n", filtered3);
    }
}
