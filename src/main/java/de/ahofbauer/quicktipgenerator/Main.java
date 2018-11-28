package de.ahofbauer.quicktipgenerator;

import de.ahofbauer.quicktipgenerator.application.commandapp.CommandApplication;
import de.ahofbauer.quicktipgenerator.application.config.DefaultConfiguration;

/**
 * Bootstrap command application with default configuration.
 */
public class Main {

    public static void main(String... args) {
        new CommandApplication(new DefaultConfiguration()).run(args);
    }

}
