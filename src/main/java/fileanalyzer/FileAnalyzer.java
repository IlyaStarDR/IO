package fileanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {

    public static void main(String[] args) throws IOException, IllegalStateException {
        String pathToFile = args[0];
        String word = args[1];
        String fileContent = getStringContentOfFile(pathToFile);
        int wordOccurrencesInFile = getCountWordOccurrencesInString(fileContent, word);
        ArrayList<String> sentencesWordOccurrences = getSentencesInWhichWordOccurredInString(fileContent, word);
        printCountWordOccurrencesInFile(wordOccurrencesInFile, word);
        printSentencesInWhichWordOccurredInFile(sentencesWordOccurrences, word);
    }

    public static int getCountWordOccurrencesInString(String fileContent, String word) {
        Pattern pattern = Pattern.compile("\s*" + word + "\s*[,.?!]*");
        Matcher matcher = pattern.matcher(fileContent);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static ArrayList<String> getSentencesInWhichWordOccurredInString(String fileContent, String word) {
        Pattern pattern = Pattern.compile("\\s*[^.!?]*" + word + "\\s*[^.!?]*[.!?]");
        Matcher matcher = pattern.matcher(fileContent);

        ArrayList<String> sentences = new ArrayList<>();
        while (matcher.find()) {
            sentences.add(matcher.group().trim());
        }
        return sentences;
    }

    private static void printCountWordOccurrencesInFile(int count, String word) {
        System.out.println("Found " + count + " occurrences of word \033[3m" + word + "\033[0m in text");
    }

    private static void printSentencesInWhichWordOccurredInFile(ArrayList<String> sentences, String word) {
        for (String sentence : sentences) {
            System.out.println(sentence.replaceAll(word, "\u001B[32m" + word + "\u001B[0m"));
        }
    }

    public static String getStringContentOfFile(String pathToFile) throws IOException, IllegalStateException {
        File file = new File(pathToFile);
        if (!file.isFile()) {
            throw new IllegalStateException("File not found in path: " + pathToFile);
        }
        FileInputStream textFile = new FileInputStream(file);
        byte[] fileContent = textFile.readAllBytes();
        textFile.close();

        return new String(fileContent);
    }

}
