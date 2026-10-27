package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Solover.DijkstraSolver;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

class DijkstraSolverTest {

    @Test
    void testSolveSimpleMaze() {
        CellType[][] cells = {
            {CellType.PATH, CellType.PATH, CellType.PATH},
            {CellType.WALL, CellType.WALL, CellType.PATH},
            {CellType.PATH, CellType.PATH, CellType.PATH}
        };

        Maze maze = new Maze(cells);
        DijkstraSolver solver = new DijkstraSolver();

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        Path path = solver.solve(maze, start, end);

        assertNotNull(path);
        assertTrue(path.points().length > 0);
        assertEquals(start, path.points()[0]);
        assertEquals(end, path.points()[path.points().length - 1]);
    }

    @Test
    void testSolveDirectPath() {
        // Прямой путь по горизонтали
        CellType[][] cells = {{CellType.PATH, CellType.PATH, CellType.PATH}};

        Maze maze = new Maze(cells);
        DijkstraSolver solver = new DijkstraSolver();

        Point start = new Point(0, 0);
        Point end = new Point(2, 0);

        Path path = solver.solve(maze, start, end);

        assertEquals(3, path.points().length);
        assertEquals(start, path.points()[0]);
        assertEquals(end, path.points()[2]);
    }

    @Test
    void testSolveNoPath() {
        CellType[][] cells = {
            {CellType.PATH, CellType.WALL},
            {CellType.WALL, CellType.PATH}
        };

        Maze maze = new Maze(cells);
        DijkstraSolver solver = new DijkstraSolver();

        Point start = new Point(0, 0);
        Point end = new Point(1, 1);

        Path path = solver.solve(maze, start, end);

        assertNotNull(path);
        assertEquals(0, path.points().length);
    }
}
