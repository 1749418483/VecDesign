import context.Context;
import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("App.fxml"));
        var scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.titleProperty().bind(
                new SimpleStringProperty("VecDesign [").concat(
                        new When(Context.currentFileNameProperty().isEmpty()).then("Unnamed")
                                .otherwise(
                                    new When(Context.getCurrentCommands().emptyProperty()).then("")
                                        .otherwise(" (Modified)")
                                ).concat("]")
                )

        );
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
