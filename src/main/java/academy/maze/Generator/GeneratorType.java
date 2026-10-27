package academy.maze.Generator;

import academy.maze.utils.AlgorithmEnum;
import academy.maze.utils.AlgorithmUtils;

public enum GeneratorType implements AlgorithmEnum {
    DFS("dfs"),
    PRIM("prim"),
    ALDOUSBRODER("aldousbroder");

    private final String name;

    GeneratorType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    /** Проверяет, поддерживается ли указанный алгоритм */
    public static boolean isSupported(String algorithm) {
        return AlgorithmUtils.isSupported(GeneratorType.class, algorithm);
    }

    /** Возвращает тип генератора по имени алгоритма */
    public static GeneratorType fromString(String algorithm) {
        return AlgorithmUtils.fromString(GeneratorType.class, algorithm);
    }

    /** Возвращает список всех поддерживаемых алгоритмов */
    public static String getSupportedAlgorithms() {
        return AlgorithmUtils.getSupportedAlgorithms(GeneratorType.class);
    }
}
