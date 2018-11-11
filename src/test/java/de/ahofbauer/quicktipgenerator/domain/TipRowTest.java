package de.ahofbauer.quicktipgenerator.domain;

import org.junit.jupiter.api.Test;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class TipRowTest {

    @Test
    void testTipRowPartSortsTheNumbersAscending() {
        TipRow.TipRowPart tipRowPart = new TipRow.TipRowPart("test-row", toList(17, 8, 2, 23));

        assertThat(tipRowPart.getNumbers(), is(toList(2, 8, 17, 23)));
    }

    @Test
    void testToString() {
        TipRow tipRow = new TipRow(
                new TipRow.TipRowPart("5aus50", toList(23, 5, 19, 41, 13)),
                new TipRow.TipRowPart("2aus10", toList(9, 2))
        );

        assertThat(tipRow.toString(), is("5 13 19 23 41 - 2 9"));
    }

}