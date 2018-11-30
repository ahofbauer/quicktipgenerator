package de.ahofbauer.quicktipgenerator.application.interactive;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.commandapp.CommandConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Starts the application in interactive mode, where the user can input the commands instead of using the command line.
 */
public class InteractiveModeCommand implements Command {

    private CommandConfiguration commandConfiguration;

    public InteractiveModeCommand(CommandConfiguration commandConfiguration) {
        this.commandConfiguration = commandConfiguration;
    }

    @Override
    public String getCommandLineParameter() {
        return "i";
    }

    @Override
    public String getInvokePatternDescription() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Interaktiver Modus: Die Anwendung wird über Menüs und Eingaben gesteuert, statt über Kommandozeilenparameter";
    }

    @Override
    public List<String> validateArguments(List<String> arguments) {
        List<String> errors = new ArrayList<>();
        if (!arguments.isEmpty()) {
            errors.add("-l akzeptiert keine Parameter.");
        }
        return errors;
    }

    @Override
    public void executeCommand(List<String> arguments) {
        new InteractiveModeApplication(commandConfiguration).run();
    }
}
