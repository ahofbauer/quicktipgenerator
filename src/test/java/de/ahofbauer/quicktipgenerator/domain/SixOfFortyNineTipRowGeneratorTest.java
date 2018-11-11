package de.ahofbauer.quicktipgenerator.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.assertTipRow;
import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SixOfFortyNineTipRowGeneratorTest {

    private static final MisfortuneNumbers MISFORTUNE_NUMBERS = new MisfortuneNumbers(toList(7, 13));

    @Mock
    private RandomNumberGenerator randomNumberGeneratorMock;

    @InjectMocks
    private SixOfFortyNineTipRowGenerator sixOfFortyNineTipRowGenerator;

    @Test
    void testGenerateTipRows() {
        given(randomNumberGeneratorMock.generateNumbers(6, 49, MISFORTUNE_NUMBERS)).willReturn(toList(1, 2, 3, 4, 5, 6));

        TipRow tipRow = sixOfFortyNineTipRowGenerator.generateTipRow(MISFORTUNE_NUMBERS);

        assertThat(tipRow, is(not(nullValue())));
        assertThat(tipRow.getTipRowParts(), hasSize(1));
        assertTipRow(tipRow.getTipRowParts().get(0), "6aus49", 1, 2, 3, 4, 5, 6);
    }

}