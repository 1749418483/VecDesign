package implemention;

import command.Command;
import context.Context;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Rectangle extends CommandBehaviour {
    private Rectangle() {

    }
    private static Rectangle instance = new Rectangle();
    public static Rectangle getInstance() {
        return instance;
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
        if (Context.isFill())
            context.fillRect(x1, y1, x2 - x1, y2 - y1);
        context.strokeRect(x1, y1, x2 - x1, y2 - y1);
    }

    private void addToCommand(double x1, double y1, double x2, double y2) {
        Context.getCurrentCommands().add(String.join(" ", getKeyword(), "" + x1, "" + y1, "" + x2, "" + y2));
    }

    @Override
    public String getKeyword() {
        return "RECTANGLE";
    }

    private javafx.scene.shape.Rectangle shape = new javafx.scene.shape.Rectangle();

    private boolean isFirst = true;

    @Override
    public void onAttach(StackPane canvasParent) {
        canvasParent.getChildren().add(shape);
        shape.setManaged(false);
        shape.setVisible(false);
        shape.setMouseTransparent(true);
    }

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        if (isFirst) {
            shape.setX(event.getX());
            shape.setY(event.getY());
        } else {
            actualDraw(context, shape.getX(), shape.getY(), event.getX(), event.getY());
            var scale = getScale(context);
            addToCommand(shape.getX() / scale.getX(), shape.getY() / scale.getY(), event.getX() / scale.getX(), event.getY() / scale.getY());
        }
        shape.setVisible(isFirst);
        isFirst = !isFirst;
    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {
        shape.setFill(context.getFill());
        shape.setStroke(context.getStroke());
        if (isFirst) return;
        shape.setWidth(event.getX() - shape.getX());
        shape.setHeight(event.getY() - shape.getY());
    }

    @Override
    public void onDetach(StackPane canvasParent) {
        canvasParent.getChildren().remove(shape);
    }
}
