package de.ahofbauer.quicktipgenerator.application.output;

/**
 * Abstraction to print output e.g. to an console.
 */
public interface Output {

    /**
     * Prints the given output. The function can format the given output with the given parameters.
     * For details regarding the format see {@link java.text.MessageFormat#format}
     */
    void println(String output, Object... arguments);

}
