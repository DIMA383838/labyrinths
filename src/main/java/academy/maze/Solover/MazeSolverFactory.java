package academy.maze.Solover;

public class MazeSolverFactory {
    public static Solver createSolver(SolverType type) {
        switch (type) {
            case BFS:
                return new BFSSolver();
            case ASTAR:
                return new AStarSolver();
            case DIJKSTRA:
                return new DijkstraSolver();
            default:
                throw new IllegalArgumentException("Неизвестный тип решателя: " + type);
        }
    }
}
