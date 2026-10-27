package academy.maze.Generator;

import academy.maze.dto.CellType;
import java.util.*;

public class DFSMazeGenerator extends AbstractMazeGenerator {

    @Override
    protected void generateMaze(CellType[][] grid, int width, int height) {
        boolean[][] visited = new boolean[height][width];
        Deque<int[]> stack = new ArrayDeque<>();

        int startX = 1;
        int startY = 1;
        grid[startY][startX] = CellType.PATH;
        visited[startY][startX] = true;
        stack.push(new int[] {startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0], y = current[1];

            List<int[]> unvisitedNeighbors = getUnvisitedNeighbors(x, y, width, height, visited, DIRECTIONS);
            if (!unvisitedNeighbors.isEmpty()) {
                int[] neighbor = unvisitedNeighbors.get(random.nextInt(unvisitedNeighbors.size()));
                removeWallBetween(x, y, neighbor[0], neighbor[1], grid);

                grid[neighbor[1]][neighbor[0]] = CellType.PATH;
                visited[neighbor[1]][neighbor[0]] = true;
                stack.push(neighbor);
            } else {
                stack.pop();
            }
        }
    }
}
