package de.ahofbauer.quicktipgenerator.domain;

import java.util.Collections;
import java.util.List;

/**
 * Thrown if the validation of an domain object did fail e.g. due to a invalid user input. Contains one or more error
 * messages to be displayed to the user.
 */
public class ValidationException extends RuntimeException {

    private List<String> errorMessages;

    public ValidationException(String errorMessage) {
        this(Collections.singletonList(errorMessage));
    }

    public ValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * Returns a list of error messages to be displayed to the user.
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
