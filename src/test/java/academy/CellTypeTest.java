package academy;

import static org.junit.jupiter.api.Assertions.*;

import academy.maze.dto.CellType;
import org.junit.jupiter.api.Test;

class CellTypeTest {

    @Test
    void testCellTypeSymbols() {
        assertEquals("#", CellType.WALL.getSymbol());
        assertEquals(" ", CellType.PATH.getSymbol());
    }

    @Test
    void testCellTypeToString() {
        assertEquals("#", CellType.WALL.toString());
        assertEquals(" ", CellType.PATH.toString());
    }
}
