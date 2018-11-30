package de.ahofbauer.quicktipgenerator.application.config;

import de.ahofbauer.quicktipgenerator.application.commandapp.Command;
import de.ahofbauer.quicktipgenerator.application.commandapp.CommandConfiguration;
import de.ahofbauer.quicktipgenerator.application.commands.GenerateTipRowCommand;
import de.ahofbauer.quicktipgenerator.application.commands.LotteryConfiguration;
import de.ahofbauer.quicktipgenerator.application.commands.RemoveMisfortuneNumbersCommand;
import de.ahofbauer.quicktipgenerator.application.commands.SetMisfortuneNumbersCommand;
import de.ahofbauer.quicktipgenerator.application.filestorage.FileStorageMisfortuneNumberRepository;
import de.ahofbauer.quicktipgenerator.application.interactive.InteractiveModeCommand;
import de.ahofbauer.quicktipgenerator.application.output.ConsoleOutput;
import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.EuroJackpotTipRowGenerator;
import de.ahofbauer.quicktipgenerator.domain.LotteryTipRowGenerator;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;
import de.ahofbauer.quicktipgenerator.domain.SixOfFortyNineTipRowGenerator;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Default configuration for the quicktipgenerator.
 */
public class DefaultConfiguration implements LotteryConfiguration, CommandConfiguration {

    @Override
    public List<LotteryTipRowGenerator> lotteryTipRowGenerators() {
        return Arrays.asList(
                new SixOfFortyNineTipRowGenerator(),
                new EuroJackpotTipRowGenerator()
        );
    }

    @Override
    public List<Command> commands() {
        return Arrays.asList(
                new SetMisfortuneNumbersCommand(this),
                new RemoveMisfortuneNumbersCommand(this),
                new GenerateTipRowCommand(this),
                new InteractiveModeCommand(this)
        );
    }

    @Override
    public Output output() {
        return new ConsoleOutput();
    }

    @Override
    public MisfortuneNumberRepository misfortuneNumberRepository() {
        return new FileStorageMisfortuneNumberRepository(Paths.get("./misfortune-numbers.json"));
    }

}
