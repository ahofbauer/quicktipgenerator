package de.ahofbauer.quicktipgenerator.application.interactive;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.commandapp.CommandConfiguration;
import de.ahofbauer.quicktipgenerator.application.output.Output;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Application able to process commands in a interactive way, prompting for user input. See {@link Command}
 */
public class InteractiveModeApplication {

    private Output output;
    private Scanner scanner;
    private Map<String, Command> commandMap;
    private Pattern argumentSplitPattern = Pattern.compile(" ");

    public InteractiveModeApplication(CommandConfiguration configuration) {
        output = configuration.output();
        scanner = new Scanner(System.in);
        commandMap = configuration.commands().stream()
                .filter(c -> !(c instanceof InteractiveModeCommand))
                .collect(toMap(Command::getCommandLineParameter, command -> command));
    }

    public void run() {
        Optional<Command> chosenCommand;
        do {
            chosenCommand = readInput(this::outputAvailableCommands, this::convertCommandInput);
            chosenCommand.ifPresent(this::readArgumentsForCommandAndExecute);
        } while (chosenCommand.isPresent());
    }

    /**
     * Reads user input in a loop until the user entered a valid input or the user aborts.
     *
     * @param prompt A runnable executed for each input. Can be used to create the user prompt.
     * @param convertInput Converts the entered string to the desired return type. If the input of the user
     *                     was not correct an Optional.empty() should be returned and the user will be
     *                     prompted again unit the input is correct or he abborts
     * @return The converted user input or an Optional.empty() if the user did abort.
     */
    private <T> Optional<T> readInput(Runnable prompt, Function<String, Optional<T>> convertInput) {
        Optional<T> input;
        do {
            // Generate the user prompt
            prompt.run();
            // Read input from the user
            String inputStr = scanner.nextLine();
            // If the user enters "e" the input is aborted and an Optional.empty() will be returned
            if ("e".equals(inputStr)) {
                return Optional.empty();
            }
            // Try to convert the user input
            input = convertInput.apply(inputStr);
        } while (!input.isPresent());// If the input is not present, the input was invalid and prompting continues
        return input;
    }

    /**
     * Outputs the list of available commands.
     */
    private void outputAvailableCommands() {
        output.println("");
        commandMap.values().forEach(command -> {
            output.println("{0} - {1}", command.getCommandLineParameter(), command.getDescription());
        });
        output.println("");
        output.println("Bitte wählen (e - exit): ");
    }

    /**
     * Converts the user input to a command.
     */
    private Optional<Command> convertCommandInput(String input) {
        Optional<Command> command = Optional.ofNullable(commandMap.get(input));
        if (!command.isPresent()) {
            output.println("Ungültige Eingabe: {0}", input);
        }
        return command;
    }

    /**
     * Prompts the user for the arguments necessary for the given command. If the command does not require
     * arguments it will be executed immediately.
     */
    private void readArgumentsForCommandAndExecute(Command command) {
        if (command.getInvokePatternDescription().isEmpty()) {
            // The command does not require arguments, execute it.
            command.executeCommand(Collections.emptyList());
        } else {
            readInput(() -> outputArgumentPattern(command), input -> convertArguments(command, input))
                    .ifPresent(command::executeCommand);
        }
    }

    /**
     * Outputs the argument pattern for the given command.
     */
    private void outputArgumentPattern(Command command) {
        output.println("");
        output.println("Bitte parameter eingeben ({0}):", command.getInvokePatternDescription());
    }

    /**
     * Converts the user input to a list of arguments. Validates the arguments with the given command.
     */
    private Optional<List<String>> convertArguments(Command command, String input) {
        // Convert input to list of arguments
        List<String> args = argumentSplitPattern.splitAsStream(input)
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .collect(Collectors.toList());
        // Validate arguments with the given command
        List<String> errors = command.validateArguments(args);
        if (!errors.isEmpty()) {// If there are validation errors, output them
            errors.forEach(output::println);
            return Optional.empty();
        }
        return Optional.of(args);
    }

}
