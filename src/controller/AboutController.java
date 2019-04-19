package controller;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AboutController {

    @FXML
    private JFXButton btnCloseAbout;

    public void initialize(){

    }

    public void handleDoneCloseAbout(){
        ((Stage) btnCloseAbout.getScene().getWindow()).close();
    }
}
