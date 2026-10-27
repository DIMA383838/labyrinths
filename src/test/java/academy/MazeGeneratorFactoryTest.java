package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.Generator.DFSMazeGenerator;
import academy.maze.Generator.Generator;
import academy.maze.Generator.GeneratorType;
import academy.maze.Generator.MazeGeneratorFactory;
import academy.maze.Generator.PrimMazeGenerator;
import org.junit.jupiter.api.Test;

class MazeGeneratorFactoryTest {

    @Test
    void testCreateDFSGenerator() {
        Generator generator = MazeGeneratorFactory.createGenerator(GeneratorType.DFS);
        assertNotNull(generator);
        assertTrue(generator instanceof DFSMazeGenerator);
    }

    @Test
    void testCreatePrimGenerator() {
        Generator generator = MazeGeneratorFactory.createGenerator(GeneratorType.PRIM);
        assertNotNull(generator);
        assertTrue(generator instanceof PrimMazeGenerator);
    }
}
