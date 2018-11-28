package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.application.output.TestOutput;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class CommandApplicationTest {

    @Test
    void runShouldExecuteMultipleCommands() {
        TestCommand testCommandA = new TestCommand("a", "1");
        TestCommand testCommandB = new TestCommand("b", "2");
        CommandConfiguration configuration = createConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run("-a", "1", "-b", "2");

        assertThat("Command has not been executed", testCommandA.executed, is(true));
        assertThat("Command has not been executed", testCommandB.executed, is(true));
    }

    @Test
    void runShouldNotExecuteAnyCommandIfValidationFails() {
        TestCommand testCommandA = new TestCommand("a", "1");
        TestCommand testCommandB = new TestCommand("b", "2");
        CommandConfiguration configuration = createConfiguration(testCommandA, testCommandB);

        new CommandApplication(configuration).run("-a", "1", "-b", "3");

        assertThat("Command should not have been executed", testCommandA.executed, is(false));
        assertThat("Command should not have been executed", testCommandB.executed, is(false));
        TestOutput testOutput = (TestOutput) configuration.output();
        assertThat(testOutput.getOutputAsString(), is("Fehler beim ausführen von: -b 3\nWrong parameter\n"));
    }

    @Test
    void runShouldComplainAboutUnknownCommands() {
        TestCommand testCommandB = new TestCommand("b", "2");
        TestCommand testCommandD = new TestCommand("d", "4");
        CommandConfiguration configuration = createConfiguration(testCommandB, testCommandD);

        new CommandApplication(configuration).run("1", "-b", "2", "-c", "3", "-d", "4");

        assertThat("Command should not have been executed", testCommandB.executed, is(false));
        assertThat("Command should not have been executed", testCommandD.executed, is(false));
        TestOutput testOutput = (TestOutput) configuration.output();
        assertThat(testOutput.getOutputAsString(), is(
                "Fehler beim ausführen von: 1\nUnbekanntes Kommando\n" +
                "Fehler beim ausführen von: -c 3\nUnbekanntes Kommando\n"
        ));
    }

    private CommandConfiguration createConfiguration(Command... commands) {
        TestOutput testOutput = new TestOutput();
        return new CommandConfiguration() {
            @Override
            public List<Command> commands() {
                return toList(commands);
            }

            @Override
            public Output output() {
                return testOutput;
            }
        };
    }

    /**
     * Command used for tests. Excepts the given parameter, will return an validation error otherwise.
     */
    static class TestCommand implements Command {

        private String command;
        private String expectedParameter;
        private boolean executed = false;

        TestCommand(String command, String expectedParameter) {
            this.command = command;
            this.expectedParameter = expectedParameter;
        }

        @Override
        public String getCommandLineParameter() {
            return command;
        }

        @Override
        public String getInvokePatternDescription() {
            return command + " " + expectedParameter;
        }

        @Override
        public String getDescription() {
            return "Test command " + command;
        }

        @Override
        public List<String> validateArguments(List<String> arguments) {
            if (arguments.equals(toList(expectedParameter))) {
                return toList();
            } else {
                return toList("Wrong parameter");
            }
        }

        @Override
        public void executeCommand(List<String> arguments) {
            executed = true;
        }
    }

}