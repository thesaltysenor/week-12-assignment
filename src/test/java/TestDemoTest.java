import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class TestDemoTest {
    private TestDemo testDemo;

    @BeforeEach
    void setUp() {
        // This method will be called before each test
        testDemo = new TestDemo();
    }

    @ParameterizedTest
    @MethodSource("TestDemoTest#argumentsForAddPositive")
    void assertThatTwoPositiveNumbersAreAddedCorrectly(int a, int b, int expected, boolean expectException) {
        // Your first test case
        if(!expectException) {
            assertThat(testDemo.addPositive(a, b)) .isEqualTo(expected);
        } else {
            assertThatThrownBy(() -> testDemo.addPositive(a, b)) .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void assertThatNumberSquaredIsCorrect() {
        TestDemo mockDemo = spy(testDemo);
        doReturn(5).when(mockDemo).getRandomInt();
        int fiveSquared = mockDemo.randomNumberSquared();
        assertThat(fiveSquared).isEqualTo(25);
    }


    static Stream<Arguments> argumentsForAddPositive() {
        return Stream.of(
                arguments(2, 4, 6, false),  // Both numbers are positive, no exception
                arguments(-1, 1, 0, true),  // One number is negative, expect an exception
                arguments(1, -1, 0, true),  // One number is negative, expect an exception
                arguments(-1, -1, 0, true),   // Both numbers are negative, expect an exception
                arguments(0, 4, 0, true),   // One number is zero, expect an exception
                arguments(2, 0, 0, true)    // One number is zero, expect an exception
        );
    }
}
