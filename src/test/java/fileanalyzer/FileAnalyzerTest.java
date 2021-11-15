package fileanalyzer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerTest {

    private static final String CONTENT_OF_FILE = """
            He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.
            The bedding was hardly able to cover it and seemed ready to slide off any moment.
            His room, a proper human room although a little too small, lay peacefully between its four familiar walls.
            """;

    @DisplayName("Returns count of occurrences is 0 if no matches of word found")
    @Test
    void testGetCountWordOccurrencesInStringReturnsZeroIfNoMatches() {
        int actual = FileAnalyzer.getCountWordOccurrencesInString(CONTENT_OF_FILE, "cat");
        assertEquals(0, actual, "Occurrences differ");
    }

    @DisplayName("Returns count of occurrences if matches of word found")
    @Test
    void testGetCountWordOccurrencesInStringReturnsIfMatches() {
        int actual = FileAnalyzer.getCountWordOccurrencesInString(CONTENT_OF_FILE, "it");
        assertEquals(4, actual, "Occurrences differ");
    }

    @DisplayName("Returns sentences of occurrences if matches of word found")
    @Test
    void testGetSentencesInWhichWordOccurredInStringIfMatches() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("He lay on his armour-like back, and if he lifted his head a little he could see his brown belly, slightly domed and divided by arches into stiff sections.");
        expected.add("The bedding was hardly able to cover it and seemed ready to slide off any moment.");
        expected.add("His room, a proper human room although a little too small, lay peacefully between its four familiar walls.");
        ArrayList<String> actual = FileAnalyzer.getSentencesInWhichWordOccurredInString(CONTENT_OF_FILE, "it");
        assertEquals(expected, actual, "Arrays differ");
    }

    @DisplayName("Returns empty sentences of occurrences if no matches of word found")
    @Test
    void testGetSentencesInWhichWordOccurredInStringIfNoMatches() {
        ArrayList<String> expected = new ArrayList<>();
        ArrayList<String> actual = FileAnalyzer.getSentencesInWhichWordOccurredInString(CONTENT_OF_FILE, "cat");
        assertEquals(expected, actual, "Arrays differ");
    }
}