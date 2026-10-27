package academy.maze.Generator;

/** Фабрика для создания генераторов лабиринта */
public class MazeGeneratorFactory {

    public static Generator createGenerator(GeneratorType type) {
        switch (type) {
            case ALDOUSBRODER:
                return new AldousBroderMazeGenerator();
            case DFS:
                return new DFSMazeGenerator();
            case PRIM:
                return new PrimMazeGenerator();
            default:
                throw new IllegalArgumentException("Неизвестный тип генератора: " + type);
        }
    }
}
