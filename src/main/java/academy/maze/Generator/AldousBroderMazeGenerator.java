package academy.maze.Generator;

import academy.maze.dto.CellType;

public class AldousBroderMazeGenerator extends AbstractMazeGenerator {

    @Override
    protected void generateMaze(CellType[][] grid, int width, int height) {
        int currentX = 1 + 2 * random.nextInt((width - 2) / 2);
        int currentY = 1 + 2 * random.nextInt((height - 2) / 2);
        grid[currentY][currentX] = CellType.PATH;

        int unvisited = (width - 1) / 2 * (height - 1) / 2 - 1;

        while (unvisited > 0) {
            int[] dir = DIRECTIONS[random.nextInt(4)];
            int newX = currentX + dir[0];
            int newY = currentY + dir[1];

            if (isValidCell(newX, newY, width, height)) {
                if (grid[newY][newX] == CellType.WALL) {
                    removeWallBetween(currentX, currentY, newX, newY, grid);
                    grid[newY][newX] = CellType.PATH;
                    unvisited--;
                }
                currentX = newX;
                currentY = newY;
            }
            // Если клетка невалидна - остаёмся на месте и пробуем снова
        }
    }
}
