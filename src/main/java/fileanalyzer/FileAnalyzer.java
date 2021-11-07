package fileanalyzer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileAnalyzer {
    private String path;
    private String word;

    public static void main(String[] args) throws IOException, IllegalStateException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(args[0], args[1]);
        fileAnalyzer.printCountWordOccurrencesInFile();
        fileAnalyzer.printSentencesInWhichWordOccurredInFile();
    }

    protected FileAnalyzer(String path, String word) {
        this.path = path;
        this.word = word;
    }

    public int getCountWordOccurrencesInFile() throws IOException {
        String stringFileContent = getStringContentOfFile();

        Pattern pattern = Pattern.compile("\s*" + word + "\s*[,.?!]*");
        Matcher matcher = pattern.matcher(stringFileContent);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public ArrayList<String> getSentencesInWhichWordOccurredInFile() throws IOException {
        String stringFileContent = getStringContentOfFile();

        Pattern pattern = Pattern.compile("\\s*[^.!?]*" + word + "\\s*[^.!?]*[.!?]");
        Matcher matcher = pattern.matcher(stringFileContent);

        ArrayList<String> sentences = new ArrayList<>();
        while (matcher.find()) {
            sentences.add(matcher.group().trim());
        }
        return sentences;
    }

    private void printCountWordOccurrencesInFile() throws IOException {
        System.out.println("Found " + getCountWordOccurrencesInFile() + " occurrences of word \033[3m" + word + "\033[0m in text");
    }

    private void printSentencesInWhichWordOccurredInFile() throws IOException {
        ArrayList<String> sentences = getSentencesInWhichWordOccurredInFile();
        for (String sentence : sentences) {
            System.out.println(sentence.replaceAll(word, "\u001B[32m" + word + "\u001B[0m"));
        }
    }

    private String getStringContentOfFile() throws IOException, IllegalStateException {
        File file = new File(path);
        if (!file.isFile()) {
            throw new IllegalStateException("File not found in path: " + path);
        }
        FileInputStream textFile = new FileInputStream(file);
        byte[] fileContent = textFile.readAllBytes();
        textFile.close();

        return new String(fileContent);
    }

}
