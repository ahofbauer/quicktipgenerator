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
import static org.mockito.BDDMockito.willReturn;

@ExtendWith(MockitoExtension.class)
class EuroJackpotTipRowGeneratorTest {

    private static final MisfortuneNumbers MISFORTUNE_NUMBERS = new MisfortuneNumbers(toList(7, 13));

    @Mock
    private RandomNumberGenerator randomNumberGeneratorMock;

    @InjectMocks
    private EuroJackpotTipRowGenerator euroJackpotTipRowGenerator;

    @Test
    void testGenerateTipRows() {
        willReturn(toList(1, 2, 3, 4, 5)).given(randomNumberGeneratorMock).generateNumbers(5, 50, MISFORTUNE_NUMBERS);
        willReturn(toList(1, 2)).given(randomNumberGeneratorMock).generateNumbers(2, 10, MISFORTUNE_NUMBERS);

        TipRow tipRow = euroJackpotTipRowGenerator.generateTipRow(MISFORTUNE_NUMBERS);

        assertThat(tipRow, is(not(nullValue())));
        assertThat(tipRow.getTipRowParts(), hasSize(2));
        assertTipRow(tipRow.getTipRowParts().get(0), "5aus50", 1, 2, 3, 4, 5);
        assertTipRow(tipRow.getTipRowParts().get(1), "2aus10", 1, 2);
    }
}