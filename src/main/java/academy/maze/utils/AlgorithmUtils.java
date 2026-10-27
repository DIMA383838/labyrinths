package academy.maze.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AlgorithmUtils {

    /** Возвращает enum по имени алгоритма */
    public static <T extends Enum<T> & AlgorithmEnum> T fromString(Class<T> enumType, String algorithm) {
        if (algorithm == null || algorithm.trim().isEmpty()) {
            throw new IllegalArgumentException("Algorithm name cannot be null or empty");
        }

        String normalized = algorithm.toLowerCase().trim();

        for (T enumValue : enumType.getEnumConstants()) {
            if (enumValue.getName().equals(normalized)) {
                return enumValue;
            }
        }

        throw new IllegalArgumentException(
                "Unknown algorithm: " + algorithm + ". Supported algorithms: " + getSupportedAlgorithms(enumType));
    }

    /** Проверяет, поддерживается ли указанный алгоритм */
    public static <T extends Enum<T> & AlgorithmEnum> boolean isSupported(Class<T> enumType, String algorithm) {
        if (algorithm == null || algorithm.trim().isEmpty()) {
            return false;
        }

        String normalized = algorithm.toLowerCase().trim();

        for (T enumValue : enumType.getEnumConstants()) {
            if (enumValue.getName().equals(normalized)) {
                return true;
            }
        }
        return false;
    }

    /** Возвращает список всех поддерживаемых алгоритмов */
    public static <T extends Enum<T> & AlgorithmEnum> String getSupportedAlgorithms(Class<T> enumType) {
        return Arrays.stream(enumType.getEnumConstants())
                .map(AlgorithmEnum::getName)
                .collect(Collectors.joining(", "));
    }
}
