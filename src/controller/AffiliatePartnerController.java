package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import javax.swing.*;
import java.util.ArrayList;

public class AffiliatePartnerController {

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXButton btnAffiliatePartner;

    private PartnersController partnersController;

    public Club club;

    public void initialize(){

    }

    public void handleDoneAffiliatePartner(){
        String name = txtName.getText();
        String id = txtId.getText();

        if (name.isEmpty() && id.isEmpty()){
            txtName.setStyle("-jfx-focus-color: red");
            txtId.setStyle("-jfx-focus-color: red");
            txtName.setStyle("-jfx-unfocus-color: red");
            txtId.setStyle("-jfx-unfocus-color: red");
            return;
        }else if (id.isEmpty()){
            txtId.setStyle("-jfx-focus-color: red");
            txtId.setStyle("-jfx-unfocus-color: red");
            return;
        }else if(name.isEmpty()){
            txtName.setStyle("-jfx-focus-color: red");
            txtName.setStyle("-jfx-unfocus-color: red");
            return;
        }

        try{
            Integer.parseInt(id);
        }catch (NumberFormatException e){
            txtId.setStyle("-jfx-focus-color: red");
            txtId.setStyle("-jfx-unfocus-color: red");
            return;
        }

        try {
            club.affiliatePartner(id,name);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            return;
        }

        ObservableList<Partner> partners = FXCollections.observableArrayList(club.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersController.getPartnersTable().setRoot(root);
        partnersController.getPartnersTable().setShowRoot(false);


        ((Stage) txtName.getScene().getWindow()).close();
    }

    public void handleReleasedName(){
        txtName.setStyle("");
    }

    public void handleReleasedId(){
        txtId.setStyle("");
    }

    public void setPartnersController(PartnersController partnersController) {
        this.partnersController = partnersController;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
