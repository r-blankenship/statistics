package com.blankenship.statistics;

import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AbstractMontyHall {

    public record BeforeReveal(int correctDoor, int firstChoice) {}
    public record AfterReveal(int correctDoor, int firstChoice, int revealedDoor) {}

    public static int NUM_TRIALS = 10000;

    public static int doorSelector(final Predicate<Integer> doorFilter, final int numDoors) {
        final var potentialDoors = Stream.iterate(0, i -> i < numDoors, i -> i + 1)
            .filter(doorFilter)
            .toList();
        return potentialDoors.get((int) (Math.random() * potentialDoors.size()));
    }

    public static void runSimulation(
        final BiFunction<BeforeReveal, Integer, Boolean> allowedReveal,
        final BiFunction<AfterReveal, Integer, Boolean> allowedFinalChoice,
        final String gameType,
        final int numDoors
    ) {
        var timesCorrect = 0;

        for (int trial = 0; trial < NUM_TRIALS; trial++) {
            final var correctDoor = doorSelector(i -> true, numDoors);
            final var firstChoice = doorSelector(i -> true, numDoors);

            final var beforeReveal = new BeforeReveal(correctDoor, firstChoice);

            final var revealedDoor = doorSelector(i -> allowedReveal.apply(beforeReveal, i), numDoors);

            if (revealedDoor == correctDoor) {
                timesCorrect += 1;
                continue;
            }

            final var afterReveal = new AfterReveal(correctDoor, firstChoice, revealedDoor);
            final var secondChoice = doorSelector(i -> allowedFinalChoice.apply(afterReveal, i), numDoors);

            timesCorrect += secondChoice == correctDoor ? 1 : 0;
        }

        System.out.println("Monty Hall " + gameType + ": " + timesCorrect + " out of " + NUM_TRIALS + " correct. " + (timesCorrect * 100.0 / NUM_TRIALS) + "%");
    }
}
