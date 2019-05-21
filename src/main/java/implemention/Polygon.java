package implemention;

import command.Command;
import context.Context;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Polygon extends CommandBehaviour {
    private static Polygon instance = new Polygon();

    public static Polygon getInstance() {
        return instance;
    }

    private Polygon() {
    }

    @Override
    public void draw(GraphicsContext context, String[] args) {
        var n = args.length / 2;
        var x = new double[n];
        var y = new double[n];
        var scale = getScale(context);
        for (int i = 0; i < n; i++) {
            x[i] = Double.parseDouble(args[i * 2]) * scale.getX();
            y[i] = Double.parseDouble(args[i * 2 + 1]) * scale.getY();
        }
        actualDraw(context, x, y);

    }

    private void actualDraw(GraphicsContext context, double[] x, double[] y) {
        if (x.length != y.length) throw new IllegalStateException("Length of x doesn't equal to y's");
        if (Context.isFill())
            context.fillPolygon(x, y, x.length);
        context.strokePolygon(x, y, x.length);
    }

    private void addToCommand(double[] x, double[] y, Point2D scale) {
        if (x.length != y.length) throw new IllegalStateException("Length of x doesn't equal to y's");
        String[] arr = new String[x.length + y.length];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) arr[i] = "" + x[i / 2] / scale.getX();
            else arr[i] = "" + y[i / 2] / scale.getY();
        }
        Context.getCurrentCommands().add(getKeyword() + " " + String.join(" ", arr));
    }

    @Override
    public String getKeyword() {
        return "POLYGON";
    }

    private javafx.scene.shape.Polygon shape = new javafx.scene.shape.Polygon();
    private int currentIndex = 0;

    @Override
    public void onAttach(StackPane canvasParent) {
        canvasParent.getChildren().add(shape);
        shape.setVisible(false);
        shape.setManaged(false);
        shape.setMouseTransparent(true);
    }

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {
        if (event.getButton() == MouseButton.SECONDARY) {
            shape.setVisible(false);
            var points = shape.getPoints();
            var size = points.size() - 2;
            var x = new double[size / 2];
            var y = new double[size / 2];
            for (int i = 0; i < size; i++) {
                if (i % 2 == 0) x[i / 2] = points.get(i);
                else y[i / 2] = points.get(i);
            }
            actualDraw(context, x, y);
            addToCommand(x, y, getScale(context));
            shape.getPoints().clear();
            currentIndex = 0;
            shape.setVisible(false);
        } else if (event.getButton() != MouseButton.PRIMARY) return;

        shape.setVisible(true);
        currentIndex += 2;
        shape.getPoints().addAll(event.getX(), event.getY());
    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {
        shape.setStroke(context.getStroke());
        shape.setFill(context.getFill());
        if (currentIndex <= 2) return;
        shape.getPoints().set(currentIndex - 2, event.getX());
        shape.getPoints().set(currentIndex - 1, event.getY());
    }

    @Override
    public void onDetach(StackPane canvasParent) {
        canvasParent.getChildren().remove(shape);
    }
}
