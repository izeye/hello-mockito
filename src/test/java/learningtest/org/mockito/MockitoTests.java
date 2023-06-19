package learningtest.org.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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

    @Test
    void argumentCaptor() {
        List<String> mockedList = mock();

        String value = "one";
        mockedList.add(value);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockedList).add(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(value);
    }

    @Test
    void argumentCaptorWithMultipleInvocations() {
        List<String> mockedList = mock();

        String value1 = "one";
        String value2 = "two";
        mockedList.add(value1);
        mockedList.add(value2);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockedList, times(2)).add(argumentCaptor.capture());
        List<String> allValues = argumentCaptor.getAllValues();
        assertThat(allValues).containsExactly(value1, value2);
    }

    @Test
    void testAssertArg() {
        List<String> mockedList = mock();

        String value = "one";
        mockedList.add(value);

        AtomicInteger count = new AtomicInteger(0);

        verify(mockedList).add(assertArg((v) ->  {
            count.incrementAndGet();

            assertThat(v).isEqualTo(value);
        }));

        // assertArg() has been invoked twice. Is this intentional?
        assertThat(count.get()).isEqualTo(2);
    }

}
