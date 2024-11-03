package math;

import io.FileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileIOTest {
    private FileIO fileIO;
    private String testDir;

    @BeforeEach
    void setUp() {
        fileIO = new FileIO();
        testDir = System.getProperty("java.io.tmpdir");
    }

    @Test
    void testReadFile_FileDoesNotExist() {
        String nonexistentFile = testDir + "/nonexistent.txt";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileIO.readFile(nonexistentFile);
        });
        assertEquals("Input file does not exist", exception.getMessage());
    }

    @Test
    void testReadFile_EmptyFile() throws IOException {
        File emptyFile = new File(testDir + "/empty.txt");
        emptyFile.createNewFile();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileIO.readFile(emptyFile.getAbsolutePath());
        });
        assertEquals("Given file is empty", exception.getMessage());

        emptyFile.delete();
    }

    @Test
    void testReadFile_FileWithValidNumbers() throws IOException {
        File validNumbersFile = new File(testDir + "/valid_numbers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(validNumbersFile))) {
            writer.write("10\n");
            writer.write("20\n");
            writer.write("30\n");
        }

        int[] result = fileIO.readFile(validNumbersFile.getAbsolutePath());
        assertArrayEquals(new int[]{10, 20, 30}, result);

        validNumbersFile.delete();
    }

    @Test
    void testReadFile_FileWithInvalidNumbers() throws IOException {
        File mixedNumbersFile = new File(testDir + "/mixed_numbers.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(mixedNumbersFile))) {
            writer.write("10\n");
            writer.write("invalid\n");
            writer.write("20\n");
            writer.write("abc\n");
            writer.write("30\n");
        }

        int[] result = fileIO.readFile(mixedNumbersFile.getAbsolutePath());
        assertArrayEquals(new int[]{10, 20, 30}, result);

        mixedNumbersFile.delete();
    }

    @Test
    void testReadFile_FileWithAllInvalidNumbers() throws IOException {
        File allInvalidFile = new File(testDir + "/all_invalid.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(allInvalidFile))) {
            writer.write("invalid\n");
            writer.write("abc\n");
        }

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            fileIO.readFile(allInvalidFile.getAbsolutePath());
        });
        assertEquals("Given file is empty", exception.getMessage());

        allInvalidFile.delete();
    }
}
