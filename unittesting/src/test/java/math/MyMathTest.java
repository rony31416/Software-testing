package math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyMathTest {
    private MyMath myMath;

    @BeforeEach
    void setUp() {
        myMath = new MyMath();
    }

    @Test
    void testFactorial_ValidInputs() {
        assertEquals(1, myMath.factorial(0)); // 0! = 1
        assertEquals(1, myMath.factorial(1)); // 1! = 1
        assertEquals(2, myMath.factorial(2)); // 2! = 2
        assertEquals(6, myMath.factorial(3)); // 3! = 6
        assertEquals(24, myMath.factorial(4)); // 4! = 24
        assertEquals(120, myMath.factorial(5)); // 5! = 120
        assertEquals(479001600, myMath.factorial(12)); // 12! = 479001600
    }

    @Test
    void testFactorial_InvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> myMath.factorial(-1)); // Negative input
        assertThrows(IllegalArgumentException.class, () -> myMath.factorial(13)); // Input greater than 12
    }

    @Test
    void testIsPrime_ValidInputs() {
        assertTrue(myMath.isPrime(2)); // 2 is prime
        assertTrue(myMath.isPrime(3)); // 3 is prime
        assertTrue(myMath.isPrime(5)); // 5 is prime
        assertTrue(myMath.isPrime(7)); // 7 is prime
        assertTrue(myMath.isPrime(11)); // 11 is prime
    }

    @Test
    void testIsPrime_InvalidInputs() {
        assertThrows(IllegalArgumentException.class, () -> myMath.isPrime(1)); // 1 is not prime
        assertThrows(IllegalArgumentException.class, () -> myMath.isPrime(-5)); // Negative input
    }

    @Test
    void testIsPrime_NonPrimeInputs() {
        assertFalse(myMath.isPrime(4)); // 4 is not prime
        assertFalse(myMath.isPrime(6)); // 6 is not prime
        assertFalse(myMath.isPrime(9)); // 9 is not prime
        assertFalse(myMath.isPrime(10)); // 10 is not prime
    }
}

