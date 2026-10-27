package academy.maze.Generator;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.utils.MazeUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class AbstractMazeGenerator implements Generator {
    protected final Random random = new Random();
    static final int[][] DIRECTIONS = {{0, -2}, {2, 0}, {0, 2}, {-2, 0}};

    @Override
    public Maze generate(int width, int height) {
        validateDimensions(width, height);

        CellType[][] grid = initializeGrid(width, height);
        generateMaze(grid, width, height);
        addSpecialSurfaces(grid, width, height);
        addEntranceAndExit(grid, width, height);

        return new Maze(grid);
    }

    protected void validateDimensions(int width, int height) {
        if (width < 3 || height < 3) {
            throw new IllegalArgumentException("Ширина и высота должны быть не менее 3");
        }
    }

    protected CellType[][] initializeGrid(int width, int height) {
        CellType[][] grid = new CellType[height][width];
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], CellType.WALL);
        }
        return grid;
    }

    protected void addEntranceAndExit(CellType[][] grid, int width, int height) {
        grid[1][0] = CellType.PATH;
        grid[height - 2][width - 1] = CellType.PATH;
    }

    protected List<int[]> getUnvisitedNeighbors(
            int x, int y, int width, int height, boolean[][] visited, int[][] directions) {
        return MazeUtils.getNeighbors(x, y, width, height, null, visited, directions);
    }

    protected boolean isValidCell(int x, int y, int width, int height) {
        return MazeUtils.isValidGenerationCell(x, y, width, height);
    }

    protected void removeWallBetween(int currentX, int currentY, int neighborX, int neighborY, CellType[][] grid) {
        MazeUtils.removeWallBetween(currentX, currentY, neighborX, neighborY, grid);
    }

    protected void addSpecialSurfaces(CellType[][] grid, int width, int height) {

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                if (grid[y][x] == CellType.PATH) {
                    double rand = random.nextDouble();

                    // 60% - остаётся обычным путём (стоимость 1)
                    // 40% - получает специальную поверхность
                    if (rand < 0.10) { // 10% - болото (высокая стоимость)
                        grid[y][x] = CellType.SWAMP;
                    } else if (rand < 0.20) { // 10% - песок (средняя стоимость)
                        grid[y][x] = CellType.SAND;
                    } else if (rand < 0.25) { // 5% - грязь (очень высокая стоимость)
                        grid[y][x] = CellType.MUD;
                    } else if (rand < 0.35) { // 10% - дорога (низкая стоимость, но визуально отличается)
                        grid[y][x] = CellType.ROAD;
                    } else if (rand < 0.37) { // 2% - монетка
                        grid[y][x] = CellType.COIN;
                    } else if (rand < 0.39) { // 2% - ускорение
                        grid[y][x] = CellType.BOOST;
                    }
                    // остальные 60% остаются CellType.PATH
                }
            }
        }
    }

    protected abstract void generateMaze(CellType[][] grid, int width, int height);
}
