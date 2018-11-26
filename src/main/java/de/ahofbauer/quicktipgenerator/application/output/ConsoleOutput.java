package de.ahofbauer.quicktipgenerator.application.output;

import java.text.MessageFormat;

/**
 * Prints the given output to the console.
 */
public class ConsoleOutput implements Output {

    @Override
    public void println(String output, Object... arguments) {
        System.out.println(MessageFormat.format(output, arguments));
    }

}
