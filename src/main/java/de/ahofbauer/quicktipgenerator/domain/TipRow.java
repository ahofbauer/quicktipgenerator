package de.ahofbauer.quicktipgenerator.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * A list of numbers that represent a tip row generated for a certain lottery. This list can be used by the user to fill
 * out his lottery ticket. The numbers may be divided into different parts. E.g. a tip row for Eurojackpot consists of
 * two parts.
 */
public class TipRow {
    private List<TipRowPart> tipRowParts;

    public TipRow(TipRowPart... tipRowPart) {
        tipRowParts = new ArrayList<>(Arrays.asList(tipRowPart));
    }

    /**
     * Returns the parts of the tip row.
     */
    public List<TipRowPart> getTipRowParts() {
        return new ArrayList<>(tipRowParts);
    }

    @Override
    public String toString() {
        return tipRowParts.stream().map(TipRowPart::toString).collect(joining(" - "));
    }

    /**
     * A list of numbers that represent a part of a tip row. Some tip row may consist of multiple parts. Eurojackpot for
     * instance has two parts. "5aus50" and "2aus10".
     */
    public static class TipRowPart {
        private final String name;
        private final List<Integer> numbers;

        TipRowPart(String name, List<Integer> numbers) {
            this.name = name;
            // Copy the list, so that you are not able to alter the state from outside of the class
            this.numbers = new ArrayList<>(numbers);
            Collections.sort(this.numbers);
        }

        /**
         * Return the name of the tip row part. Examples: "6aus49" or "5aus50".
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the numbers for this tip row.
         */
        public List<Integer> getNumbers() {
            return new ArrayList<>(numbers);
        }

        @Override
        public String toString() {
            return numbers.stream().map(String::valueOf).collect(joining(" "));
        }
    }
}
