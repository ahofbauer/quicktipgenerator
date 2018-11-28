package de.ahofbauer.quicktipgenerator.application.commandapp;

import de.ahofbauer.quicktipgenerator.application.commandapp.ArgumentParser.CommandInfo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

class ArgumentParserTest {

    @Test
    void parseArguments() {
        List<CommandInfo> commandInfoList = new ArgumentParser().parseArguments("-s", "7", "13", "23", "-r", "-g", "6aus49");

        assertThat(commandInfoList, hasSize(3));
        assertCommandInfo(commandInfoList.get(0), "s", toList("7", "13", "23"));
        assertCommandInfo(commandInfoList.get(1), "r", toList());
        assertCommandInfo(commandInfoList.get(2), "g", toList("6aus49"));
    }

    @Test
    void parseArgumentsShouldNotFailIfNotStartingWithCommand() {
        List<CommandInfo> commandInfoList = new ArgumentParser().parseArguments("7", "13", "23", "-r", "-g", "6aus49");

        assertThat(commandInfoList, hasSize(3));
        assertCommandInfo(commandInfoList.get(0), null, toList("7", "13", "23"));
    }

    private void assertCommandInfo(CommandInfo commandInfo, String commandName, List<String> arguments) {
        assertThat(commandInfo.getCommandName(), is(commandName));
        assertThat(commandInfo.getArguments(), is(arguments));
    }

}