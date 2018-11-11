package de.ahofbauer.quicktipgenerator.domain;


import java.util.*;

/**
 * Generates a list of non repeating random numbers based on the given parameters.
 */
class RandomNumberGenerator {

    // Initializing random as member will enable to mock it in the unit test
    private Random random = new Random();

    /**
     * Generates a list of non repeating random numbers in the given range avoiding the given misfortune numbers
     *
     * @param amountOfNumbers   How many numbers should be generated
     * @param maxNumber         The numbers will be generated from one to the given max number
     * @param misfortuneNumbers The list of misfortune numbers which should not be generated
     * @return The list of generated non repeating random numbers
     */
    List<Integer> generateNumbers(int amountOfNumbers, int maxNumber, MisfortuneNumbers misfortuneNumbers) {
        // Setup the source for random numbers
        PrimitiveIterator.OfInt numberIterator = initNumberIterator(maxNumber);

        // Use a set, so that there are no duplicate numbers
        Set<Integer> numbers = new HashSet<>();
        // Loop until we have enough numbers
        while (numbers.size() < amountOfNumbers) {
            int number = numberIterator.nextInt();
            // Do not add a misfortune number
            if (!misfortuneNumbers.isMisfortuneNumber(number)) {
                numbers.add(number);
            }
        }

        return new ArrayList<>(numbers);
    }

    private PrimitiveIterator.OfInt initNumberIterator(int maxNumber) {
        return random.ints(1, maxNumber + 1).iterator();
    }

}
