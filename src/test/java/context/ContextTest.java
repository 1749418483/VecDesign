package context;

import implemention.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ContextTest {

    @Test
    void getCommandByName() {
        assertEquals(Context.getCommandByName("ELLIPSE"), Ellipse.getInstance());
        assertEquals(Context.getCommandByName("FILL"), Fill.getInstance());
        assertEquals(Context.getCommandByName("LINE"), Line.getInstance());
        assertEquals(Context.getCommandByName("PEN"), Pen.getInstance());
        assertEquals(Context.getCommandByName("PLOT"), Plot.getInstance());
        assertEquals(Context.getCommandByName("POLYGON"), Polygon.getInstance());
        assertEquals(Context.getCommandByName("RECTANGLE"), Rectangle.getInstance());
    }

    @Test
    void getBehaviourByName() {
        assertEquals(Context.getBehaviourByName("CURSOR"), CursorBehaviour.getInstance());
        assertEquals(Context.getBehaviourByName("ELLIPSE"), Ellipse.getInstance());
        assertEquals(Context.getBehaviourByName("LINE"), Line.getInstance());
        assertEquals(Context.getBehaviourByName("PLOT"), Plot.getInstance());
        assertEquals(Context.getBehaviourByName("POLYGON"), Polygon.getInstance());
        assertEquals(Context.getBehaviourByName("RECTANGLE"), Rectangle.getInstance());
    }
}