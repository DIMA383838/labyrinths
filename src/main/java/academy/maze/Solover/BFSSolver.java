package academy.maze.Solover;

import academy.maze.dto.CellType;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.*;

public class BFSSolver extends AbstractSolver {

    @Override
    protected Path findPath(CellType[][] grid, Point start, Point end, int width, int height) {
        boolean[][] visited = new boolean[height][width];
        Point[][] parent = new Point[height][width];

        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        visited[start.y()][start.x()] = true;
        parent[start.y()][start.x()] = null;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.equals(end)) {
                return reconstructPath(parent, end);
            }

            for (int[] dir : DIRECTIONS) {
                int newX = current.x() + dir[0];
                int newY = current.y() + dir[1];

                if (isValidCell(newX, newY, width, height, grid) && !visited[newY][newX]) {
                    Point neighbor = new Point(newX, newY);
                    queue.add(neighbor);
                    visited[newY][newX] = true;
                    parent[newY][newX] = current;
                }
            }
        }

        return new Path(new Point[0]);
    }
}
