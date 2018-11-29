package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.application.output.TestOutput;

import java.util.List;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;

/**
 * Command used for tests. Excepts the given parameter, will return an validation error otherwise.
 */
class TestCommand implements Command {

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
        return "-" + command + " " + expectedParameter;
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

    boolean isExecuted() {
        return executed;
    }

    static CommandConfiguration createTestCommandConfiguration(Command... commands) {
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
}
