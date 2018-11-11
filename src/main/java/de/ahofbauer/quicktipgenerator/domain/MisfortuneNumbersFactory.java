package de.ahofbauer.quicktipgenerator.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * This is the factory for the domain object MisfortuneNumbers. It allows to create a new MisfortuneNumbers instance and
 * will take care that it is not possible to create an invalid instance. A <code>ValidationException</code> will be
 * thrown in case of the attempt to create an invalid instance.
 */
public class MisfortuneNumbersFactory {

    public static final int MAX_AMOUNT_OF_NUMBERS = 6;
    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 50;

    /**
     * Creates a new MisfortuneNumbers instance for the given numbers. Will throw a <code>ValidationException</code> if
     * a number is out of range or if the list is to long.
     * <ul>
     * <li>A number must be between 1 and 50</li>
     * <li>A maximum of 6 numbers is allowed</li>
     * </ul>
     */
    public MisfortuneNumbers createNewInstance(List<Integer> numbers) {
        List<Integer> numbersToCheck = nullToEmpty(numbers);

        List<String> errorMessages = new ArrayList<>();

        validateNumberRange(numbersToCheck, errorMessages);
        validateListSize(numbersToCheck, errorMessages);

        if (errorMessages.size() > 0) {
            throw new ValidationException(errorMessages);
        }

        return new MisfortuneNumbers(numbersToCheck);
    }

    private List<Integer> nullToEmpty(List<Integer> numbers) {
        return numbers == null ? Collections.emptyList() : numbers;
    }

    private void validateListSize(List<Integer> numbersToCheck, List<String> errorMessages) {
        if (numbersToCheck.size() > MAX_AMOUNT_OF_NUMBERS) {
            errorMessages.add("Es sind maximal sechs Unglückszahlen erlaubt.");
        }
    }

    private void validateNumberRange(List<Integer> numbersToCheck, List<String> errorMessages) {
        String invalidNumbersString = numbersToCheck.stream()
                .filter(n -> (n < MIN_NUMBER) || (n > MAX_NUMBER))
                .map(String::valueOf)
                .collect(joining(", "));
        if (invalidNumbersString.length() > 0) {
            errorMessages.add("Unglückzahlen müssen zwischen 1 und 50 liegen: " + invalidNumbersString);
        }
    }

}
