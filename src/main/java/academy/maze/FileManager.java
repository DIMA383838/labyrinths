package academy.maze;

import academy.maze.dto.CellType;
import academy.maze.dto.Maze;
import academy.maze.dto.Point;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class FileManager {
    private static final String MAZES_DIR = "mazes";
    private static final String SOLUTIONS_DIR = "solutions";

    public FileManager() {
        try {
            createDirectories();
        } catch (Exception e) {
            System.err.println("Предупреждение: не удалось создать директории: " + e.getMessage());
        }
    }

    private void createDirectories() {
        try {
            Files.createDirectories(java.nio.file.Path.of(MAZES_DIR));
            Files.createDirectories(java.nio.file.Path.of(SOLUTIONS_DIR));
        } catch (IOException e) {
            throw new UncheckedIOException("Не удалось создать директории для сохранения файлов", e);
        }
    }

    /** Загружает лабиринт из TXT файла */
    public Maze loadMaze(String filename) {
        java.nio.file.Path filePath = java.nio.file.Path.of(filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            List<String> mazeLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                // Используем символы из CellType для проверки
                if (containsMazeCharacters(line)) {
                    mazeLines.add(line);
                }
            }

            if (mazeLines.isEmpty()) {
                throw new IOException("Файл не содержит данных лабиринта");
            }

            // Преобразуем строки в массив CellType
            int height = mazeLines.size();
            int width = mazeLines.get(0).length();
            CellType[][] cells = new CellType[height][width];

            for (int y = 0; y < height; y++) {
                String mazeLine = mazeLines.get(y);
                for (int x = 0; x < width; x++) {
                    char cellChar = mazeLine.charAt(x);
                    cells[y][x] = charToCellType(cellChar);
                }
            }

            return new Maze(cells);
        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка при загрузке лабиринта из файла: " + filename, e);
        }
    }

    /** Проверяет, содержит ли строка символы лабиринта */
    private boolean containsMazeCharacters(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }

        // Проверяем, содержит ли строка хотя бы один символ из CellType
        for (char c : line.toCharArray()) {
            if (isMazeCharacter(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMazeCharacter(char c) {
        // Используем все символы из CellType
        for (CellType cellType : CellType.values()) {
            if (cellType.getSymbol().charAt(0) == c) {
                return true;
            }
        }
        return false;
    }

    /** Преобразует символ в CellType */
    private CellType charToCellType(char cellChar) {
        // Ищем символ среди всех значений CellType
        for (CellType cellType : CellType.values()) {
            if (cellType.getSymbol().charAt(0) == cellChar) {
                // Специальные символы пути преобразуем в обычный PATH
                if (cellType.isPathMarker()) {
                    return CellType.PATH;
                }
                return cellType;
            }
        }

        // Если символ неизвестен, считаем его стеной для безопасности
        return CellType.WALL;
    }

    /** Получает список всех сохраненных лабиринтов */
    public List<String> getSavedMazes() {
        return getFilesInDirectory(MAZES_DIR, ".txt");
    }

    /** Получает список всех сохраненных решений */
    public List<String> getSavedSolutions() {
        return getFilesInDirectory(SOLUTIONS_DIR, ".txt");
    }

    private List<String> getFilesInDirectory(String directory, String extension) {
        try {
            java.nio.file.Path dirPath = java.nio.file.Path.of(directory);
            if (!Files.exists(dirPath) || !Files.isDirectory(dirPath)) {
                return new ArrayList<>();
            }

            List<String> fileList = new ArrayList<>();
            try (var stream = Files.list(dirPath)) {
                stream.filter(path -> path.toString().toLowerCase().endsWith(extension))
                        .sorted()
                        .forEach(path -> fileList.add(path.toString()));
            }
            return fileList;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /** Выводит содержимое файла решения в консоль */
    public void printSolutionFile(String filename) {
        java.nio.file.Path filePath = java.nio.file.Path.of(filename);
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            System.out.println("\n=== Содержимое файла решения: " + filename + " ===");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла решения: " + e.getMessage());
        }
    }

    public void printMaze(Maze maze) {
        for (CellType[] row : maze.cells()) {
            for (CellType cell : row) {
                System.out.print(cell.isObstacle() ? "#" : " ");
            }
            System.out.println();
        }
    }

    /** Сохраняет лабиринт в указанный файл */
    public String saveMazeToFile(Maze maze, String filename) {
        try {
            String fullFilename = MAZES_DIR + "/" + filename;
            java.nio.file.Path outputPath = java.nio.file.Path.of(fullFilename);
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outputPath))) {
                CellType[][] cells = maze.cells();
                for (int y = 0; y < cells.length; y++) {
                    for (int x = 0; x < cells[y].length; x++) {
                        writer.print(cells[y][x].getSymbol());
                    }
                    writer.println();
                }
            }
            return fullFilename;
        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка при сохранении лабиринта", e);
        }
    }

    /** Сохраняет решение в указанный файл */
    public String saveSolutionToFile(Maze maze, academy.maze.dto.Path path, Point start, Point end, String filename) {
        try {
            String fullFilename = SOLUTIONS_DIR + "/" + filename;
            java.nio.file.Path outputPath = java.nio.file.Path.of(fullFilename);
            try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(outputPath))) {
                CellType[][] cells = maze.cells();
                Set<Point> pathPoints = new HashSet<>(Arrays.asList(path.points()));

                for (int y = 0; y < cells.length; y++) {
                    for (int x = 0; x < cells[y].length; x++) {
                        Point current = new Point(x, y);
                        if (current.equals(start)) {
                            writer.print(CellType.START.getSymbol());
                        } else if (current.equals(end)) {
                            writer.print(CellType.END.getSymbol());
                        } else if (pathPoints.contains(current)) {
                            writer.print(CellType.PATH_MARKER.getSymbol());
                        } else {
                            writer.print(cells[y][x].getSymbol());
                        }
                    }
                    writer.println();
                }
            }
            return fullFilename;
        } catch (IOException e) {
            throw new UncheckedIOException("Ошибка при сохранении решения", e);
        }
    }
}
