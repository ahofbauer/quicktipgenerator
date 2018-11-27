package de.ahofbauer.quicktipgenerator.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * A series of numbers which should not be part of any generated tip row because the user relates these numbers with
 * bad luck.
 */
public class MisfortuneNumbers {

    private final List<Integer> numbers;

    /**
     * Package private constructor. This constructor is only allowed for unit tests in the domain package or for the
     * <code>MisfortuneNumbersFactory</code>.
     * To create a new instance use <code>MisfortuneNumbersFactory</code>.
     */
    MisfortuneNumbers(List<Integer> numbers) {
        // Copy the list, so that you are not able to alter the state from outside of the class
        this.numbers = new ArrayList<>(numbers);
    }

    public List<Integer> getNumbers() {
        // Copy the list, so that you are not able to alter the state from outside of the class
        return new ArrayList<>(numbers);
    }

    /**
     * Checks if the given number is part of the misfortune numbers.
     */
    public boolean isMisfortuneNumber(int number) {
        return numbers.contains(number);
    }

    /**
     * @return <code>true</code> if the object does not contain any number.
     */
    public boolean isEmpty() {
        return numbers.isEmpty();
    }

    @Override
    public String toString() {
        return numbers.stream().map(String::valueOf).collect(joining(" "));
    }
}
