package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import javax.swing.*;
import java.util.ArrayList;

public class AffiliatePartnerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnAffiliatePartner;

    private PartnersController partnersController;

    public Club club;

    public void initialize(){

    }

    public void handleDoneAffiliatePartner(){
        String name = txtName.getText();
        String id = txtId.getText();

        if (name.isEmpty() && id.isEmpty()){
            txtName.setStyle("-fx-text-box-border: red");
            txtId.setStyle("-fx-text-box-border: red");
            return;
        }else if (id.isEmpty()){
            txtId.setStyle("-fx-text-box-border: red");
            return;
        }else if(name.isEmpty()){
            txtName.setStyle("-fx-text-box-border: red");
            return;
        }

        try{
            Integer.parseInt(id);
        }catch (NumberFormatException e){
            txtId.setStyle("-fx-text-box-border: red");
            return;
        }

        try {
            club.affiliatePartner(id,name);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }

        partnersController.getPartnersTable().getItems().clear();
        for (Partner thisPartner: club.getPartners()) {
            partnersController.getPartnersTable().getItems().add(thisPartner);
            System.out.println(thisPartner);
        }
        ((Stage) txtName.getScene().getWindow()).close();
    }

    public void handleReleasedName(){
        txtName.setStyle("");
    }

    public void handleReleasedId(){
        txtId.setStyle("");
    }

    public void handleRemoveFocus(){
        Parent parent = txtId.getParent();
        parent.requestFocus();
    }


    public void setPartnersController(PartnersController partnersController) {
        this.partnersController = partnersController;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
