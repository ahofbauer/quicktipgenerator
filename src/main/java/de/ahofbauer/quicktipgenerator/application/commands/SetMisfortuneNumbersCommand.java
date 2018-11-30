package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbersFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This command sets (stores) a list of misfortune numbers.
 */
public class SetMisfortuneNumbersCommand implements Command {

    private MisfortuneNumberRepository repository;
    private MisfortuneNumbersFactory factory;
    private Output output;

    public SetMisfortuneNumbersCommand(LotteryConfiguration lotteryConfiguration) {
        this.repository = lotteryConfiguration.misfortuneNumberRepository();
        this.factory = new MisfortuneNumbersFactory();
        this.output = lotteryConfiguration.output();
    }

    @Override
    public String getCommandLineParameter() {
        return "s";
    }

    @Override
    public String getInvokePatternDescription() {
        return "Zahl [Zahl]...";
    }

    @Override
    public String getDescription() {
        return "Speichert eine Liste von Unglückzahlen.";
    }

    @Override
    public List<String> validateArguments(List<String> arguments) {
        List<String> errors = new ArrayList<>();
        validateNotEmpty(errors, arguments);
        arguments.forEach(arg -> validateNumber(errors, arg));
        // If the arguments are numbers we can transform them an let the factory validate the input
        if (errors.isEmpty()) {
            errors.addAll(factory.validate(toIntegerList(arguments)));
        }
        return errors;
    }

    private void validateNotEmpty(List<String> errors, List<String> arguments) {
        if (arguments.isEmpty()) {
            errors.add("Bitte geben Sie mindestens eine Unglückszahl an.");
        }
    }

    private void validateNumber(List<String> errors, String arg) {
        if (!isNumber(arg)) {
            errors.add("'" + arg + "' ist keine gültige Zahl.");
        }
    }

    private boolean isNumber(String arg) {
        for (int i = 0; i < arg.length(); i++) {
            if (!Character.isDigit(arg.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private List<Integer> toIntegerList(List<String> arguments) {
        return arguments.stream()//
                .map(Integer::valueOf)//
                .collect(Collectors.toList());
    }

    @Override
    public void executeCommand(List<String> arguments) {
        List<Integer> numbers = toIntegerList(arguments);
        MisfortuneNumbers misfortuneNumbers = factory.createNewInstance(numbers);
        repository.save(misfortuneNumbers);
        output.println("Unglückszahlen gespeichert: {0}", misfortuneNumbers);
    }

}
