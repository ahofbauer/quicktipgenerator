package de.ahofbauer.quicktipgenerator.application.commands;

import de.ahofbauer.quicktipgenerator.domain.MisfortuneNumbers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static de.ahofbauer.quicktipgenerator.domain.TestHelperFunctions.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SetMisfortuneNumbersCommandTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private LotteryConfiguration configurationMock;

    @InjectMocks
    private SetMisfortuneNumbersCommand command;

    @Test
    void validateArgumentsShouldNotAllowNoArguments() {
        assertThat(command.validateArguments(toList()),
                is(toList("Bitte geben Sie mindestens eine Unglückszahl an.")));
    }

    @Test
    void validateArgumentsShouldOnlyAllowNumbers() {
        assertThat(command.validateArguments(toList("7", "13", "a", "5", "5g")),
                is(toList("'a' ist keine gültige Zahl.", "'5g' ist keine gültige Zahl.")));
    }

    @Test
    void validateArgumentsShouldAllowNumbers() {
        assertThat(command.validateArguments(toList("7", "13", "23")), hasSize(0));
    }

    @Test
    void validateArgumentsShouldIncludeFactoryValidation() {
        assertThat(command.validateArguments(toList("7", "55", "23", "0")),
                is(toList("Unglückzahlen müssen zwischen 1 und 50 liegen: 55, 0")));
    }

    @Test
    void executeShouldSaveMisfortuneNumbers() {
        command.executeCommand(toList("7", "13", "23"));

        ArgumentCaptor<MisfortuneNumbers> captor = ArgumentCaptor.forClass(MisfortuneNumbers.class);

        then(configurationMock.misfortuneNumberRepository()).should().save(captor.capture());

        assertThat(captor.getValue().getNumbers(), is(toList(7, 13, 23)));
    }
}