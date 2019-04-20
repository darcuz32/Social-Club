package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Club;
import model.Theme;

import java.io.*;

public class ConfigController {

    @FXML
    private JFXButton btnSaveConfig;

    @FXML
    private JFXRadioButton radioBtnDarkTheme;

    @FXML
    private JFXRadioButton radioBtnDefaultTheme;

    public String theme;

    public Club club;

    public Stage primaryStage;

    public Stage dialog;

    private  ToggleGroup toggleGroupTheme;

    public PartnersController partnersController;

    public AuthorizedController authorizedController;

    public InvoicesController invoicesController;

    public void initialize(){
        toggleGroupTheme = new ToggleGroup();
        radioBtnDarkTheme.setToggleGroup(toggleGroupTheme);
        radioBtnDefaultTheme.setToggleGroup(toggleGroupTheme);

        String filename = "theme.tmp";
        // Deserialization
        try{
            // Reading the object from a file
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            Theme themeAsObject = (Theme) in.readObject();
            theme = themeAsObject.getTheme();

            in.close();
            file.close();
        }catch(IOException ex){
            System.out.println("IOException is caught");
            theme = "DarkTheme.css";
        }catch(ClassNotFoundException ex){
            System.out.println("ClassNotFoundException is caught");
        }

        switch (theme){
            case "DarkTheme.css":
                radioBtnDarkTheme.setSelected(true);
            break;

            case "DefaultTheme.css":
                radioBtnDefaultTheme.setSelected(true);
            break;
        }
    }

    public void handleSaveConfig(){
        RadioButton selectedRadioBtn = (RadioButton) toggleGroupTheme.getSelectedToggle();
        theme = selectedRadioBtn.getText();
        switch (theme){
            case "Oscuro":
                theme = "DarkTheme.css";
                break;

            case "Default":
                theme = "DefaultTheme.css";
                break;
        }

        String filename = "theme.tmp";
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(new Theme(theme));
            System.out.println(new Theme(theme));

            out.close();
            file.close();
        }catch(IOException ex) {
            System.out.println("IOException is caught\n"+ex.getMessage());
        }catch (Exception e){
            System.out.println(e);
            return;
        }

        ((Stage) btnSaveConfig.getScene().getWindow()).close();
    }

    public void handleChangeTheme(){
        RadioButton selectedRadioBtn = (RadioButton) toggleGroupTheme.getSelectedToggle();
        String themeSelected = selectedRadioBtn.getText();
        switch (themeSelected){
            case "Oscuro":
                themeSelected = "DarkTheme.css";
                break;

            case "Default":
                themeSelected = "DefaultTheme.css";
                break;
        }
        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add("resources/styles/"+themeSelected);
        dialog.getScene().getStylesheets().clear();
        dialog.getScene().getStylesheets().add("resources/styles/"+themeSelected);
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setDialog(Stage dialog) {
        this.dialog = dialog;

        dialog.setOnHiding(event -> Platform.runLater(() -> {

            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add("resources/styles/"+theme);
            if (partnersController != null){
                partnersController.theme = theme;
            }
            if (authorizedController != null){
                authorizedController.theme = theme;
            }
            if (invoicesController != null){
                invoicesController.theme = theme;
            }
        }));
    }

    public void setPartnersController(PartnersController partnersController) {
        this.partnersController = partnersController;
    }

    public void setAuthorizedController(AuthorizedController authorizedController) {
        this.authorizedController = authorizedController;
    }

    public void setInvoicesController(InvoicesController invoicesController) {
        this.invoicesController = invoicesController;
    }
}
