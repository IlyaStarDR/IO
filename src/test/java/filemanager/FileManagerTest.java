package filemanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {
    private final static String path = "src/test/resources/filemanager/testdata";
    private final static FileManager fileManager = new FileManager();

    @BeforeEach
    public void generateTestData() throws IOException {
        File folder1Folder1 = new File(path + "/folder1/folder1");
        File folder1Folder2 = new File(path + "/folder1/folder2");
        File folder2Folder1 = new File(path + "/folder2/folder1");
        File folder2Folder2 = new File(path + "/folder2/folder2");
        folder1Folder1.mkdirs();
        folder1Folder2.mkdirs();
        folder2Folder1.mkdirs();
        folder2Folder2.mkdirs();
        File folder1Folder1File1 = new File(path + "/folder1/folder1/file1.txt");
        File folder1File1 = new File(path + "/folder1/file1.txt");
        File folder2File2 = new File(path + "/folder2/file2.txt");
        File folder2Folder1File2 = new File(path + "/folder2/folder1/file2.txt");
        File folder2Folder2File2 = new File(path + "/folder2/folder2/file2.txt");
        folder1Folder1File1.createNewFile();
        folder1File1.createNewFile();
        folder2File2.createNewFile();
        folder2Folder1File2.createNewFile();
        folder2Folder2File2.createNewFile();
    }

    @DisplayName("Test method returns count of files in directories and subdirectories")
    @Test
    public void testReturnCountFilesInDirectoryAndSubdirectories() {
        int actual = fileManager.countFiles(path);
        assertEquals(5, actual, "Number of files do not match");
    }

    @DisplayName("Test method returns 0 files if path is null")
    @Test
    public void testReturnZeroCountFilesIfPathIsNull() {
        int actual = fileManager.countFiles(path + "/notExistingFolder");
        assertEquals(0, actual, "Number of files do not match");
    }

    @DisplayName("Test method returns 0 files if directories and subdirectories are empty")
    @Test
    public void testReturnZeroCountFilesIfDirectoryIsEmpty() {
        int actual = fileManager.countFiles(path + "/folder1/folder2");
        assertEquals(0, actual, "Number of files do not match");
    }

    @DisplayName("Test method returns count of directories and subdirectories")
    @Test
    public void testReturnCountDirectoriesInDirectoryAndSubdirectories() {
        int actual = fileManager.countDirs(path);
        assertEquals(6, actual, "Number of directories do not match");
    }

    @DisplayName("Test method returns 0 directories if path is null")
    @Test
    public void testReturnZeroCountDirectoriesIfPathIsNull() {
        int actual = fileManager.countDirs(path + "/notExistingDirectory");
        assertEquals(0, actual, "Number of directories do not match");
    }

    @DisplayName("Test method returns 0 directories if directories and subdirectories are empty")
    @Test
    public void testReturnZeroCountDirectoriesIfDirectoryIsEmpty() {
        int actual = fileManager.countDirs(path + "/folder1/folder2");
        assertEquals(0, actual, "Number of directories do not match");
    }

    @DisplayName("Test method copy directory with subdirectories to directory not exists")
    @Test
    public void testCopyDirectoryWithSubdirectoriesAndFilesToDirectoryNotExists() throws IOException {
        String toPath = path + "1";
        fileManager.copy(path, toPath);
        int actualCountOfFiles = fileManager.countFiles(toPath);
        int actualCountOfDirectories = fileManager.countDirs(toPath);
        assertEquals(5, actualCountOfFiles, "Number of files do not match");
        assertEquals(6, actualCountOfDirectories, "Number of directories do not match");
        assertTrue(new File(toPath).exists(), "Directory " + toPath + " doesn't exist");
    }

    @DisplayName("Test method copy directory with subdirectories to directory exists")
    @Test
    public void testCopyDirectoryWithSubdirectoriesAndFilesToDirectoryExists() throws IOException {
        String toPath = path + "/folder2/folder1";
        fileManager.copy(path + "/folder1", toPath);
        int actualCountOfFiles = fileManager.countFiles(toPath);
        int actualCountOfDirectories = fileManager.countDirs(toPath);
        assertEquals(3, actualCountOfFiles, "Number of files do not match");
        assertEquals(2, actualCountOfDirectories, "Number of directories do not match");
    }

    @DisplayName("Test method move directory with subdirectories to directory files to another directory")
    @Test
    public void testMoveDirectoryWithSubdirectoriesAndFilesToDirectory() throws IOException {
        String fromPath = path + "/folder1";
        String toPath = path + "/folder2";
        fileManager.move(fromPath, toPath);
        int actualCountOfFiles = fileManager.countFiles(path);
        int actualCountOfDirectories = fileManager.countDirs(path);
        File notExistingDirectory = new File(fromPath);
        assertFalse(notExistingDirectory.exists(), "Directory exists");
        assertEquals(5, actualCountOfFiles, "Number of files do not match");
        assertEquals(3, actualCountOfDirectories, "Number of directories do not match");
    }

    @AfterEach
    public void deleteTestDataOnTestComplete() {
        deleteDirectory(path);
        deleteDirectory(path + "1");
        deleteDirectory(path + "/folder2/folder1");
    }

    private void deleteDirectory(String path) {
        File directory = new File(path);
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file.getPath());
            }
        }
        directory.delete();
    }
}