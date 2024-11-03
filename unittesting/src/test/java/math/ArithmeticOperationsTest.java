package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticOperationsTest {

    private final ArithmeticOperations arithmetic = new ArithmeticOperations();

    @Test
    void testDivide_ValidInput() {
        assertEquals(2.0, arithmetic.divide(4, 2));
        assertEquals(-2.0, arithmetic.divide(-4, 2));
        assertEquals(0.0, arithmetic.divide(0, 5));
        assertEquals(1.0, arithmetic.divide(5, 5));
    }

    @Test
    void testDivide_DenominatorZero() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            arithmetic.divide(4, 0);
        });
        assertEquals("Cannot divide with zero", exception.getMessage());
    }

    @Test
    void testMultiply_ValidInput() {
        assertEquals(20, arithmetic.multiply(4, 5));
        assertEquals(0, arithmetic.multiply(0, 5));
        assertEquals(15, arithmetic.multiply(3, 5));
        assertEquals(12, arithmetic.multiply(3, 4));
    }

    @Test
    void testMultiply_NegativeInput() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> {
            arithmetic.multiply(-1, 5);
        });
        assertEquals("x & y should be >= 0", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> {
            arithmetic.multiply(4, -2);
        });
        assertEquals("x & y should be >= 0", exception2.getMessage());
    }

    @Test
    void testMultiply_ProductExceedsInteger() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            arithmetic.multiply(Integer.MAX_VALUE, 2);
        });
        assertEquals("The product does not fit in an Integer variable", exception.getMessage());
    }
}
