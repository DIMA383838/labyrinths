package academy.maze.Command;

import academy.maze.FileManager;
import academy.maze.Solover.MazeSolverFactory;
import academy.maze.Solover.Solver;
import academy.maze.Solover.SolverType;
import academy.maze.UnicodeDisplay;
import academy.maze.dto.Maze;
import academy.maze.dto.Path;
import academy.maze.dto.Point;
import picocli.CommandLine;
// java -jar target\project-1.0.jar solve --algorithm=astar --file=mazes\maze888.txt --start=0,1 --end=6,7
// --output=solution_maze888.txt

@CommandLine.Command(name = "solve", description = "Solve a maze")
public class SolveCommand implements Runnable {
    @CommandLine.Option(names = "--algorithm", required = true, description = "Algorithm: astar, dijkstra")
    private String algorithm;

    @CommandLine.Option(names = "--file", required = true, description = "Input maze file")
    private String file;

    @CommandLine.Option(names = "--start", required = true, description = "Start point (x,y)")
    private String start;

    @CommandLine.Option(names = "--end", required = true, description = "End point (x,y)")
    private String end;

    @CommandLine.Option(names = "--output", required = true, description = "Output file")
    private String output;

    @CommandLine.Option(names = "--unicode", description = "Use Unicode symbols for display")
    private boolean useUnicode = false;

    private FileManager fileManager;

    public SolveCommand() {
        this.fileManager = new FileManager();
    }

    @Override
    public void run() {
        try {
            System.out.println("Solving maze with algorithm: " + algorithm);

            SolverType solverType;
            try {
                solverType = SolverType.fromString(algorithm);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                return;
            }

            Maze maze = fileManager.loadMaze(file);
            Point startPoint = parsePoint(start);
            Point endPoint = parsePoint(end);

            Solver solver = MazeSolverFactory.createSolver(solverType);
            Path path = solver.solve(maze, startPoint, endPoint);
            if (path.points().length == 0) {
                System.out.println("Path not found!");
            } else {
                String savedFilename = fileManager.saveSolutionToFile(maze, path, startPoint, endPoint, output);
                System.out.println("Solution saved to: " + savedFilename);

                if (useUnicode) {
                    UnicodeDisplay.printMazeWithUnicode(maze, path, startPoint, endPoint, true);
                } else {
                    fileManager.printSolutionFile(savedFilename);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Point parsePoint(String pointStr) {
        String[] parts = pointStr.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid point format: " + pointStr + ". Expected format: x,y");
        }
        int x = Integer.parseInt(parts[0].trim());
        int y = Integer.parseInt(parts[1].trim());
        return new Point(x, y);
    }
}
