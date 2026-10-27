package academy.maze.utils;

import academy.maze.dto.CellType;
import academy.maze.dto.Point;
import java.util.*;

public class MazeUtils {
    public static final int[][] DIRECTIONS_4 = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    public static final int[][] DIRECTIONS_8 = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}, {-1, -1}, {1, -1}, {1, 1}, {-1, 1}};

    /** Универсальный метод для получения соседних клеток с учетом стоимости */
    public static List<int[]> getNeighbors(
            int x, int y, int width, int height, CellType[][] grid, boolean[][] visited, int[][] directions) {
        List<int[]> neighbors = new ArrayList<>();

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (isValidCell(newX, newY, width, height)
                    && (grid == null || grid[newY][newX].isPassable())
                    && (visited == null || !visited[newY][newX])) {
                neighbors.add(new int[] {newX, newY});
            }
        }
        return neighbors;
    }

    /** Получает стоимость перехода в клетку */
    public static int getCellCost(int x, int y, CellType[][] grid) {
        if (!isValidCell(x, y, grid[0].length, grid.length)) {
            return Integer.MAX_VALUE;
        }
        return grid[y][x].getCost();
    }

    /** Получает соседей с их стоимостью */
    public static Map<Point, Integer> getNeighborsWithCost(
            Point current, CellType[][] grid, int width, int height, int[][] directions) {
        Map<Point, Integer> neighbors = new HashMap<>();

        for (int[] dir : directions) {
            int newX = current.x() + dir[0];
            int newY = current.y() + dir[1];

            if (isValidCell(newX, newY, width, height) && grid[newY][newX].isPassable()) {
                Point neighbor = new Point(newX, newY);
                int cost = getCellCost(newX, newY, grid);
                neighbors.put(neighbor, cost);
            }
        }
        return neighbors;
    }

    public static boolean isValidCell(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public static boolean isValidGenerationCell(int x, int y, int width, int height) {
        return x >= 1 && x < width - 1 && y >= 1 && y < height - 1;
    }

    public static void removeWallBetween(int currentX, int currentY, int neighborX, int neighborY, CellType[][] grid) {
        int wallX = currentX + (neighborX - currentX) / 2;
        int wallY = currentY + (neighborY - currentY) / 2;
        grid[wallY][wallX] = CellType.PATH;
    }

    public static void validatePoints(Point start, Point end, CellType[][] grid, int width, int height) {
        if (!isValidPoint(start, width, height) || grid[start.y()][start.x()].isObstacle()) {
            throw new IllegalArgumentException("Начальная точка невалидна или находится в стене");
        }
        if (!isValidPoint(end, width, height) || grid[end.y()][end.x()].isObstacle()) {
            throw new IllegalArgumentException("Конечная точка невалидна или находится в стене");
        }
    }

    public static boolean isValidPoint(Point point, int width, int height) {
        return point.x() >= 0 && point.x() < width && point.y() >= 0 && point.y() < height;
    }
}
