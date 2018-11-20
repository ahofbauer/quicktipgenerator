package de.ahofbauer.quicktipgenerator.application.filestorage;

import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbersFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileStorageMisfortuneNumberRepositoryTest {

    @Test
    void saveShouldStoreInFileAsString() throws IOException {
        Path tempFile = createTempFile();
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        repository.save(createMisfortuneNumbers(7, 13, 23));

        assertThat(readAsString(tempFile), is("[7,13,23]"));
    }

    @Test
    void saveShouldOverwriteExistingNumbers() throws IOException {
        Path tempFile = createTempFile();
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        repository.save(createMisfortuneNumbers(7, 13, 23));
        repository.save(createMisfortuneNumbers(1, 2, 3));

        assertThat(readAsString(tempFile), is("[1,2,3]"));
    }

    @Test
    void saveShouldCreateNewFileIfNotExisting() throws IOException {
        Path tempFile = createTempFile();
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);
        Files.delete(tempFile);

        repository.save(createMisfortuneNumbers(7, 13, 23));

        assertThat("The file should have been created", Files.exists(tempFile), is(true));
    }

    @Test
    void saveShouldRemoveFileForAnEmptyObject() throws IOException {
        Path tempFile = createTempFile();
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        repository.save(createMisfortuneNumbers());

        assertThat("The file should have been deleted", Files.exists(tempFile), is(false));
    }

    @Test
    void loadShouldLoadObjectFromFile() throws IOException {
        Path tempFile = createTempFile("[7,13,23]");
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        MisfortuneNumbers misfortuneNumbers = repository.load();

        assertThat(misfortuneNumbers, is(not(nullValue())));
        assertThat(misfortuneNumbers.getNumbers(), is(toList(7, 13, 23)));
    }

    @Test
    void loadThrowsExceptionForIncorrectContent() throws IOException {
        Path tempFile = createTempFile("7,13,23]");
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        assertThrows(FileStorageException.class, () -> {
            repository.load();
        });
    }

    @Test
    void loadThrowsExceptionForIncorrectNumberContent() throws IOException {
        Path tempFile = createTempFile("[7,13a,23]");
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        assertThrows(FileStorageException.class, () -> {
            repository.load();
        });
    }

    @Test
    void loadShouldReturnEmptyObjectIfFileDoesNotExist() throws IOException {
        Path tempFile = createTempFile();
        Files.delete(tempFile);
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        MisfortuneNumbers misfortuneNumbers = repository.load();

        assertThat(misfortuneNumbers, is(not(nullValue())));
        assertThat(misfortuneNumbers.getNumbers(), hasSize(0));
    }

    @Test
    void deleteShouldRemoveFile() throws IOException {
        Path tempFile = createTempFile();
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        repository.delete();

        assertThat("The file should have been deleted", Files.exists(tempFile), is(false));
    }

    @Test
    void deleteShouldNotFailIfAlreadyRemoved() throws IOException {
        Path tempFile = createTempFile();
        Files.delete(tempFile);
        FileStorageMisfortuneNumberRepository repository = new FileStorageMisfortuneNumberRepository(tempFile);

        repository.delete();
    }

    private MisfortuneNumbers createMisfortuneNumbers(Integer... numbers) {
        return new MisfortuneNumbersFactory().createNewInstance(toList(numbers));
    }

    private Path createTempFile() throws IOException {
        return createTempFile(null);
    }

    private Path createTempFile(String content) throws IOException {
        Path tempFile = Files.createTempFile("qtg-test", ".tmp");
        tempFile.toFile().deleteOnExit();
        if (content != null) {
            writeString(tempFile, content);
        }
        return tempFile;
    }

    private String readAsString(Path tempFile) throws IOException {
        return new String(Files.readAllBytes(tempFile), Charset.forName("UTF-8"));
    }

    private void writeString(Path tempFile, String str) throws IOException {
        Files.write(tempFile, str.getBytes(Charset.forName("UTF-8")));
    }
}