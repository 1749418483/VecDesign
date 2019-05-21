package implemention;

import command.Command;
import context.Context;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Fill implements Command {

    private final static Fill instance;

    static {
        instance = new Fill();
    }

    public static Fill getInstance() {
        return instance;
    }

    private Fill() { }

    @Override
    public void draw(GraphicsContext context, String[] args) {
        boolean flag;
        var color = (flag = "OFF".equals(args[0])) ? Color.TRANSPARENT : Color.web(args[0]);
        context.setFill(color);
        Context.setFill(!flag);
    }

    @Override
    public String getKeyword() {
        return "FILL";
    }
}
