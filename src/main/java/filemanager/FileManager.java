package filemanager;

import java.io.*;
import java.util.Objects;

public class FileManager {

    public int countFiles(String path) {
        File filePath = new File(path);
        int count = 0;
        File[] currentFilesInDirectory = filePath.listFiles();
        if (currentFilesInDirectory != null) {
            for (File file : currentFilesInDirectory) {
                if (file.isDirectory()) {
                    count += countFiles(file.toString());
                }
                if (file.isFile()) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countDirs(String path) {
        File filePath = new File(path);
        int count = 0;
        File[] currentFilesInDirectory = filePath.listFiles();
        if (currentFilesInDirectory != null) {
            for (File file : currentFilesInDirectory) {
                if (file.isDirectory()) {
                    count += countDirs(file.toString());
                    count++;
                }
            }
        }
        return count;
    }

    public void copy(String from, String to) throws IOException {
        File sourceDirectory = new File(from);
        File destinationDirectory = new File(to);
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdir();
        }

        for (String path : Objects.requireNonNull(sourceDirectory.list())) {
            File source = new File(sourceDirectory, path);
            File destination = new File(destinationDirectory, path);

            if (source.isDirectory()) {
                copy(source.toString(), destination.toString());
            }
            if (source.isFile()) {
                copyFile(source.toString(), destination.toString());
            }
        }
    }

    public void move(String from, String to) throws IOException {
        copy(from, to);
        deleteDirectory(from);
    }

    private void deleteDirectory(String path) {
        File directory = new File(path);
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file.getAbsolutePath());
            }
        }
        directory.delete();
    }

    private void copyFile(String from, String to) throws IOException {
        FileInputStream inputStream = new FileInputStream(from);
        FileOutputStream outputStream = new FileOutputStream(to);

        byte[] array = new byte[1024];
        int read;

        while ((read = inputStream.read(array)) > 0) {
            outputStream.write(array, 0, read);
        }
        inputStream.close();
        outputStream.close();
    }
}
