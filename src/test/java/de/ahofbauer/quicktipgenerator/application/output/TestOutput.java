package de.ahofbauer.quicktipgenerator.application.output;

import java.text.MessageFormat;

/**
 * Output usable for tests. Renders all the output into a buffer and returns what has been rendered as String.
 */
public class TestOutput implements Output {

    private StringBuilder sb = new StringBuilder();

    @Override
    public void println(String output, Object... arguments) {
        sb.append(MessageFormat.format(output, arguments)).append("\n");
    }

    public String getOutputAsString() {
        return sb.toString();
    }
}
