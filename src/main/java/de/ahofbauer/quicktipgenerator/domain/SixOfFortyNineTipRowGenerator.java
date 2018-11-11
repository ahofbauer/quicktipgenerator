package de.ahofbauer.quicktipgenerator.domain;

/**
 * Generates tip rows for the "6 aus 49" lottery.
 */
public class SixOfFortyNineTipRowGenerator implements LotteryTipRowGenerator {

    private static final String TIP_ROW_6_OF_49 = "6aus49";

    // Initializing RandomNumberGenerator as member will enable to mock it in the unit test
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Override
    public TipRow generateTipRow(MisfortuneNumbers misfortuneNumbers) {
        return new TipRow(
                new TipRow.TipRowPart(TIP_ROW_6_OF_49, randomNumberGenerator.generateNumbers(6, 49, misfortuneNumbers))
        );
    }

}
