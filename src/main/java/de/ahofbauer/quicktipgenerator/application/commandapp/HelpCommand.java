package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.Output;

import java.util.ArrayList;
import java.util.List;

/**
 * This command lists all available commands and their descriptions
 */
public class HelpCommand implements Command {

    private List<Command> commands;
    private Output output;

    public HelpCommand(CommandConfiguration configuration) {
        this.commands = new ArrayList<>(configuration.commands());
        this.commands.add(this);
        this.output = configuration.output();
    }

    @Override
    public String getCommandLineParameter() {
        return "h";
    }

    @Override
    public String getInvokePatternDescription() {
        return "-h";
    }

    @Override
    public String getDescription() {
        return "Hilfe: Listet alle verfügbaren Kommandos und ihre Beschreibungen auf.";
    }

    @Override
    public List<String> validateArguments(List<String> arguments) {
        List<String> errors = new ArrayList<>();
        if (!arguments.isEmpty()) {
            errors.add("-r akzeptiert keine Parameter.");
        }
        return errors;
    }

    @Override
    public void executeCommand(List<String> arguments) {
        output.println("Verfügbare Kommandos:");
        commands.forEach(command -> {
            output.println("{0} - {1}", command.getInvokePatternDescription(), command.getDescription());
        });
    }
}
