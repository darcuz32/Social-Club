package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AddAuthorizedController {
    @FXML
    private JFXTextField txtName;

    @FXML
    private Label lblPartner;

    @FXML
    private StackPane stackPane;

    public AuthorizedController authorizedController;

    public Partner partner;

    public Club club;

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
            String filename = "club.tmp";

            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(club);

            out.close();
            file.close();
        }catch(IOException ex) {
            System.out.println("IOException is caught\n"+ex.getMessage());
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

    public void setClub(Club club) {
        this.club = club;
    }
}
