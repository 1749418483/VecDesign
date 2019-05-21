package behaviour;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public interface Behaviour {
    void onAttach(StackPane canvasParent);
    void onMouseClicked(MouseEvent event, GraphicsContext context);
    void onMouseMoved(MouseEvent event, GraphicsContext context);
    void onDetach(StackPane canvasParent);
}


