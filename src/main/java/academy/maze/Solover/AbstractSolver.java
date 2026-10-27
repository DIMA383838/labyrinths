package academy.maze.Solover;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import academy.maze.utils.MazeUtils;
import java.util.*;

public abstract class AbstractSolver implements Solver {
    static final int[][] DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    @Override
    public Path solve(Maze maze, Point start, Point end) {
        CellType[][] grid = maze.cells();
        int width = grid[0].length;
        int height = grid.length;
        MazeUtils.validatePoints(start, end, grid, width, height);
        return findPath(grid, start, end, width, height);
    }

    protected Path reconstructPath(Point[][] parent, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;

        while (current != null) {
            path.add(current);
            current = parent[current.y()][current.x()];
        }

        Collections.reverse(path);
        return new Path(path.toArray(new Point[0]));
    }

    protected int manhattanDistance(Point a, Point b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    protected boolean isValidCell(int x, int y, int width, int height, CellType[][] grid) {
        return MazeUtils.isValidCell(x, y, width, height) && grid[y][x] == CellType.PATH;
    }
    /** Получает валидных соседей без учета стоимости */
    protected List<Point> getValidNeighbors(
            Point current, CellType[][] grid, int width, int height, boolean[][] visited) {
        List<int[]> neighborCoords =
                MazeUtils.getNeighbors(current.x(), current.y(), width, height, grid, visited, DIRECTIONS);

        List<Point> neighbors = new ArrayList<>();
        for (int[] coord : neighborCoords) {
            neighbors.add(new Point(coord[0], coord[1]));
        }
        return neighbors;
    }

    protected Map<Point, Integer> getNeighborsWithCost(
            Point current, CellType[][] grid, int width, int height, boolean[][] visited) {
        Map<Point, Integer> neighbors = new HashMap<>();

        List<Point> validNeighbors = getValidNeighbors(current, grid, width, height, visited);
        for (Point neighbor : validNeighbors) {
            int cost = grid[neighbor.y()][neighbor.x()].getCost();
            neighbors.put(neighbor, cost);
        }
        return neighbors;
    }

    protected abstract Path findPath(CellType[][] grid, Point start, Point end, int width, int height);
}
