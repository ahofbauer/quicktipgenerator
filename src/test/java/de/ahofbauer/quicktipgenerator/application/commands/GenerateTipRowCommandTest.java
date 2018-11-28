package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.application.output.TestOutput;
import de.ahofbauer.quicktipgenerator.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GenerateTipRowCommandTest {

    @Test
    void validateArguments() {
        GenerateTipRowCommand command = new GenerateTipRowCommand(createTestConfiguration(new TestOutput(), null));

        assertThat(
                command.validateArguments(toList("lottery-a", "lottery-b", "lottery-c", "lottery-d")),
                is(toList(
                        "Unbekannte Lotterie: lottery-a",
                        "Unbekannte Lotterie: lottery-c"
                ))
        );
    }

    @Test
    void executeCommand(@Mock MisfortuneNumberRepository repositoryMock) {
        MisfortuneNumbers misfortuneNumbers = new MisfortuneNumbersFactory().createNewInstance(toList(2, 5));
        given(repositoryMock.load()).willReturn(misfortuneNumbers);
        TestOutput testOutput = new TestOutput();
        GenerateTipRowCommand command = new GenerateTipRowCommand(createTestConfiguration(testOutput, repositoryMock));

        command.executeCommand(toList("lottery-b", "lottery-d"));

        assertThat(testOutput.getOutputAsString(), is(
                "Generiere Tippreihen ohne die Unglückszahlen: 2 5\n" +
                        "Tippreihe für lottery-b: 1 3\n" +
                        "Tippreihe für lottery-d: 4 6\n"
        ));
    }

    private LotteryConfiguration createTestConfiguration(Output output, MisfortuneNumberRepository repository) {
        return new LotteryConfiguration() {
            @Override
            public List<LotteryTipRowGenerator> lotteryTipRowGenerators() {
                return Arrays.asList(
                        createTipRowGenerator("lottery-b", 1, 2, 3),
                        createTipRowGenerator("lottery-d", 4, 5, 6)
                );
            }

            @Override
            public Output output() {
                return output;
            }

            @Override
            public MisfortuneNumberRepository misfortuneNumberRepository() {
                return repository;
            }
        };
    }

    private LotteryTipRowGenerator createTipRowGenerator(String name, Integer... numbers) {
        return new LotteryTipRowGenerator() {
            @Override
            public TipRow generateTipRow(MisfortuneNumbers misfortuneNumbers) {
                List<Integer> filtered = Arrays.stream(numbers)
                        .filter(n -> !misfortuneNumbers.isMisfortuneNumber(n))
                        .collect(Collectors.toList());
                return new TipRow(new TipRow.TipRowPart(name + "-trp", filtered));
            }

            @Override
            public String getLotteryName() {
                return name;
            }
        };
    }
}