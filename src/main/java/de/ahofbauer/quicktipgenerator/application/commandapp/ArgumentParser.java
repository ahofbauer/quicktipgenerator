package de.ahofbauer.quicktipgenerator.application.commandapp;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Parses a array of arguments into command name and arguments. Example -s 7 13 23 -g 6aus49 will be parsed as command
 * "s" with the arguments "7", "13", "23" and command g with the argument "6aus49"
 */
public class ArgumentParser {

    public List<CommandInfo> parseArguments(String... args) {
        List<CommandInfo> infoList = new ArrayList<>();
        CommandInfo currentInfo = null;
        for (String arg : args) {
            if (arg.startsWith("-")) {
                currentInfo = new CommandInfo(arg.substring(1));
                infoList.add(currentInfo);
            } else {
                if (currentInfo == null) {// This is an error. The arguments are not starting with a command
                    currentInfo = new CommandInfo(null);
                    infoList.add(currentInfo);
                }
                currentInfo.addArgument(arg);
            }
        }
        return infoList;
    }

    public static class CommandInfo {
        private final String commandName;
        private final List<String> arguments;

        public CommandInfo(String commandName) {
            this.commandName = commandName;
            this.arguments = new ArrayList<>();
        }

        public String getCommandName() {
            return commandName;
        }

        public List<String> getArguments() {
            return arguments;
        }

        public void addArgument(String argument) {
            arguments.add(argument);
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            if (commandName != null) {
                sb.append('-').append(commandName).append(" ");
            }
            sb.append(arguments.stream().collect(joining(" ")));
            return sb.toString();
        }
    }
}
