package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.dto.Point;
import org.junit.jupiter.api.Test;

class PointTest {

    @Test
    void testPointCreation() {
        Point point = new Point(3, 5);
        assertEquals(3, point.x());
        assertEquals(5, point.y());
    }

    @Test
    void testPointEquals() {
        Point point1 = new Point(2, 3);
        Point point2 = new Point(2, 3);
        Point point3 = new Point(3, 2);

        assertEquals(point1, point2);
        assertNotEquals(point1, point3);
    }

    @Test
    void testPointHashCode() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);

        assertEquals(point1.hashCode(), point2.hashCode());
    }
}
