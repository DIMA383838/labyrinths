// PathTest.java
package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.dto.Path;
import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

class PathTest {

    @Test
    void testPathCreation() {
        Point[] points = {new Point(0, 0), new Point(1, 0), new Point(1, 1)};

        Path path = new Path(points);
        assertEquals(3, path.points().length);
        assertEquals(new Point(0, 0), path.points()[0]);
        assertEquals(new Point(1, 1), path.points()[2]);
    }

    @Test
    void testEmptyPath() {
        Path path = new Path(new Point[0]);
        assertEquals(0, path.points().length);
    }
}
