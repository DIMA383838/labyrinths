package academy.maze.dto;

/** Тип ячейки в лабиринте с различными свойствами поверхностей */
public enum CellType {
    // Базовые типы
    WALL("#", 0, "Стена"),
    PATH(" ", 1, "Обычный путь"),

    // Ухудшающие поверхности
    SWAMP("~", 3, "Болото"),
    SAND("+", 2, "Песок"),
    MUD("&", 4, "Грязь"),

    // Улучшающие поверхности
    ROAD("=", 1, "Дорога"),
    COIN("$", 1, "Монетка"),
    BOOST("@", 1, "Ускорение"),

    START("O", 1, "Стартовая точка"),
    END("X", 1, "Конечная точка"),
    PATH_MARKER(".", 1, "Путь решения");

    private final String symbol;
    private final int cost;
    private final String description;

    CellType(String symbol, int cost, String description) {
        this.symbol = symbol;
        this.cost = cost;
        this.description = description;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPassable() {
        return this != WALL;
    }

    public boolean isObstacle() {
        return this == WALL;
    }

    public boolean isPathMarker() {
        return this == START || this == END || this == PATH_MARKER;
    }

    public boolean isSpecialSurface() {
        return this != WALL && this != PATH;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
