package academy.maze.Generator;

import academy.maze.dto.CellType;
import java.util.*;

public class PrimMazeGenerator extends AbstractMazeGenerator {

    @Override
    protected void generateMaze(CellType[][] grid, int width, int height) {
        List<int[]> walls = new ArrayList<>();

        int startX = 1 + 2 * random.nextInt((width - 2) / 2);
        int startY = 1 + 2 * random.nextInt((height - 2) / 2);
        grid[startY][startX] = CellType.PATH;

        addWalls(startX, startY, width, height, walls);

        while (!walls.isEmpty()) {
            int[] wall = walls.remove(random.nextInt(walls.size()));
            int wallX = wall[0], wallY = wall[1];
            int cellX = wall[2], cellY = wall[3];

            // Проверяем, что одна сторона - путь, другая - стена
            if (isValidCell(cellX, cellY, width, height) && grid[cellY][cellX] == CellType.WALL) {
                // Делаем стену путем и добавляем новую клетку
                grid[wallY][wallX] = CellType.PATH;
                grid[cellY][cellX] = CellType.PATH;

                // Добавляем стены новой клетки
                addWalls(cellX, cellY, width, height, walls);
            }
        }
    }

    private void addWalls(int x, int y, int width, int height, List<int[]> walls) {
        for (int[] dir : DIRECTIONS) {
            int wallX = x + dir[0] / 2;
            int wallY = y + dir[1] / 2;
            int cellX = x + dir[0];
            int cellY = y + dir[1];

            if (isValidCell(cellX, cellY, width, height)) {
                walls.add(new int[] {wallX, wallY, cellX, cellY});
            }
        }
    }
}
