package fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerITest {
    private static final String PATH_EMPTY_TEST_FILE = "src/test/resources/fileanalyzer/test_data_empty.txt";
    private static final String PATH_TEST_FILE = "src/test/resources/fileanalyzer/test_data.txt";
    private static final String PATH_NOT_FOUND_FILE = "src/test/resources/fileanalyzer/testdata_empty.txt";

    @DisplayName("Test method returns 0 if file is empty")
    @Test
    public void getCountWordOccurrencesReturnsZeroCountIfFileEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_EMPTY_TEST_FILE);
        int actualCount = FileAnalyzer.getCountWordOccurrencesInString(fileContent, "any");
        assertEquals(0, actualCount, "Counts do not match");
    }

    @DisplayName("Test method returns 0 if file is not empty but not found occurrences")
    @Test
    public void getCountWordOccurrencesReturnsZeroCountIfFileNotEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_TEST_FILE);
        int actualCount = FileAnalyzer.getCountWordOccurrencesInString(fileContent, "milk");
        assertEquals(0, actualCount, "Counts do not match");
    }

    @DisplayName("Test method returns count if file is not empty")
    @Test
    public void getCountWordOccurrencesReturnsCountIfFileNotEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_TEST_FILE);
        int actualCount = FileAnalyzer.getCountWordOccurrencesInString(fileContent, "it");
        assertEquals(9, actualCount, "Counts do not match");
    }

    @DisplayName("Test method returns empty list if file is empty")
    @Test
    public void getSentencesInWhichWordOccurredReturnsEmptyListIfFileEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_EMPTY_TEST_FILE);
        ArrayList<String> actualList = FileAnalyzer.getSentencesInWhichWordOccurredInString(fileContent, "any");
        assertEquals(new ArrayList<String>(), actualList, "Lists do not match");
    }

    @DisplayName("Test method throws IllegalStateException when file is not found")
    @Test
    public void getStringContentOfFileThrowsWhenFileIsNotFound() {
        assertThrows(IllegalStateException.class, () -> {
            FileAnalyzer.getStringContentOfFile(PATH_NOT_FOUND_FILE);
        });
    }

    @DisplayName("Test method returns empty list if file is not empty but not found occurrences")
    @Test
    public void getSentencesInWhichWordOccurredReturnsEmptyListIfFileNotEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_TEST_FILE);
        ArrayList<String> actualList = FileAnalyzer.getSentencesInWhichWordOccurredInString(fileContent, "milk");
        assertEquals(new ArrayList<String>(), actualList, "Lists do not match");
    }

    @DisplayName("Test method returns list with sentences if file is not empty")
    @Test
    public void getSentencesInWhichWordOccurredReturnsNotEmptyListIfFileNotEmpty() throws IOException {
        String fileContent = FileAnalyzer.getStringContentOfFile(PATH_TEST_FILE);
        ArrayList<String> expectedList = new ArrayList<>();
        expectedList.add("He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.");
        expectedList.add("The bedding was hardly able to cover it and seemed ready to slide off any moment.");
        expectedList.add("His many legs, pitifully thin compared with the size of the rest of him, waved about helplessly as he looked.");
        expectedList.add("His room, a proper human room although a little too small, lay peacefully between its four familiar walls.");
        expectedList.add("A collection of textile samples lay spread out on the table - Samsa was a travelling salesman - and above it there hung a picture that he had recently cut out of an illustrated magazine and housed in a nice, gilded frame.");
        expectedList.add("It showed a lady fitted out with a fur hat and fur boa who sat upright, raising a heavy fur muff that covered the whole of her lower arm towards the viewer.");
        ArrayList<String> actualList = FileAnalyzer.getSentencesInWhichWordOccurredInString(fileContent, "it");
        assertEquals(expectedList, actualList, "Lists do not match");
    }
}