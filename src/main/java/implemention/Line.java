package implemention;

import context.Context;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.awt.*;

public class Line extends CommandBehaviour {

    private static Line instance = new Line();
    public static Line getInstance() {
        return instance;
    }
    private Line() {

    }

    @Override
    public void draw(GraphicsContext context, String[] args) {
        var scale = getScale(context);
        var x1 = Double.parseDouble(args[0]) * scale.getX();
        var y1 = Double.parseDouble(args[1]) * scale.getY();
        var x2 = Double.parseDouble(args[2]) * scale.getX();
        var y2 = Double.parseDouble(args[3]) * scale.getY();
        actualDraw(context, x1, y1, x2, y2);
    }

    private void actualDraw(GraphicsContext context, double x1, double y1, double x2, double y2) {
        context.strokeLine(x1, y1, x2, y2);
    }

    private void addToCommands(double x1, double y1, double x2, double y2) {
        Context.getCurrentCommands().add(String.join(" ", getKeyword(), "" + x1, "" + y1, "" + x2, "" + y2));
    }


    @Override
    public String getKeyword() {
        return "LINE";
    }

    private javafx.scene.shape.Line shape = new javafx.scene.shape.Line();


    @Override
    public void onAttach(StackPane canvasParent) {
        canvasParent.getChildren().add(shape);
        shape.setVisible(false);
        shape.setManaged(false);
        shape.setMouseTransparent(true);
    }


    private boolean isFirst = true;

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        shape.setStroke(context.getStroke());
        shape.setStrokeWidth(context.getLineWidth());

        if (isFirst) {
            shape.setStartX(event.getX());
            shape.setStartY(event.getY());
        } else {
            shape.setEndX(event.getX());
            shape.setEndY(event.getY());
            actualDraw(context, shape.getStartX(), shape.getStartY(), shape.getEndX(), shape.getEndY());
            var scale = getScale(context);
            addToCommands(shape.getStartX() / scale.getX(), shape.getStartY() / scale.getY(), shape.getEndX() / scale.getX(), shape.getEndY() / scale.getY());
        }
        shape.setVisible(isFirst);
        isFirst = !isFirst;
    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {
        if (!isFirst) {
            shape.setEndX(event.getX());
            shape.setEndY(event.getY());
        }

    }

    @Override
    public void onDetach(StackPane canvasParent) {
        canvasParent.getChildren().remove(shape);
    }
}
