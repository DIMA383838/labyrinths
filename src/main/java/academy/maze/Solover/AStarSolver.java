package academy.maze.Solover;

import academy.maze.dto.CellType;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.*;

public class AStarSolver extends AbstractSolver {

    @Override
    protected Path findPath(CellType[][] grid, Point start, Point end, int width, int height) {
        int[][] gScore = new int[height][width];
        Point[][] parent = new Point[height][width];
        boolean[][] visited = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            Arrays.fill(gScore[y], Integer.MAX_VALUE);
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        gScore[start.y()][start.x()] = 0;
        parent[start.y()][start.x()] = null;
        openSet.add(new Node(start, manhattanDistance(start, end)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Point currentPoint = current.point;

            if (visited[currentPoint.y()][currentPoint.x()]) continue;
            visited[currentPoint.y()][currentPoint.x()] = true;

            if (currentPoint.equals(end)) {
                return reconstructPath(parent, end);
            }

            for (Map.Entry<Point, Integer> entry : getNeighborsWithCost(currentPoint, grid, width, height, visited)
                    .entrySet()) {
                Point neighbor = entry.getKey();
                int cost = entry.getValue();

                int tentativeG = gScore[currentPoint.y()][currentPoint.x()] + cost;

                if (tentativeG < gScore[neighbor.y()][neighbor.x()]) {
                    parent[neighbor.y()][neighbor.x()] = currentPoint;
                    gScore[neighbor.y()][neighbor.x()] = tentativeG;
                    int fScore = tentativeG + manhattanDistance(neighbor, end);

                    // Удаляем старую версию если есть
                    openSet.removeIf(node -> node.point.equals(neighbor));
                    openSet.add(new Node(neighbor, fScore));
                }
            }
        }

        return new Path(new Point[0]);
    }

    private record Node(Point point, int f) implements Comparable<Node> {
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }
}
