package math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.FileIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;



class ArrayOperationsTest {
    private ArrayOperations arrayOperations;
    private FileIO fileIO;
    private MyMath myMath;
    private String testDir;

    @BeforeEach
    void setUp() {
        arrayOperations = new ArrayOperations();
        fileIO = new FileIO();
        myMath = new MyMathImpl(); // Concrete implementation
        testDir = System.getProperty("java.io.tmpdir");
    }

    @Test
    void testFindPrimesInFile_WithPrimes() throws IOException {
        File validNumbersFile = new File(testDir + "/primes.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(validNumbersFile))) {
            writer.write("2\n3\n4\n5\n6\n7\n");
        }

        int[] primes = arrayOperations.findPrimesInFile(fileIO, validNumbersFile.getAbsolutePath(), myMath);
        assertArrayEquals(new int[]{2, 3, 5, 7}, primes);

        validNumbersFile.delete();
    }

    @Test
    void testFindPrimesInFile_NoPrimes() throws IOException {
        File noPrimesFile = new File(testDir + "/no_primes.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(noPrimesFile))) {
            writer.write("4\n6\n8\n9\n");
        }

        int[] primes = arrayOperations.findPrimesInFile(fileIO, noPrimesFile.getAbsolutePath(), myMath);
        assertArrayEquals(new int[]{}, primes);

        noPrimesFile.delete();
    }

    @Test
    void testFindPrimesInFile_MixedNumbers() throws IOException {
        File mixedFile = new File(testDir + "/mixed.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mixedFile))) {
            writer.write("1\n2\n3\n4\n5\n10\n11\n");
        }

        int[] primes = arrayOperations.findPrimesInFile(fileIO, mixedFile.getAbsolutePath(), myMath);
        assertArrayEquals(new int[]{2, 3, 5, 11}, primes);

        mixedFile.delete();
    }

    class MyMathImpl extends MyMath {
        @Override
        public boolean isPrime(int number) {
            if (number <= 1) return false;
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) return false;
            }
            return true;
        }
    }
}
