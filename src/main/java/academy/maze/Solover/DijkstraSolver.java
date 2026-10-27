package academy.maze.Solover;

import academy.maze.dto.CellType;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.*;

public class DijkstraSolver extends AbstractSolver {

    @Override
    protected Path findPath(CellType[][] grid, Point start, Point end, int width, int height) {
        int[][] distance = new int[height][width];
        Point[][] parent = new Point[height][width];
        boolean[][] visited = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            Arrays.fill(distance[y], Integer.MAX_VALUE);
        }

        // Используем TreeSet для автоматического обновления приоритета
        TreeSet<Point> queue = new TreeSet<>(Comparator.<Point>comparingInt(p -> distance[p.y()][p.x()])
                .thenComparingInt(p -> p.x())
                .thenComparingInt(p -> p.y()));

        distance[start.y()][start.x()] = 0;
        parent[start.y()][start.x()] = null;
        queue.add(start);

        while (!queue.isEmpty()) {
            Point current = queue.pollFirst();

            if (visited[current.y()][current.x()]) continue;
            visited[current.y()][current.x()] = true;

            if (current.equals(end)) {
                return reconstructPath(parent, end);
            }

            for (Map.Entry<Point, Integer> entry :
                    getNeighborsWithCost(current, grid, width, height, visited).entrySet()) {
                Point neighbor = entry.getKey();
                int cost = entry.getValue();

                int newDist = distance[current.y()][current.x()] + cost;

                if (newDist < distance[neighbor.y()][neighbor.x()]) {
                    // Удаляем старую версию
                    queue.remove(neighbor);

                    distance[neighbor.y()][neighbor.x()] = newDist;
                    parent[neighbor.y()][neighbor.x()] = current;
                    queue.add(neighbor);
                }
            }
        }

        return new Path(new Point[0]);
    }
}
