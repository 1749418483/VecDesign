import context.Context;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.DepthTest;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import parser.Parser;

public class AppController {

    @FXML
    private Button cursor;

    @FXML
    private Button polygon;

    @FXML
    private Button line;

    @FXML
    private Button rectangle;

    @FXML
    private Button ellipse;

    @FXML
    private ColorPicker strokeColorChooser;

    @FXML
    private ColorPicker fillColorChooser;

    @FXML
    private Button point;

    @FXML
    private GridPane grid;

    @FXML
    private MenuItem menuFileOpen;

    @FXML
    private MenuItem menuFileSave;

    @FXML
    private Canvas canvas;

    @FXML
    private StackPane canvasParent;


    @FXML
    private ListView<String> commandListView;

    @FXML
    void initialize() {


        commandListView.itemsProperty().bind(Context.getCurrentCommands());


        var context = canvas.getGraphicsContext2D();

        Parser.setContext(context);
        canvas.setDepthTest(DepthTest.DISABLE);
        canvas.setOnMouseClicked(action ->Context.getCurrentBehaviour().onMouseClicked(action, context));
        canvas.setOnMouseMoved(action ->Context.getCurrentBehaviour().onMouseMoved(action, context));

        setUpIcons();
        setUpTooltips();
        setUpShortCuts();





        strokeColorChooser.valueProperty().addListener((observable, oldValue, newValue) -> {

            Parser.parseAndAdd("PEN",Color.TRANSPARENT.equals(newValue) ? "OFF": newValue.toString().replace("0x", "#"));
        });
        fillColorChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            Parser.parseAndAdd("FILL",Color.TRANSPARENT.equals(newValue) ? "OFF": newValue.toString().replace("0x", "#"));
        });

        strokeColorChooser.setValue(Color.BLACK);
        fillColorChooser.setValue(Color.TRANSPARENT);




    }

    private void setUpIcons() {
        Shape c;
        c = new Circle(2.5);
        point.setGraphic(c);

        c = new Line(0.0, 0.0, 30.0, 30.0);
        line.setGraphic(c);

        c = new Rectangle(30, 20, Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        rectangle.setGraphic(c);

        c = new Circle(15.0, Color.TRANSPARENT);
        c.setStroke(Color.BLACK);
        ellipse.setGraphic(c);

        c = new Polygon(7.5, 0.0, 22.5, 0.0, 30.0, 2.0 * 15, 0.0, 15.0, 7.5, 0.0);
        c.setStroke(Color.BLACK);
        c.setFill(Color.TRANSPARENT);
        polygon.setGraphic(c);
    }

    private void setUpShortCuts() {
        menuFileOpen.setAccelerator(KeyCombination.keyCombination("ctrl + f12"));
        menuFileSave.setAccelerator(KeyCombination.keyCombination("ctrl + s"));
    }

    private void setUpTooltips() {
        grid.getChildrenUnmodifiable()
                .stream()
                .map(it -> (Button)it)
                .forEach(it -> {
                    var id = it.getId();
                    var tooltip = new Tooltip(id);
                    tooltip.setShowDelay(Duration.seconds(0.5));
                    it.setTooltip(tooltip);
                    it.setOnAction((action) -> {
                        var behaviour = Context.getBehaviourByName(id.toUpperCase());
                        System.out.println(behaviour);
                        Context.setCurrentBehaviour(behaviour, canvasParent);
                    });
                });
    }

    @FXML
    void onFileOpen(ActionEvent event) {

    }

    @FXML
    void onFileSave(ActionEvent event) {

    }
    @FXML
    void onFileNew(ActionEvent event) {

    }


}
