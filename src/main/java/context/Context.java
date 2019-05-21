package context;

import behaviour.Behaviour;
import implemention.CursorBehaviour;
import command.Command;
import implemention.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.StackPane;

import java.util.Map;

public class Context {
    private Context() {}
    private static boolean isFill = false;

    public static boolean isFill() {
        return isFill;
    }

    public static void setFill(boolean isFill) {
        Context.isFill = isFill;
    }

    private static final Map<String, Command> COMMMANDS;

    private static final Map<String, CommandBehaviour> BEHAVIOURS;

    static {
        BEHAVIOURS = Map.of(
                Ellipse.getInstance().getKeyword(), Ellipse.getInstance(),
                Line.getInstance().getKeyword(), Line.getInstance(),
                Plot.getInstance().getKeyword(), Plot.getInstance(),
                Polygon.getInstance().getKeyword(), Polygon.getInstance(),
                Rectangle.getInstance().getKeyword(), Rectangle.getInstance()
        );

        COMMMANDS = Map.of(
                Pen.getInstance().getKeyword(), Pen.getInstance(),
                Fill.getInstance().getKeyword(), Fill.getInstance()
        );
    }

    private static ListProperty<String> currentCommands = new SimpleListProperty<>(FXCollections.observableArrayList());

    public static ListProperty<String> getCurrentCommands() {
        return currentCommands;
    }

    public static Command getCommandByName(String name) {
        return COMMMANDS.getOrDefault(name, BEHAVIOURS.get(name));
    }

    public static Behaviour getBehaviourByName(String name){
        var behaviour = BEHAVIOURS.get(name);
        return behaviour == null ? CursorBehaviour.getInstance() : behaviour;
    }

    private static StringProperty currentFileNameProperty = new SimpleStringProperty("");
    public static StringProperty currentFileNameProperty() {
        return currentFileNameProperty;
    }

    private static Behaviour currentBehaviour = CursorBehaviour.getInstance();

    public static Behaviour getCurrentBehaviour() {
        return currentBehaviour;
    }

    public static void setCurrentBehaviour(Behaviour currentBehaviour, StackPane canvasParent) {
        Context.currentBehaviour.onDetach(canvasParent);
        Context.currentBehaviour = currentBehaviour;
        currentBehaviour.onAttach(canvasParent);
    }

}


