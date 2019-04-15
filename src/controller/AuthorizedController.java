package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private TableView<Partner> partnersAuthorizedTable;

    @FXML
    private TableView<String> authorizedTable;

    @FXML
    private MenuItem btnPartnersMenu;

    @FXML
    private MenuItem btnAuthorizedMenu;

    @FXML
    private MenuItem btnInvoicesMenu;

    @FXML
    private Button btnAddAuthorized;

    @FXML
    private TextField txtSearchPartner;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public Partner partner;

    public void initialize(){

        TableColumn<Partner, String> columnId = new TableColumn<>("Cédula");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnId.prefWidthProperty().bind(partnersAuthorizedTable.widthProperty().multiply(0.2));
        columnId.setStyle("-fx-alignment: CENTER;");

        TableColumn<Partner, String> columnName = new TableColumn<>("Nombre");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.prefWidthProperty().bind(partnersAuthorizedTable.widthProperty().multiply(0.8));
        columnName.setStyle("-fx-alignment: CENTER;");

        partnersAuthorizedTable.getColumns().addAll(columnId,columnName);
        partnersAuthorizedTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<String, String> columnAuthorizedName = new TableColumn<>("Nombre");
        columnAuthorizedName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        columnAuthorizedName.prefWidthProperty().bind(authorizedTable.widthProperty().multiply(1));
        columnAuthorizedName.setStyle("-fx-alignment: CENTER;");

        authorizedTable.getColumns().add(columnAuthorizedName);
        authorizedTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        partnersAuthorizedTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> fillTableAuthorized(newSelection));


    }

    public void handleSearchPartner(){
        String txtToSearch = txtSearchPartner.getText();
        clubToSearch = club.searchPartner(txtToSearch);
        partnersAuthorizedTable.getItems().clear();
        for (Partner thisPartner: clubToSearch.getPartners()) {
            partnersAuthorizedTable.getItems().add(thisPartner);
        }
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
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }

    public void handlePartnersMenu() throws Exception{
        URL url = getClass().getClassLoader().getResource("resources/views/SocialClub.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        PartnersController partnersController = fxmlLoader.getController();
        partnersController.setClub(club);
        partnersController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(parent));
        parent.requestFocus();

        partnersController.getPartnersTable().getItems().clear();
        for (Partner thisPartner: club.getPartners()) {
            partnersController.getPartnersTable().getItems().add(thisPartner);
        }
    }

    public void handleInvoicesMenu() throws Exception{
        URL url = getClass().getClassLoader().getResource("resources/views/Invoices.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        InvoicesController invoicesController = fxmlLoader.getController();
        invoicesController.setClub(club);
        invoicesController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(parent));
        parent.requestFocus();

        invoicesController.getPartnersInvoicesTable().getItems().clear();
        for (Partner thisPartner: club.getPartners()) {
            invoicesController.getPartnersInvoicesTable().getItems().add(thisPartner);
        }

    }

    public void fillTableAuthorized(Partner partner){
        authorizedTable.getItems().clear();
        this.partner = partner;
        System.out.println(partner);
        if (partner != null){
            for (String thisAuthorized:partner.getAuthorized()) {
               authorizedTable.getItems().add(String.valueOf(thisAuthorized));
            }
        }

    }

    public void handleRemoveFocus(){
        Parent parent = txtSearchPartner.getParent();
        parent.requestFocus();
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TableView<Partner> getPartnersAuthorizedTable() {
        return partnersAuthorizedTable;
    }

    public TableView<String> getAuthorizedTable() {
        return authorizedTable;
    }
}
