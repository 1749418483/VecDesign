package implemention;

import command.Command;
import context.Context;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Plot extends CommandBehaviour {
    private Plot() {
    }
    private static Plot instance = new Plot();

    public static Plot getInstance() {
        return instance;
    }

    @Override
    public void draw(GraphicsContext context, String[] args) {
        var scale = getScale(context);
        var x = Double.parseDouble(args[0]) * scale.getX();
        var y = Double.parseDouble(args[1]) * scale.getY();
        actualDraw(context, x, y);
    }

    private void actualDraw(GraphicsContext context, double x, double y) {
        context.fillOval(x, y, context.getLineWidth() / 2, context.getLineWidth() / 2);
    }

    private void addToCommand(double x, double y) {
        Context.getCurrentCommands().add(String.join(" ", getKeyword(), "" + x, "" + y));
    }

    @Override
    public String getKeyword() {
        return "PLOT";
    }

    @Override
    public void onAttach(StackPane canvasParent) {

    }

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        actualDraw(context, event.getX(), event.getY());
        addToCommand(event.getX(), event.getY());
    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {

    }

    @Override
    public void onDetach(StackPane canvasParent) {

    }
}
