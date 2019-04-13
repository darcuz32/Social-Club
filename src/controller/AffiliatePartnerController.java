package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Partner;

import java.util.ArrayList;

public class AffiliatePartnerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnAffiliatePartner;

    private PartnersController partnersController;

    public ArrayList<Partner> partners;

    public void initialize(){

    }

    public void handleDoneAffiliatePartner(){
        String name = txtName.getText();
        String id = txtId.getText();




        try{
            Integer.parseInt(id);
        }catch (NumberFormatException e){
            txtId.setStyle("-fx-text-box-border: red");
            return;
        }


        Partner partner = new Partner(id,name);
        partners.add(partner);
        System.out.println(partner);

        partnersController.getPartnersTable().getItems().add(partner);

        ((Stage) txtName.getScene().getWindow()).close();
    }

    public void setPartnersController(PartnersController partnersController) {
        this.partnersController = partnersController;
    }
}
