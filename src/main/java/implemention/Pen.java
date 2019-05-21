package implemention;

import command.Command;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pen implements Command {


    private final static Pen instance;

    static {
        instance = new Pen();
    }

    private Pen() {

    }

    public static Pen getInstance() {
        return instance;
    }

    @Override
    public void draw(GraphicsContext context, String[] args) {
        var color = "OFF".equals(args[0]) ? Color.TRANSPARENT : Color.web(args[0]);
        context.setStroke(color);
    }

    @Override
    public String getKeyword() {
        return "PEN";
    }
}
