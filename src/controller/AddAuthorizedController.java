package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import javax.swing.*;

public class AddAuthorizedController {
    @FXML
    private JFXTextField txtName;

    @FXML
    private Label lblPartner;

    @FXML
    private JFXButton btnAddAuthorized;

    @FXML
    private StackPane stackPane;

    public AuthorizedController authorizedController;

    public Partner partner;

    public void initialize(){

    }

    public void handleDoneAddAuthorized(){
        String name = txtName.getText();

        if (name.isEmpty()){
            txtName.setStyle("-jfx-focus-color: red");
            txtName.setStyle("-jfx-unfocus-color: red");
            return;
        }

        try {
            partner.addAuthorized(name);
        }catch (Exception e){
            callExceptionDialog(e);
            return;
        }

        authorizedController.fillTableAuthorized(partner);
        ((Stage) txtName.getScene().getWindow()).close();
        authorizedController.handleRemoveFocus();
    }

    public void handleReleasedName(){
        txtName.setStyle("");
    }

    public void callExceptionDialog(Exception e){
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("Error");
        headerText.getStyleClass().add("dialog-text");
        content.setHeading(headerText);
        Text msgText = new Text(e.getMessage());
        msgText.getStyleClass().add("dialog-text");
        content.setBody(msgText);
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Ok");
        content.getStyleClass().add("background");
        content.getStyleClass().add("dialog");
        button.getStyleClass().add("btn");
        button.setOnAction(event -> {
            Parent parent = txtName.getParent();
            stackPane.setVisible(false);
            parent.requestFocus();
            dialog.close();
        });
        content.setActions(button);
        stackPane.setVisible(true);
        Parent parent = txtName.getParent();
        parent.requestFocus();
        dialog.show();
    }

    public void handleRemoveFocus(){
        Parent parent = txtName.getParent();
        stackPane.setVisible(false);
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
