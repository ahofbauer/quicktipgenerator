package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.commandapp.ArgumentParser.CommandInfo;
import de.ahofbauer.quicktipgenerator.application.output.Output;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

/**
 * Application able to process commands. See {@link Command}.
 */
public class CommandApplication {

    private Map<String, Command> commandMap;
    private Output output;
    private ArgumentParser argumentParser;
    private HelpCommand helpCommand;

    public CommandApplication(CommandConfiguration commandConfiguration) {
        commandMap = commandConfiguration.commands().stream()
                .collect(toMap(Command::getCommandLineParameter, command -> command));
        helpCommand = new HelpCommand(commandConfiguration);
        commandMap.put(helpCommand.getCommandLineParameter(), helpCommand);
        output = commandConfiguration.output();
        argumentParser = new ArgumentParser();
    }

    public void run(String... arguments) {
        List<CommandInfo> commandInfoList = argumentParser.parseArguments(arguments);
        if (commandInfoList.isEmpty()) {// If no command was chosen show the help
            commandInfoList.add(new CommandInfo(helpCommand.getCommandLineParameter()));
        }
        if (isValid(commandInfoList)) {
            for (CommandInfo info : commandInfoList) {
                Command command = commandMap.get(info.getCommandName());
                command.executeCommand(info.getArguments());
            }
        }
    }

    private boolean isValid(List<CommandInfo> commandInfoList) {
        boolean valid = true;
        for (CommandInfo info : commandInfoList) {
            Command command = commandMap.get(info.getCommandName());
            valid &= isValid(info, command);
        }
        return valid;
    }

    private boolean isValid(CommandInfo info, Command command) {
        if (command == null) {
            printExecutionError(info);
            output.println("Unbekanntes Kommando");
            return false;
        }
        List<String> errors = command.validateArguments(info.getArguments());
        if (!errors.isEmpty()) {
            printExecutionError(info);
            errors.forEach(output::println);
            return false;
        }
        return true;
    }

    private void printExecutionError(CommandInfo info) {
        output.println("Fehler beim ausführen von: {0}", info);
    }

}
