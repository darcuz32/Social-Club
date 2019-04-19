package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Authorized;
import model.Club;
import model.Invoice;
import model.Partner;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AuthorizedController {
    @FXML
    private JFXTreeTableView<Partner> partnersAuthorizedTable;

    @FXML
    private JFXTreeTableView<Authorized> authorizedTable;

    @FXML
    private MenuItem btnPartnersMenu;

    @FXML
    private MenuItem btnAuthorizedMenu;

    @FXML
    private MenuItem btnInvoicesMenu;

    @FXML
    private JFXButton btnAddAuthorized;

    @FXML
    private JFXTextField txtSearchPartner;

    @FXML
    private StackPane stackPane;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public Partner partner;

    public void initialize(){

        JFXTreeTableColumn<Partner, String> columnId = new JFXTreeTableColumn<>("Cédula");
        columnId.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnId.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getId());
            else return columnId.getComputedValue(param);
        });
        columnId.prefWidthProperty().bind(partnersAuthorizedTable.widthProperty().multiply(0.2));
        columnId.setStyle("-fx-alignment: CENTER;");
        columnId.getStyleClass().add("columns");

        JFXTreeTableColumn<Partner, String> columnName = new JFXTreeTableColumn<>("Nombre");
        columnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnName.getComputedValue(param);
        });
        columnName.prefWidthProperty().bind(partnersAuthorizedTable.widthProperty().multiply(0.8));
        columnName.setStyle("-fx-alignment: CENTER;");
        columnName.getStyleClass().add("columns");

        partnersAuthorizedTable.getColumns().addAll(columnId,columnName);

        JFXTreeTableColumn<Authorized, String> columnAuthorizedName = new JFXTreeTableColumn<>("Nombre");
        columnAuthorizedName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Authorized, String> param) ->{
            if(columnAuthorizedName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnAuthorizedName.getComputedValue(param);
        });
        columnAuthorizedName.prefWidthProperty().bind(authorizedTable.widthProperty().multiply(1));
        columnAuthorizedName.setStyle("-fx-alignment: CENTER;");
        columnAuthorizedName.getStyleClass().add("columns");

        authorizedTable.getColumns().add(columnAuthorizedName);

        partnersAuthorizedTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                fillTableAuthorized(newSelection.getValue());
            }else{
                fillTableAuthorized(null);
            }
        });


    }

    public void handleSearchPartner(){
        String txtToSearch = txtSearchPartner.getText();
        clubToSearch = club.searchPartner(txtToSearch);
        partnersAuthorizedTable.setRoot(null);
        ObservableList<Partner> partners = FXCollections.observableArrayList(clubToSearch.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersAuthorizedTable.setRoot(root);
        partnersAuthorizedTable.setShowRoot(false);
    }

    public void handleAddAuthorized(){
        try {
            club.checkPartner(partner);
            URL url = getClass().getClassLoader().getResource("resources/views/AddAuthorized.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            AddAuthorizedController addAuthorizedController = fxmlLoader.getController();
            addAuthorizedController.setAuthorizedController(this);
            addAuthorizedController.setPartner(partner);
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.getIcons().add(new Image("resources/images/cs.png"));
            dialog.setScene(scene);
            dialog.setResizable(false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            callExceptionDialog(e);
        }
    }

    public void handlePartnersMenu(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/SocialClub.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent;
            parent = fxmlLoader.load();
            PartnersController partnersController = fxmlLoader.getController();
            partnersController.setClub(club);
            partnersController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(parent, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));
            parent.requestFocus();

            partnersController.fillPartnersTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleInvoicesMenu(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/Invoices.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent;
            parent = fxmlLoader.load();
            InvoicesController invoicesController = fxmlLoader.getController();
            invoicesController.setClub(club);
            invoicesController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(parent, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));
            parent.requestFocus();

            invoicesController.fillPartnersTable();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleAbout(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/About.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.getIcons().add(new Image("resources/images/cs.png"));
            dialog.setScene(scene);
            parent.requestFocus();
            dialog.setResizable(false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            callExceptionDialog(e);
        }

    }

    public void fillTableAuthorized(Partner partner){
        authorizedTable.setRoot(null);
        this.partner = partner;
        if (partner != null){
            ObservableList<Authorized> authorized = FXCollections.observableArrayList(this.partner.getAuthorizedAsObject());
            final TreeItem<Authorized> root = new RecursiveTreeItem<>(authorized, RecursiveTreeObject::getChildren);
            authorizedTable.setRoot(root);
            authorizedTable.setShowRoot(false);
        }

    }

    public void fillPartnersTable(){
        ObservableList<Partner> partners = FXCollections.observableArrayList(club.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersAuthorizedTable.setRoot(root);
        partnersAuthorizedTable.setShowRoot(false);
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
            Parent parent = txtSearchPartner.getParent();
            stackPane.setVisible(false);
            parent.requestFocus();
            dialog.close();
        });
        content.setActions(button);
        stackPane.setVisible(true);
        Parent parent = txtSearchPartner.getParent();
        parent.requestFocus();
        dialog.show();
    }

    public void handleRemoveFocus(){
        Parent parent = txtSearchPartner.getParent();
        stackPane.setVisible(false);
        parent.requestFocus();
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
