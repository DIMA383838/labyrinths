package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Solover.AStarSolver;
import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

class AStarSolverTest {

    @Test
    void testSolveSimpleMaze() {
        // Создаем простой лабиринт: прямой путь
        CellType[][] cells = {
            {CellType.PATH, CellType.PATH, CellType.PATH},
            {CellType.WALL, CellType.WALL, CellType.PATH},
            {CellType.PATH, CellType.PATH, CellType.PATH}
        };

        Maze maze = new Maze(cells);
        AStarSolver solver = new AStarSolver();

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        Path path = solver.solve(maze, start, end);

        assertNotNull(path);
        assertTrue(path.points().length > 0);
        assertEquals(start, path.points()[0]);
        assertEquals(end, path.points()[path.points().length - 1]);
    }

    @Test
    void testSolveNoPath() {
        // Лабиринт без пути
        CellType[][] cells = {
            {CellType.PATH, CellType.WALL, CellType.PATH},
            {CellType.WALL, CellType.WALL, CellType.WALL},
            {CellType.PATH, CellType.WALL, CellType.PATH}
        };

        Maze maze = new Maze(cells);
        AStarSolver solver = new AStarSolver();

        Point start = new Point(0, 0);
        Point end = new Point(2, 2);

        Path path = solver.solve(maze, start, end);

        assertNotNull(path);
        assertEquals(0, path.points().length);
    }

    @Test
    void testSolveWithInvalidStartPoint() {
        CellType[][] cells = {
            {CellType.WALL, CellType.PATH},
            {CellType.PATH, CellType.PATH}
        };

        Maze maze = new Maze(cells);
        AStarSolver solver = new AStarSolver();

        Point start = new Point(0, 0); // Стена
        Point end = new Point(1, 1);

        assertThrows(IllegalArgumentException.class, () -> solver.solve(maze, start, end));
    }

    @Test
    void testSolveWithInvalidEndPoint() {
        CellType[][] cells = {
            {CellType.PATH, CellType.PATH},
            {CellType.PATH, CellType.WALL}
        };

        Maze maze = new Maze(cells);
        AStarSolver solver = new AStarSolver();

        Point start = new Point(0, 0);
        Point end = new Point(1, 1); // Стена

        assertThrows(IllegalArgumentException.class, () -> solver.solve(maze, start, end));
    }
}
