package de.ahofbauer.quicktipgenerator.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Contains a set of static functions to be used in unit tests.
 */
class TestHelperFunctions {

    private TestHelperFunctions() {
        // Static function container should not be instanced
    }

    /**
     * Converts an array to a array list.
     */
    static <T> List<T> toList(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    /**
     * Asserts that the given tip row has the expected name and numbers
     */
    static void assertTipRow(TipRow.TipRowPart tipRowPart, String expectedName, Integer... numbers) {
        assertThat(tipRowPart.getNumbers(), is(toList(numbers)));
        assertThat(tipRowPart.getName(), is(expectedName));
    }

}
