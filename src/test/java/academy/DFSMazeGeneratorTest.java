package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Generator.DFSMazeGenerator;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import org.junit.jupiter.api.Test;

class DFSMazeGeneratorTest {

    @Test
    void testGenerateValidMaze() {
        DFSMazeGenerator generator = new DFSMazeGenerator();
        Maze maze = generator.generate(5, 5);

        assertNotNull(maze);
        assertNotNull(maze.cells());
        assertEquals(5, maze.cells().length);
        assertEquals(5, maze.cells()[0].length);
    }

    @Test
    void testGenerateMazeWithMinimumSize() {
        DFSMazeGenerator generator = new DFSMazeGenerator();
        Maze maze = generator.generate(3, 3);

        assertNotNull(maze);
        assertEquals(3, maze.cells().length);
        assertEquals(3, maze.cells()[0].length);
    }

    @Test
    void testGenerateMazeThrowsExceptionForSmallSize() {
        DFSMazeGenerator generator = new DFSMazeGenerator();

        assertThrows(IllegalArgumentException.class, () -> generator.generate(2, 2));
        assertThrows(IllegalArgumentException.class, () -> generator.generate(3, 2));
        assertThrows(IllegalArgumentException.class, () -> generator.generate(2, 3));
    }

    @Test
    void testMazeHasEntranceAndExit() {
        DFSMazeGenerator generator = new DFSMazeGenerator();
        Maze maze = generator.generate(7, 7);

        CellType[][] cells = maze.cells();

        // Проверяем вход (1,0)
        assertEquals(CellType.PATH, cells[1][0]);

        // Проверяем выход (height-2, width-1)
        assertEquals(CellType.PATH, cells[5][6]);
    }
}
