package implemention;

import context.Context;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Ellipse extends CommandBehaviour {
    private static Ellipse instance = new Ellipse();

    public static Ellipse getInstance() {
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
        var diamX = x2 - x1 ;
        var diamY = y2 - y1;
        context.strokeOval(x1, y1, diamX, diamY);

        if (Context.isFill()) {
            context.fillOval(x1, y1, diamX, diamY);
        }
    }

    @Override
    public String getKeyword() {
        return "ELLIPSE";
    }


    private double x1;
    private double y1;
    private boolean isFirst = true;
    private javafx.scene.shape.Ellipse shape = new javafx.scene.shape.Ellipse();

    private void addToCommands(double x1, double y1, double x2, double y2) {
        Context.getCurrentCommands().add(String.join(" ", getKeyword(), "" + x1, "" + y1, "" + x2, "" + y2));
    }


    @Override
    public void onAttach(StackPane canvasParent) {
        canvasParent.getChildren().add(shape);
        shape.setManaged(false);
        shape.setVisible(false);
        shape.setOpacity(0.6);
    }

    @Override
    public void onDetach(StackPane canvasParent) {
        canvasParent.getChildren().remove(shape);
    }

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        shape.setFill(context.getFill());
        shape.setStroke(context.getStroke());
        shape.setStrokeWidth(context.getLineWidth());
        if (isFirst) {
            x1 = event.getX();
            y1 = event.getY();
        } else {
            var x2 = event.getX();
            var y2 = event.getY();
            actualDraw(context, x1, y1, x2, y2);
            var scale = getScale(context);
            addToCommands(x1 / scale.getX(), y1 / scale.getY(), x2 / scale.getX(), y2 / scale.getY());
        }
        shape.setVisible(isFirst);
        isFirst = !isFirst;
    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {
        var radiusX = (event.getX() - x1) / 2;
        var radiusY = (event.getY() - y1) / 2;
        shape.setCenterX(x1 + radiusX);
        shape.setCenterY(y1 + radiusY);
        shape.setRadiusX(radiusX);
        shape.setRadiusY(radiusY);
    }




}
