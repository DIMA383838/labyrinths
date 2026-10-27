package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Solover.AStarSolver;
import academy.maze.Solover.DijkstraSolver;
import academy.maze.Solover.MazeSolverFactory;
import academy.maze.Solover.Solver;
import academy.maze.Solover.SolverType;
import org.junit.jupiter.api.Test;

class MazeSolverFactoryTest {

    @Test
    void testCreateAStarSolver() {
        Solver solver = MazeSolverFactory.createSolver(SolverType.ASTAR);
        assertNotNull(solver);
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testCreateDijkstraSolver() {
        Solver solver = MazeSolverFactory.createSolver(SolverType.DIJKSTRA);
        assertNotNull(solver);
        assertTrue(solver instanceof DijkstraSolver);
    }
}
