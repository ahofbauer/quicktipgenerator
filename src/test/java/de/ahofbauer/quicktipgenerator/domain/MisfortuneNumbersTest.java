package de.ahofbauer.quicktipgenerator.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

class MisfortuneNumbersTest {

    @Test
    void testChangingTheStateShouldNotBePossibleFromOutsideByConstructor() {
        // Create MisfortuneNumbers instance from a list of numbers
        List<Integer> numbers = toList(1, 2, 3);
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbers(numbers);
        // Remove an item from the list
        numbers.remove(2);
        // Assert that the list inside of the MisfortuneNumbers has not been altered
        assertThat(misfortuneNumbers.getNumbers(), hasSize(3));
    }

    @Test
    void testChangingTheStateShouldNotBePossibleFromOutsideByGetter() {
        // Create MisfortuneNumbers instance from a list of numbers
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbers(toList(1, 2, 3));
        // Remove an item from the list using the getter
        misfortuneNumbers.getNumbers().remove(2);
        // Assert that the list inside of the MisfortuneNumbers has not been altered
        assertThat(misfortuneNumbers.getNumbers(), hasSize(3));
    }

    @Test
    void testIsMisfortuneNumber() {
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbers(toList(2, 4, 6));

        assertThat(misfortuneNumbers.isMisfortuneNumber(1), is(false));
        assertThat(misfortuneNumbers.isMisfortuneNumber(2), is(true));
    }


}