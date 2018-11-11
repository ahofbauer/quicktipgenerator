package de.ahofbauer.quicktipgenerator.domain;

/**
 * A generator that is able to generate tip rows for a certain lottery like "6 aus 49" or "Eurojackpot".
 */
public interface LotteryTipRowGenerator {

    /**
     * Generates tip rows taking the given given misfortune numbers into account. A tip row wont contain any of the
     * misfortune numbers.
     */
    TipRow generateTipRow(MisfortuneNumbers misfortuneNumbers);

}
