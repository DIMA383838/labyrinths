// FileManagerTest.java
package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.FileManager;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileManagerTest {
    private FileManager fileManager;
    private Maze testMaze;

    @BeforeEach
    void setUp() {
        // Создаем тестовый лабиринт
        CellType[][] cells = {
            {CellType.WALL, CellType.PATH, CellType.WALL},
            {CellType.PATH, CellType.PATH, CellType.PATH},
            {CellType.WALL, CellType.PATH, CellType.WALL}
        };
        testMaze = new Maze(cells);
        fileManager = new FileManager();
    }

    @Test
    void testSaveAndLoadMaze() throws IOException {
        // Сохраняем лабиринт
        String filename = fileManager.saveMazeToFile(testMaze, "testmaze1");
        assertNotNull(filename);
        assertTrue(Files.exists(Paths.get(filename)));

        // Загружаем лабиринт
        Maze loadedMaze = fileManager.loadMaze(filename);
        assertNotNull(loadedMaze);
    }

    @Test
    void testSaveAndLoadSolution() {
        Point start = new Point(1, 0);
        Point end = new Point(1, 2);
        Point[] pathPoints = {start, new Point(1, 1), end};
        Path path = new Path(pathPoints);

        String filename = fileManager.saveSolutionToFile(testMaze, path, start, end, "testsulution");
        assertNotNull(filename);
        assertTrue(Files.exists(Paths.get(filename)));
    }

    @Test
    void testGetSavedMazes() {
        // Сохраняем несколько лабиринтов
        fileManager.saveMazeToFile(testMaze, "testmaze2");
        fileManager.saveMazeToFile(testMaze, "testmaze3");

        List<String> savedMazes = fileManager.getSavedMazes();
        assertNotNull(savedMazes);
        assertTrue(savedMazes.size() >= 2);
    }

    @Test
    void testGetSavedSolutions() {
        Point start = new Point(1, 0);
        Point end = new Point(1, 2);
        Point[] pathPoints = {start, new Point(1, 1), end};
        Path path = new Path(pathPoints);

        fileManager.saveSolutionToFile(testMaze, path, start, end, "testsolution1");
        fileManager.saveSolutionToFile(testMaze, path, start, end, "testsolution2");

        List<String> savedSolutions = fileManager.getSavedSolutions();
        assertNotNull(savedSolutions);
        assertTrue(savedSolutions.size() >= 2);
    }

    @Test
    void testLoadNonExistentMaze() {
        assertThrows(Exception.class, () -> fileManager.loadMaze("nonexistent.txt"));
    }

    @AfterEach
    void tearDown() throws IOException {
        // Очистка тестовых файлов не требуется, так как @TempDir автоматически очищается
        // JUnit сам управляет временной директорией
    }

    @Test
    void testSaveMazeToFile() {
        String customFilename = "test_maze.txt";
        String filename = fileManager.saveMazeToFile(testMaze, customFilename);
        assertNotNull(filename);
        assertTrue(filename.contains(customFilename));
        assertTrue(Files.exists(Paths.get(filename)));
    }

    @Test
    void testSaveSolutionToFile() {
        Point start = new Point(1, 0);
        Point end = new Point(1, 2);
        Point[] pathPoints = {start, new Point(1, 1), end};
        Path path = new Path(pathPoints);

        String customFilename = "test_solution.txt";
        String filename = fileManager.saveSolutionToFile(testMaze, path, start, end, customFilename);
        assertNotNull(filename);
        assertTrue(filename.contains(customFilename));
        assertTrue(Files.exists(Paths.get(filename)));
    }
}
