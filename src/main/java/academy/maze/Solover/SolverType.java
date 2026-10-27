package academy.maze.Solover;

import academy.maze.utils.AlgorithmEnum;
import academy.maze.utils.AlgorithmUtils;

public enum SolverType implements AlgorithmEnum {
    ASTAR("astar"),
    DIJKSTRA("dijkstra"),
    BFS("bfs");

    private final String name;

    SolverType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /** Проверяет, поддерживается ли указанный алгоритм */
    public static boolean isSupported(String algorithm) {
        return AlgorithmUtils.isSupported(SolverType.class, algorithm);
    }

    /** Возвращает тип решателя по имени алгоритма */
    public static SolverType fromString(String algorithm) {
        return AlgorithmUtils.fromString(SolverType.class, algorithm);
    }

    /** Возвращает список всех поддерживаемых алгоритмов */
    public static String getSupportedAlgorithms() {
        return AlgorithmUtils.getSupportedAlgorithms(SolverType.class);
    }
}
