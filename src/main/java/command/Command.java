package command;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public interface Command {
    void draw(GraphicsContext context, String[] args);
    String getKeyword();
    default Point2D getScale(GraphicsContext context) {
        var canvas = context.getCanvas();
        return new Point2D(canvas.getWidth(), canvas.getHeight());
    }

}

