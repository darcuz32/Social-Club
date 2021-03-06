import controller.PartnersController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Club;
import model.Theme;

import java.io.*;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("resources/images/cs.png"));
        URL url = getClass().getClassLoader().getResource("resources/views/SocialClub.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        PartnersController partnersController = fxmlLoader.getController();
        Club club = null;
        String theme = null;

        String filename = "club.tmp";
        String filenametheme = "theme.tmp";
        // Deserialization
        try{
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            club = (Club) in.readObject();

            in.close();
            file.close();
        }catch(IOException ex){
            System.out.println("IOException is caught");
            club = new Club();
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException is caught");
        }

        // Deserialization
        try{
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filenametheme);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            // Method for deserialization of object
            Theme themeAsObject = (Theme) in.readObject();
            theme = themeAsObject.getTheme();

            in.close();
            file.close();
        }catch(IOException ex){
            System.out.println("IOException is caught");
            theme = "DarkTheme.css";
        }


        partnersController.theme = theme;
        partnersController.setClub(club);
        partnersController.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Club social");
        primaryStage.setScene(new Scene(parent));
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add("resources/styles/"+theme);

        parent.requestFocus();
        primaryStage.setMaximized(true);
        primaryStage.show();
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
    }
}
