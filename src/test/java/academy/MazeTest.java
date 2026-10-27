package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import org.junit.jupiter.api.Test;

class MazeTest {

    @Test
    void testMazeCreation() {
        CellType[][] cells = {
            {CellType.WALL, CellType.PATH, CellType.WALL},
            {CellType.PATH, CellType.PATH, CellType.PATH},
            {CellType.WALL, CellType.PATH, CellType.WALL}
        };

        Maze maze = new Maze(cells);
        assertNotNull(maze.cells());
        assertEquals(3, maze.cells().length);
        assertEquals(3, maze.cells()[0].length);
    }

    @Test
    void testMazeWithEmptyCells() {
        CellType[][] cells = new CellType[0][0];
        Maze maze = new Maze(cells);
        assertEquals(0, maze.cells().length);
    }
}
