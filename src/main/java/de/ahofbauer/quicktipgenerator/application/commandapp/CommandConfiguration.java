package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.Output;

import java.util.List;

/**
 * Configuration for the available commands.
 */
public interface CommandConfiguration {

    /**
     * List of available commands.
     */
    List<Command> commands();

    /**
     * The output to use.
     */
    Output output();

}
