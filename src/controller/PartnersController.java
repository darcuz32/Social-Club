package controller;

import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PartnersController {

    @FXML
    private TableView<Partner> partnersTable;

    @FXML
    private MenuItem btnAffiliatePartnerMenu;

    @FXML
    private MenuItem btnPartnersMenu;

    @FXML
    private MenuItem btnAuthorizedMenu;

    @FXML
    private MenuItem btnInvoicesMenu;

    @FXML
    private Button btnShow;

    public Club club;

    public Stage primaryStage;

    public void initialize(){

        TableColumn<Partner, String> columnId= new TableColumn<>("Cédula");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnId.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.20));
        columnId.setStyle("-fx-alignment: CENTER;");

        TableColumn<Partner, String> columnName= new TableColumn<>("Nombre");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.5));
        columnName.setStyle("-fx-alignment: CENTER;");

        TableColumn<Partner, String> columnAuthorized = new TableColumn<>("Autorizados");
        columnAuthorized.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAuthorizedSize())));
        columnAuthorized.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.15));
        columnAuthorized.setStyle("-fx-alignment: CENTER;");

        TableColumn<Partner, String> columnInvoices = new TableColumn<>("Facturas");
        columnInvoices.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAuthorizedSize())));
        columnInvoices.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.15));
        columnInvoices.setStyle("-fx-alignment: CENTER;");

        partnersTable.getColumns().addAll(columnId,columnName, columnAuthorized,columnInvoices);
        partnersTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);


    }

    public void handleAffiliatePartner(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/AffiliatePartner.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            AffiliatePartnerController affiliatePartnerController = fxmlLoader.getController();
            affiliatePartnerController.setPartnersController(this);
            affiliatePartnerController.setClub(club);
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(scene);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleShow(){
        JOptionPane.showMessageDialog(null, club.toString());
    }

    public void handleAuthorizedMenu() throws Exception{
        URL url = getClass().getClassLoader().getResource("resources/views/Authorized.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        AuthorizedController authorizedController = fxmlLoader.getController();
        authorizedController.setClub(club);
        authorizedController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(parent));

        authorizedController.getPartnersAuthorizedTable().getItems().clear();
        for (Partner thisPartner: club.getPartners()) {
            authorizedController.getPartnersAuthorizedTable().getItems().add(thisPartner);
        }

        authorizedController.getPartnersAuthorizedTable().getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> fillTableAuthorized(newSelection, authorizedController.getAuthorizedTable()));

    }

    public void fillTableAuthorized(Partner partner, TableView authorizedTable){
        authorizedTable.getItems().clear();
        if (partner != null){
            for (Object thisAuthorized:partner.getAuthorized()) {
                authorizedTable.getItems().add(String.valueOf(thisAuthorized));
            }
        }

    }

    public void handleRemoveFocus(){
        Parent parent = partnersTable.getParent();
        parent.requestFocus();
    }

    public TableView<Partner> getPartnersTable() {
        return partnersTable;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
