
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Test
    void testReadStateFromFile_validInput() throws IOException {
        String inputFile = TEMP_DIR + "/4x4_przykladowa.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write("2 3\n");
            writer.write("1 2 3\n");
            writer.write("4 5 0\n");
        }

        int[][] expected = {
                {1, 2, 3},
                {4, 5, 0}
        };

        int[][] actual = Utils.readStateFromFile(inputFile);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testWriteSolutionFile_validSolution() throws IOException {
        String outputFile = TEMP_DIR + "/test_solution.txt";
        Utils.writeSolutionFile(outputFile, "[L, R, U, D, D]");

        List<String> lines = Files.readAllLines(Path.of(outputFile));
        assertEquals("5", lines.get(0));
        assertEquals("[L, R, U, D, D]", lines.get(1));
    }

    @Test
    void testWriteSolutionFile_noSolution() throws IOException {
        String outputFile = TEMP_DIR + "/test_no_solution.txt";
        Utils.writeSolutionFile(outputFile,  "-1");

        List<String> lines = Files.readAllLines(Path.of(outputFile));
        assertEquals("-1", lines.get(0));
        assertEquals(1, lines.size()); // tylko jedna linia
    }

    @Test
    void testWriteAdditionalInfoFile() throws IOException {
        String outputFile = TEMP_DIR + "/test_info.txt";
        Utils.writeAdditionalInfoFile(outputFile, 8, 500, 450, 13, 123.456);

        List<String> lines = Files.readAllLines(Path.of(outputFile));

        assertEquals("8", lines.get(0));
        assertEquals("500", lines.get(1));
        assertEquals("450", lines.get(2));
        assertEquals("13", lines.get(3));
        assertEquals("123.456", lines.get(4)); // Sprawdza format Locale.US
    }
}