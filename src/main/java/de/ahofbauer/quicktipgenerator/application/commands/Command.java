package de.ahofbauer.quicktipgenerator.application.commands;

import java.util.List;

/**
 * Represents a command that can be executed via the command line. A command could be something like
 * "set misfortune numbers" or "generate tip row". A command has a parameter like "-s" followed by a (optional) list of
 * arguments. The command "set misfortune numbers" could look like this: "-s 7 13 23"
 */
public interface Command {

    /**
     * Gets the parameter to invoke this command like "-s" to store misfortune numbers or "-g" to generate a tip row.
     * The first leading "-" ist not part of this and will be added automatically.
     */
    String getCommandLineParameter();

    /**
     * Gets a description of the pattern how to invoke the argument. Example "-g [6aus49] [eurojackpot]".
     * The description should always start with the command line parameter followed by the available parameters.
     */
    String getInvokePatternDescription();

    /**
     * Gets a textual description of what the command does like "generate a tip row for the given lottery"
     */
    String getDescription();

    /**
     * Validates if the arguments behind the command line parameters are valid.
     * @param arguments The arguments to validate
     * @return A list of error messages if the arguments are not valid, an empty list otherwise.
     */
    List<String> validateArguments(List<String> arguments);

    /**
     * Executes the command with the given arguments
     */
    void executeCommand(List<String> arguments);
}
