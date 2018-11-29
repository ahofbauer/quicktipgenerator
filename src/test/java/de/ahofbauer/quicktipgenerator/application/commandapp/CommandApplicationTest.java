package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.TestOutput;
import org.junit.jupiter.api.Test;

import static de.ahofbauer.quicktipgenerator.application.commandapp.TestCommand.createTestCommandConfiguration;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class CommandApplicationTest {

    private TestCommand testCommandA = new TestCommand("a", "1");
    private TestCommand testCommandB = new TestCommand("b", "2");

    @Test
    void runShouldExecuteMultipleCommands() {

        CommandConfiguration configuration = createTestCommandConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run("-a", "1", "-b", "2");

        assertThat("Command has not been executed", testCommandA.isExecuted(), is(true));
        assertThat("Command has not been executed", testCommandB.isExecuted(), is(true));
    }

    @Test
    void runShouldNotExecuteAnyCommandIfValidationFails() {
        CommandConfiguration configuration = createTestCommandConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run("-a", "1", "-b", "3");

        assertThat("Command should not have been executed", testCommandA.isExecuted(), is(false));
        assertThat("Command should not have been executed", testCommandB.isExecuted(), is(false));
        TestOutput testOutput = (TestOutput) configuration.output();
        assertThat(testOutput.getOutputAsString(), is("Fehler beim ausführen von: -b 3\nWrong parameter\n"));
    }

    @Test
    void runShouldComplainAboutUnknownCommands() {
        TestCommand testCommandD = new TestCommand("d", "4");
        CommandConfiguration configuration = createTestCommandConfiguration(testCommandB, testCommandD);

        new CommandApplication(configuration).run("1", "-b", "2", "-c", "3", "-d", "4");

        assertThat("Command should not have been executed", testCommandB.isExecuted(), is(false));
        assertThat("Command should not have been executed", testCommandD.isExecuted(), is(false));
        TestOutput testOutput = (TestOutput) configuration.output();
        assertThat(testOutput.getOutputAsString(), is(
                "Fehler beim ausführen von: 1\nUnbekanntes Kommando\n" +
                        "Fehler beim ausführen von: -c 3\nUnbekanntes Kommando\n"
        ));
    }

    @Test
    void shouldIncludeHelpCommand() {
        CommandConfiguration configuration = createTestCommandConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run("-h");

        TestOutput output = (TestOutput) configuration.output();
        assertThat(output.getOutputAsString(), is(
                "Verfügbare Kommandos:\n" +
                        "-a 1 - Test command a\n" +
                        "-b 2 - Test command b\n" +
                        "-h - Hilfe: Listet alle verfügbaren Kommandos und ihre Beschreibungen auf.\n"
        ));
    }

    @Test
    void shouldRunHelpCommandForNoArgument() {
        CommandConfiguration configuration = createTestCommandConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run();

        TestOutput output = (TestOutput) configuration.output();
        assertThat(output.getOutputAsString(), is(
                "Verfügbare Kommandos:\n" +
                        "-a 1 - Test command a\n" +
                        "-b 2 - Test command b\n" +
                        "-h - Hilfe: Listet alle verfügbaren Kommandos und ihre Beschreibungen auf.\n"
        ));
    }

}