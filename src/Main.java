import controller.PartnersController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Club;

import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getClassLoader().getResource("resources/views/SocialClub.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        PartnersController partnersController = fxmlLoader.getController();
        Club club = new Club();
        partnersController.setClub(club);
        primaryStage.setTitle("Club social");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
