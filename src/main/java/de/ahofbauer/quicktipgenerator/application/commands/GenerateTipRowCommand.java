package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.LotteryTipRowGenerator;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.TipRow;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * This commands generates a tip row for a specified lottery.
 */
public class GenerateTipRowCommand implements Command {

    private Map<String, LotteryTipRowGenerator> lotteryTipRowGeneratorMap;
    private Output output;
    private MisfortuneNumberRepository repository;

    public GenerateTipRowCommand(LotteryConfiguration configuration) {
        this.lotteryTipRowGeneratorMap = configuration.lotteryTipRowGenerators().stream()
                .collect(toMap(LotteryTipRowGenerator::getLotteryName, l -> l));
        this.output = configuration.output();
        this.repository = configuration.misfortuneNumberRepository();
    }

    @Override
    public String getCommandLineParameter() {
        return "g";
    }

    @Override
    public String getInvokePatternDescription() {
        return lotteryTipRowGeneratorMap.keySet().stream()
                .map(lottery -> "[" + lottery + "]")
                .collect(joining(" "));
    }

    @Override
    public String getDescription() {
        return "Generiert Tippreihen für die angegebenen Lotterien.";
    }

    @Override
    public List<String> validateArguments(List<String> arguments) {
        return arguments.stream()
                .filter(arg -> !lotteryTipRowGeneratorMap.containsKey(arg))
                .map(arg -> "Unbekannte Lotterie: " + arg)
                .collect(toList());
    }

    @Override
    public void executeCommand(List<String> arguments) {
        MisfortuneNumbers misfortuneNumbers = repository.load();
        output.println("Generiere Tippreihen ohne die Unglückszahlen: {0}", misfortuneNumbers);

        arguments.stream()
                .map(lotteryTipRowGeneratorMap::get)
                .forEach(generator -> generateTipRow(generator, misfortuneNumbers));
    }

    private void generateTipRow(LotteryTipRowGenerator generator, MisfortuneNumbers misfortuneNumbers) {
        TipRow tipRow = generator.generateTipRow(misfortuneNumbers);
        output.println("Tippreihe für {0}: {1}", generator.getLotteryName(), tipRow);
    }

}
