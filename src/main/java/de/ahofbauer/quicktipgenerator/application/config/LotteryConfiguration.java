package de.ahofbauer.quicktipgenerator.application.config;

import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.LotteryTipRowGenerator;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;

import java.util.List;

/**
 * Configuration for the available lotteries and the dependencies to create a tip row for those.
 */
public interface LotteryConfiguration {

    /**
     * List of available lottery tip row generators.
     */
    List<LotteryTipRowGenerator> lotteryTipRowGenerators();

    /**
     * The output to use.
     */
    Output output();

    /**
     * The misfortune number repository to use.
     */
    MisfortuneNumberRepository misfortuneNumberRepository();
}
