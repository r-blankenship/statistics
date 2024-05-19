package com.blankenship.statistics;

public class Statistics {
    public static void main(String[] args) {
        runSimulations();
    }

    public static void runSimulations() {
        final var numDoors = 3;

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> i != beforeReveal.correctDoor() && i != beforeReveal.firstChoice(),
            (afterReveal, i) -> i == afterReveal.firstChoice(),
            "with same door", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> i != beforeReveal.correctDoor() && i != beforeReveal.firstChoice(),
            (afterReveal, i) -> i != afterReveal.revealedDoor() && i != afterReveal.firstChoice(),
            "with optimal strategy", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> i != beforeReveal.correctDoor() && i != beforeReveal.firstChoice(),
            (afterReveal, i) -> i != afterReveal.revealedDoor(),
            "with random door", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> i != beforeReveal.correctDoor(),
            (afterReveal, i) -> i != afterReveal.revealedDoor(),
            "first choice can be revealed, but correct door cannot", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> i != beforeReveal.correctDoor(),
            (afterReveal, i) -> i != afterReveal.revealedDoor() && i != afterReveal.firstChoice(),
            "first choice can be revealed, but correct door cannot. Always switch", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> true,
            (afterReveal, i) -> i != afterReveal.revealedDoor(),
            "any door can be revealed", numDoors
        );

        AbstractMontyHall.runSimulation(
            (beforeReveal, i) -> true,
            (afterReveal, i) -> i != afterReveal.revealedDoor() && i != afterReveal.firstChoice(),
            "first choice can be revealed, but always switch", numDoors
        );
    }
}
