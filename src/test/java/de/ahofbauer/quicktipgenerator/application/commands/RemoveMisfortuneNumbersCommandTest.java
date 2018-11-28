package de.ahofbauer.quicktipgenerator.application.commands;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RemoveMisfortuneNumbersCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private LotteryConfiguration configurationMock;

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

        then(configurationMock.misfortuneNumberRepository()).should().delete();
    }
}