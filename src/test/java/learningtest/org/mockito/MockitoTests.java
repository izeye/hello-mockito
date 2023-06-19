package learningtest.org.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Mockito}
 *
 * @author Johnny Lim
 */
class MockitoTests {

    @Test
    void verifyInteractions() {
        List mockedList = mock();

        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    void stubMethodCalls() {
        LinkedList mockedList = mock();

        String firstValue = "first";
        when(mockedList.get(0)).thenReturn(firstValue);

        assertThat(mockedList.get(0)).isEqualTo(firstValue);
        assertThat(mockedList.get(999)).isNull();
    }

}
