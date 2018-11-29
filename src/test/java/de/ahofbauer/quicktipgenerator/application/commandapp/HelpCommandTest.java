package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.TestOutput;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static de.ahofbauer.quicktipgenerator.application.commandapp.TestCommand.createTestCommandConfiguration;
import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class HelpCommandTest {

    @Test
    void validateArgumentsShouldNotAcceptParameters() {
        HelpCommand command = new HelpCommand(createTestCommandConfiguration());

        assertThat(command.validateArguments(toList("a", "b")),
                is(toList("-r akzeptiert keine Parameter.")));
    }

    @Test
    void executeShouldListAllCommands() {
        CommandConfiguration configuration = createTestCommandConfiguration(
                new TestCommand("a", "1"),
                new TestCommand("b", "2")
        );
        HelpCommand command = new HelpCommand(configuration);

        command.executeCommand(Collections.emptyList());

        TestOutput output = (TestOutput) configuration.output();
        assertThat(output.getOutputAsString(), is(
                "Verfügbare Kommandos:\n" +
                "-a 1 - Test command a\n" +
                "-b 2 - Test command b\n" +
                "-h - Hilfe: Listet alle verfügbaren Kommandos und ihre Beschreibungen auf.\n"
        ));
    }

}