package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.application.output.Output;
import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RemoveMisfortuneNumbersCommandTest {

    @Mock
    private MisfortuneNumberRepository repositoryMock;
    @Mock
    private Output outputMock;

    @InjectMocks
    private RemoveMisfortuneNumbersCommand command;

    @Test
    void validateArgumentsShouldNotAcceptParameters() {
        assertThat(command.validateArguments(toList("a", "b")),
                is(toList("-l akzeptiert keine Parameter.")));
    }

    @Test
    void validateArgumentsShouldAcceptEmptyList() {
        assertThat(command.validateArguments(toList()), is(toList()));
    }

    @Test
    void executeShouldDeleteMisfortuneNumbers() {
        command.executeCommand(toList());

        then(repositoryMock).should().delete();
    }
}