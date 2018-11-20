package de.ahofbauer.quicktipgenerator.application.filestorage;

/**
 * A system exception thrown if the FileStorageMisfortuneNumberRepository is not able to operate. These exceptions
 * should not occur during normal usage.
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
