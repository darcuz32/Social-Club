package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import javax.swing.*;

public class AddAuthorizedController {
    @FXML
    private TextField txtName;

    @FXML
    private Label lblPartner;

    @FXML
    private Button btnAddAuthorized;

    public AuthorizedController authorizedController;

    public Partner partner;

    public void initialize(){

    }

    public void handleDoneAddAuthorized(){
        String name = txtName.getText();

        if (name.isEmpty()){
            txtName.setStyle("-fx-text-box-border: red");
            return;
        }

        try {
            partner.addAuthorized(name);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }

        authorizedController.getAuthorizedTable().getItems().clear();
        for (Object thisAuthorized:partner.getAuthorized()) {
            authorizedController.getAuthorizedTable().getItems().add(String.valueOf(thisAuthorized));
        }
        ((Stage) txtName.getScene().getWindow()).close();
    }

    public void handleReleasedName(){
        txtName.setStyle("");
    }

    public void handleRemoveFocus(){
        Parent parent = txtName.getParent();
        parent.requestFocus();
    }

    public void setAuthorizedController(AuthorizedController authorizedController) {
        this.authorizedController = authorizedController;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
        lblPartner.setText(partner.getName());
    }
}
