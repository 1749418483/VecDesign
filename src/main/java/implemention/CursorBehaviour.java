package implemention;

import behaviour.Behaviour;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class CursorBehaviour implements Behaviour {
    private static final CursorBehaviour INSTANCE = new CursorBehaviour();
    public static CursorBehaviour getInstance() {
        return INSTANCE;
    }


    @Override
    public void onAttach(StackPane canvasParent) {

    }

    @Override
    public void onMouseClicked(MouseEvent event, GraphicsContext context) {

    }

    @Override
    public void onMouseMoved(MouseEvent event, GraphicsContext context) {

    }

    @Override
    public void onDetach(StackPane canvasParent) {

    }
}
