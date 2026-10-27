package academy.maze.Command;

import academy.maze.FileManager;
import academy.maze.Generator.Generator;
import academy.maze.Generator.GeneratorType;
import academy.maze.Generator.MazeGeneratorFactory;
import academy.maze.UnicodeDisplay;
import academy.maze.dto.Maze;
import picocli.CommandLine;

@CommandLine.Command(name = "generate", description = "Generate a new maze")
public class GenerateCommand implements Runnable {
    @CommandLine.Option(names = "--algorithm", required = true, description = "Algorithm: dfs, prim, AldousBorder")
    private String algorithm;

    @CommandLine.Option(names = "--width", required = true, description = "Maze width")
    private int width;

    @CommandLine.Option(names = "--height", required = true, description = "Maze height")
    private int height;

    @CommandLine.Option(names = "--output", required = true, description = "Output file")
    private String output;

    @CommandLine.Option(names = "--unicode", description = "Use Unicode symbols for display")
    private boolean useUnicode = false;

    private FileManager fileManager;

    public GenerateCommand() {
        this.fileManager = new FileManager();
    }

    @Override
    public void run() {
        try {
            System.out.println("Generating maze with algorithm: " + algorithm);

            GeneratorType generatorType;
            try {
                generatorType = GeneratorType.fromString(algorithm);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }
            Generator generator = MazeGeneratorFactory.createGenerator(generatorType);
            Maze maze = generator.generate(width, height);

            String savedFilename = fileManager.saveMazeToFile(maze, output);
            System.out.println("Maze generated and saved to: " + savedFilename);

            if (useUnicode) {
                UnicodeDisplay.printMazeWithUnicode(maze, true);
            } else {
                fileManager.printMaze(maze);
            }

        } catch (Exception e) {
            System.err.println("Error generating maze: " + e.getMessage());
        }
    }
}
