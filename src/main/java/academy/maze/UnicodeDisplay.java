package academy.maze;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import java.util.Set;

public class UnicodeDisplay {
    private static final char UNICODE_WALL = '█';
    private static final char UNICODE_PATH = ' ';
    private static final char UNICODE_START = 'S';
    private static final char UNICODE_END = 'E';
    private static final char UNICODE_PATH_MARKER = '·';
    private static final char UNICODE_CORNER_TOP_LEFT = '┌';
    private static final char UNICODE_CORNER_TOP_RIGHT = '┐';
    private static final char UNICODE_CORNER_BOTTOM_LEFT = '└';
    private static final char UNICODE_CORNER_BOTTOM_RIGHT = '┘';
    private static final char UNICODE_HORIZONTAL = '─';
    private static final char UNICODE_VERTICAL = '│';

    public static void printMazeWithUnicode(Maze maze, boolean showCoordinates) {
        printMazeWithUnicode(maze, null, null, null, showCoordinates);
    }

    public static void printMazeWithUnicode(Maze maze, Path path, Point start, Point end, boolean showCoordinates) {
        CellType[][] cells = maze.cells();
        int width = cells[0].length;
        int height = cells.length;

        Set<Point> pathPoints = path != null ? Set.of(path.points()) : Set.of();

        if (showCoordinates) {
            // Верхняя шкала X
            System.out.print("   ");
            for (int x = 0; x < width; x++) {
                System.out.print(String.format("%2d", x));
            }
            System.out.println();
        }

        // Верхняя граница
        if (showCoordinates) {
            System.out.print("  ");
        }
        System.out.print(UNICODE_CORNER_TOP_LEFT);
        for (int x = 0; x < width; x++) {
            System.out.print(UNICODE_HORIZONTAL + "" + UNICODE_HORIZONTAL);
        }
        System.out.println(UNICODE_CORNER_TOP_RIGHT);

        // Сам лабиринт
        for (int y = 0; y < height; y++) {
            if (showCoordinates) {
                System.out.print(String.format("%2d", y));
            }
            System.out.print(UNICODE_VERTICAL);

            for (int x = 0; x < width; x++) {
                Point current = new Point(x, y);
                char symbol;

                if (current.equals(start)) {
                    symbol = UNICODE_START;
                } else if (current.equals(end)) {
                    symbol = UNICODE_END;
                } else if (path != null && pathPoints.contains(current)) {
                    symbol = UNICODE_PATH_MARKER;
                } else {
                    symbol = cells[y][x] == CellType.WALL ? UNICODE_WALL : UNICODE_PATH;
                }

                System.out.print(symbol + " ");
            }
            System.out.println(UNICODE_VERTICAL);
        }

        // Нижняя граница
        if (showCoordinates) {
            System.out.print("  ");
        }
        System.out.print(UNICODE_CORNER_BOTTOM_LEFT);
        for (int x = 0; x < width; x++) {
            System.out.print(UNICODE_HORIZONTAL + "" + UNICODE_HORIZONTAL);
        }
        System.out.println(UNICODE_CORNER_BOTTOM_RIGHT);

        // Легенда
        printLegend(start, end, path != null);
    }

    private static void printLegend(Point start, Point end, boolean hasPath) {
        System.out.println("\nЛегенда Unicode:");
        System.out.println(UNICODE_WALL + " - стена");
        System.out.println(UNICODE_PATH + " - путь");
        if (start != null) {
            System.out.println(UNICODE_START + " - начальная точка (" + start.x() + ", " + start.y() + ")");
        }
        if (end != null) {
            System.out.println(UNICODE_END + " - конечная точка (" + end.x() + ", " + end.y() + ")");
        }
        if (hasPath) {
            System.out.println(UNICODE_PATH_MARKER + " - путь решения");
        }
    }
}
