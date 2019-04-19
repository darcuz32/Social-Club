package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Invoice;
import model.Partner;

import javax.swing.*;

public class RegisterConsumptionController {
    @FXML
    private JFXComboBox comboBoxName;

    @FXML
    private JFXTextField txtConcept;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXButton btnRegisterConsumption;

    public InvoicesController invoicesController;

    public Partner partner;

    public void initialize(){
    }

    public void handleDoneRegisterConsumption(){
        String name = comboBoxName.getSelectionModel().getSelectedItem().toString();
        String concept = txtConcept.getText();
        String amount = txtAmount.getText();

        try{
            Double.parseDouble(amount);
            if (concept.isEmpty() && amount.isEmpty()){
                txtConcept.setStyle("-jfx-focus-color: red");
                txtConcept.setStyle("-jfx-unfocus-color: red");
                txtAmount.setStyle("-jfx-focus-color: red");
                txtAmount.setStyle("-jfx-unfocus-color: red");
                return;
            }else if (concept.isEmpty()){
                txtConcept.setStyle("-jfx-focus-color: red");
                txtConcept.setStyle("-jfx-unfocus-color: red");
                return;
            }else if(amount.isEmpty() || Double.parseDouble(amount) < 0){
                txtAmount.setStyle("-jfx-focus-color: red");
                txtAmount.setStyle("-jfx-unfocus-color: red");
                return;
            }
        }catch (NumberFormatException e){
            txtAmount.setStyle("-jfx-focus-color: red");
            txtAmount.setStyle("-jfx-unfocus-color: red");
            return;
        }

        partner.addInvoice(name, concept,  Double.parseDouble(amount));

        invoicesController.fillTableInvoices(name);
        invoicesController.setComboBoxValue(name);
        ((Stage) txtAmount.getScene().getWindow()).close();
        invoicesController.handleRemoveFocus();
    }

    public void handleReleasedAmount(){
        txtAmount.setStyle("");
    }

    public void handleReleasedConcept(){
        txtConcept.setStyle("");
    }

    public void handleRemoveFocus(){
        Parent parent = txtConcept.getParent();
        parent.requestFocus();
    }

    public void setInvoicesController(InvoicesController invoicesController) {
        this.invoicesController = invoicesController;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public void fillComboBox(Partner partner){

        comboBoxName.getItems().add(partner.getName());
        for (Object thisAuthorized: partner.getAuthorized()) {
            comboBoxName.getItems().add(String.valueOf(thisAuthorized));
        }
        comboBoxName.getSelectionModel().select(0);
    }
}
