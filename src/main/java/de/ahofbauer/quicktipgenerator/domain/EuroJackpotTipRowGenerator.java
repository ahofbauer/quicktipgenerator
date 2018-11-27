package de.ahofbauer.quicktipgenerator.domain;

/**
 * Generates tip rows for the "Eurojackpot" lottery.
 */
public class EuroJackpotTipRowGenerator implements LotteryTipRowGenerator {

    private static final String TIP_ROW_5_OF_50 = "5aus50";
    private static final String TIP_ROW_2_OF_10 = "2aus10";

    // Initializing RandomNumberGenerator as member will enable to mock it in the unit test
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator();

    @Override
    public TipRow generateTipRow(MisfortuneNumbers misfortuneNumbers) {
        return new TipRow(
                new TipRow.TipRowPart(TIP_ROW_5_OF_50, randomNumberGenerator.generateNumbers(5, 50, misfortuneNumbers)),
                new TipRow.TipRowPart(TIP_ROW_2_OF_10, randomNumberGenerator.generateNumbers(2, 10, misfortuneNumbers))
        );
    }

    @Override
    public String getLotteryName() {
        return "eurojackpot";
    }

}
