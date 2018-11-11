package de.ahofbauer.quicktipgenerator;

import de.ahofbauer.quicktipgenerator.domain.EuroJackpotTipRowGenerator;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbersFactory;
import de.ahofbauer.quicktipgenerator.domain.SixOfFortyNineTipRowGenerator;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Simple test for the domain. To be removed when the application layer is existing.
 */
public class SimpleDomainTest {

    public static void main(String... args) {
        System.out.println("Unglückzahlen: 7, 13, 23\n");
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbersFactory().createNewInstance(Arrays.asList(7, 13, 23));

        System.out.println("Generiere 10 Tippreihen für 6aus49:");
        SixOfFortyNineTipRowGenerator sixOfFortyNineTipRowGenerator = new SixOfFortyNineTipRowGenerator();
        IntStream.range(1, 11).forEach(i -> {
            System.out.println(i + ": " + sixOfFortyNineTipRowGenerator.generateTipRow(misfortuneNumbers));
        });

        System.out.println("\nGeneriere 10 Tippreihen für Eurojackpot:");
        EuroJackpotTipRowGenerator euroJackpotTipRowGenerator = new EuroJackpotTipRowGenerator();
        IntStream.range(1, 11).forEach(i -> {
            System.out.println(i + ": " + euroJackpotTipRowGenerator.generateTipRow(misfortuneNumbers));
        });
    }
}
