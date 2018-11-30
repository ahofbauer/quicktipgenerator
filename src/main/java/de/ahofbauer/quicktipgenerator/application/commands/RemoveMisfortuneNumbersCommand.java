package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * This command removes the current misfortune numbers
 */
public class RemoveMisfortuneNumbersCommand implements Command {

    private MisfortuneNumberRepository repository;
    private Output output;

    public RemoveMisfortuneNumbersCommand(LotteryConfiguration configuration) {
        this.repository = configuration.misfortuneNumberRepository();
        this.output = configuration.output();
    }

    @Override
    public String getCommandLineParameter() {
        return "l";
    }

    @Override
    public String getInvokePatternDescription() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Löscht alle aktuellen Unglückzahlen.";
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
        repository.delete();
        output.println("Unglückzahlen wurden entfernt.");
    }

}
