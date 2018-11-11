package de.ahofbauer.quicktipgenerator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RandomNumberGeneratorTest {

    private static final int AMOUNT_OF_NUMBERS = 5;
    private static final int MAX_NUMBER = 10;
    private static final MisfortuneNumbers MISFORTUNE_NUMBERS = new MisfortuneNumbers(toList(7, 13));

    @Mock
    private Random randomMock;

    @InjectMocks
    private RandomNumberGenerator randomNumberGenerator;

    @Test
    void shouldGenerateTheCorrectAmountOfNumbers() {
        mockRandomWithNumbers(1, 2, 3, 4, 5);

        List<Integer> numbers = randomNumberGenerator.generateNumbers(AMOUNT_OF_NUMBERS, MAX_NUMBER, MISFORTUNE_NUMBERS);

        assertThat(numbers, hasSize(5));
    }

    private void mockRandomWithNumbers(int... ints) {
        // This implicitly verifies that the IntStream is generated with the correct range
        given(randomMock.ints(1, MAX_NUMBER + 1)).willReturn(IntStream.of(ints));
    }

    @Test
    void shouldNotGenerateTheSameNumbersMultipleTimes() {
        mockRandomWithNumbers(1, 2, 3, 4, 5);

        List<Integer> numbers = randomNumberGenerator.generateNumbers(AMOUNT_OF_NUMBERS, MAX_NUMBER, MISFORTUNE_NUMBERS);

        assertThat(numbers, is(toList(1, 2, 3, 4, 5)));
    }

    @Test
    void shouldNotGenerateOneOfTheMisfortuneNumbers() {
        mockRandomWithNumbers(1, 7, 2, 13, 13, 3, 7, 4, 5);

        List<Integer> numbers = randomNumberGenerator.generateNumbers(AMOUNT_OF_NUMBERS, MAX_NUMBER, MISFORTUNE_NUMBERS);

        assertThat(numbers, is(toList(1, 2, 3, 4, 5)));
    }

}