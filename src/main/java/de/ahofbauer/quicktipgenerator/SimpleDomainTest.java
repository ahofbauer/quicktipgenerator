package de.ahofbauer.quicktipgenerator;

import de.ahofbauer.quicktipgenerator.application.commands.GenerateTipRowCommand;
import de.ahofbauer.quicktipgenerator.application.config.DefaultConfiguration;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbersFactory;

import java.util.Arrays;

/**
 * Simple test for the domain. To be removed when the application layer is existing.
 */
public class SimpleDomainTest {

    public static void main(String... args) {
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbersFactory().createNewInstance(Arrays.asList(7, 13, 23));

        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.misfortuneNumberRepository().save(misfortuneNumbers);

        new GenerateTipRowCommand(configuration).executeCommand(Arrays.asList("6aus49", "6aus49", "eurojackpot", "eurojackpot"));
    }
}
