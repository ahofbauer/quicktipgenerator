package de.ahofbauer.quicktipgenerator.application.filestorage;

import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbersFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * MisfortuneNumberRepository implementation that uses the file system as storage. MisfortuneNumbers is stored in the
 * given file. The misfortune numbers are represented as JSON array of numbers. E.g. <code>[7,13,23]</code>
 */
public class FileStorageMisfortuneNumberRepository implements MisfortuneNumberRepository {

    private final MisfortuneNumbersFactory factory = new MisfortuneNumbersFactory();
    private final Path storagePath;

    public FileStorageMisfortuneNumberRepository(Path storagePath) {
        this.storagePath = storagePath;
    }

    @Override
    public void save(MisfortuneNumbers misfortuneNumbers) {
        if (misfortuneNumbers.isEmpty()) {
            delete();
        } else {
            try {
                Files.write(storagePath, toByteArray(misfortuneNumbers));
            } catch (IOException e) {
                throw new FileStorageException("Unable to write content to " + storagePath, e);
            }
        }
    }

    private byte[] toByteArray(MisfortuneNumbers misfortuneNumbers) {
        String numberList = misfortuneNumbers.getNumbers().stream().map(String::valueOf).collect(joining(","));
        return ("[" + numberList + "]").getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public MisfortuneNumbers load() {
        // Return an empty MisfortuneNumber object if the file does not exist
        if (!Files.exists(storagePath)) {
            return factory.createNewInstance(Collections.emptyList());
        }

        try {
            return fromByteArray(Files.readAllBytes(storagePath));
        } catch (IOException e) {
            throw new FileStorageException("Unable to read content from " + storagePath, e);
        }
    }

    private MisfortuneNumbers fromByteArray(byte[] bytes) {
        String str = new String(bytes, Charset.forName("UTF-8"));
        if (!str.startsWith("[") || !str.endsWith("]")) {
            throw new FileStorageException("Unable to parse: " + str);
        }
        String withoutBrackets = str.substring(1, str.length() - 1);
        List<Integer> numbers = stringToList(withoutBrackets);
        return factory.createNewInstance(numbers);
    }

    private List<Integer> stringToList(String withoutBrackets) {
        try {
            return Arrays.stream(withoutBrackets.split(","))
                    .map(String::trim)//
                    .map(Integer::valueOf)//
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new FileStorageException("Unable to parse: " + withoutBrackets, e);
        }
    }

    @Override
    public void delete() {
        try {
            if (Files.exists(storagePath)) {
                Files.delete(storagePath);
            }
        } catch (IOException e) {
            throw new FileStorageException("Unable to delete " + storagePath, e);
        }
    }

}
