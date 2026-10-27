package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Generator.PrimMazeGenerator;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import org.junit.jupiter.api.Test;

class PrimMazeGeneratorTest {

    @Test
    void testGenerateValidMaze() {
        PrimMazeGenerator generator = new PrimMazeGenerator();
        Maze maze = generator.generate(7, 7);

        assertNotNull(maze);
        assertEquals(7, maze.cells().length);
        assertEquals(7, maze.cells()[0].length);
    }

    @Test
    void testMazeHasPaths() {
        PrimMazeGenerator generator = new PrimMazeGenerator();
        Maze maze = generator.generate(5, 5);

        CellType[][] cells = maze.cells();
        boolean hasPath = false;

        // Проверяем, что есть хотя бы одна свободная ячейка
        for (int y = 0; y < cells.length; y++) {
            for (int x = 0; x < cells[y].length; x++) {
                if (cells[y][x] == CellType.PATH) {
                    hasPath = true;
                    break;
                }
            }
            if (hasPath) break;
        }

        assertTrue(hasPath, "Maze should have at least one path cell");
    }

    @Test
    void testMazeHasEntranceAndExit() {
        PrimMazeGenerator generator = new PrimMazeGenerator();
        Maze maze = generator.generate(7, 7);

        CellType[][] cells = maze.cells();

        // Проверяем вход (1,0)
        assertEquals(CellType.PATH, cells[1][0]);

        // Проверяем выход (height-2, width-1)
        assertEquals(CellType.PATH, cells[5][6]);
    }
}
