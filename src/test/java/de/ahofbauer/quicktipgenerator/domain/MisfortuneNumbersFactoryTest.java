package de.ahofbauer.quicktipgenerator.domain;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MisfortuneNumbersFactoryTest {

    private MisfortuneNumbersFactory factory = new MisfortuneNumbersFactory();

    @Test
    void shouldCreateAnInstanceForAnEmptyList() {
        MisfortuneNumbers newInstance = factory.createNewInstance(Collections.emptyList());

        assertThat(newInstance, is(not(nullValue())));
        assertThat(newInstance.getNumbers(), hasSize(0));
    }

    @Test
    void shouldCreateAnInstanceForNull() {
        MisfortuneNumbers newInstance = factory.createNewInstance(null);

        assertThat(newInstance, is(not(nullValue())));
        assertThat(newInstance.getNumbers(), hasSize(0));
    }

    @Test
    void shouldCreateAnInstanceForValidNumbers() {
        MisfortuneNumbers newInstance = factory.createNewInstance(toList(3, 7, 13, 23, 33, 47));

        assertThat(newInstance, is(not(nullValue())));
        assertThat(newInstance.getNumbers(), is(toList(3, 7, 13, 23, 33, 47)));
    }

    @Test
    void shouldThrowValidationExceptionForSevenNumbers() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            factory.createNewInstance(toList(1, 2, 3, 4, 5, 6, 7));
        });

        assertThat(exception.getErrorMessages(), is(toList("Es sind maximal sechs Unglückszahlen erlaubt.")));
    }

    @Test
    void shouldThrowValidationExceptionForNumbersOutOfRange() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            factory.createNewInstance(toList(1, 2, -3, 4, 51, 6));
        });

        assertThat(exception.getErrorMessages(), is(toList("Unglückzahlen müssen zwischen 1 und 50 liegen: -3, 51")));
    }

    @Test
    void shouldThrowValidationExceptionForNumbersOutOfRangeAndSevenNumbers() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            factory.createNewInstance(toList(1, 2, -3, 4, 51, 6, 7));
        });

        assertThat(exception.getErrorMessages(), is(toList(
                "Unglückzahlen müssen zwischen 1 und 50 liegen: -3, 51",
                "Es sind maximal sechs Unglückszahlen erlaubt."
        )));
    }

    @Test
    void validateShouldReturnErrorForSevenNumbers() {
        assertThat(factory.validate(toList(1, 2, 3, 4, 5, 6, 7)),
                is(toList("Es sind maximal sechs Unglückszahlen erlaubt.")));
    }

    @Test
    void validateShouldReturnErrorForNumbersOutOfRange() {
        assertThat(factory.validate(toList(1, 2, -3, 4, 51, 6)),
                is(toList("Unglückzahlen müssen zwischen 1 und 50 liegen: -3, 51")));
    }


}