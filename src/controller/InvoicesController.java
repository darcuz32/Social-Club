package controller;

import javafx.beans.property.SimpleStringProperty;
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

public class InvoicesController {
    @FXML
    private TableView<Partner> partnersInvoicesTable;

    @FXML
    private TableView<Invoice> invoicesTable;

    @FXML
    private MenuItem btnPartnersMenu;

    @FXML
    private MenuItem btnAuthorizedMenu;

    @FXML
    private MenuItem btnInvoicesMenu;

    @FXML
    private Button btnAddInvoice;

    @FXML
    private Button btnPayInvoice;

    @FXML
    private TextField txtSearchPartner;

    @FXML
    private ComboBox comboBoxPartner;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public Partner partner;

    public void initialize(){

        TableColumn<Partner, String> columnId = new TableColumn<>("Cédula");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnId.prefWidthProperty().bind(partnersInvoicesTable.widthProperty().multiply(0.2));
        columnId.setStyle("-fx-alignment: CENTER;");

        TableColumn<Partner, String> columnName = new TableColumn<>("Nombre");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.prefWidthProperty().bind(partnersInvoicesTable.widthProperty().multiply(0.8));
        columnName.setStyle("-fx-alignment: CENTER;");

        partnersInvoicesTable.getColumns().addAll(columnId,columnName);
        partnersInvoicesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<Invoice, String> columnInvoiceName = new TableColumn<>("Nombre");
        columnInvoiceName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnInvoiceName.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.4));
        columnInvoiceName.setStyle("-fx-alignment: CENTER;");

        TableColumn<Invoice, String> columnConcept = new TableColumn<>("Concepto");
        columnConcept.setCellValueFactory(new PropertyValueFactory<>("concept"));
        columnConcept.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.4));
        columnConcept.setStyle("-fx-alignment: CENTER;");

        TableColumn<Invoice, String> columnAmount = new TableColumn<>("Monto");
        columnAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        columnAmount.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.2));
        columnAmount.setStyle("-fx-alignment: CENTER;");


        invoicesTable.getColumns().addAll(columnInvoiceName,columnConcept, columnAmount);
        invoicesTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        partnersInvoicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> fillTableInvoices(newSelection));


    }

    public void handleSearchPartner(){
        String txtToSearch = txtSearchPartner.getText();
        clubToSearch = club.searchPartner(txtToSearch);
        partnersInvoicesTable.getItems().clear();
        for (Partner thisPartner: clubToSearch.getPartners()) {
            partnersInvoicesTable.getItems().add(thisPartner);
        }
    }

    public void handleAddInvoice(){
        /*try {
            club.checkPartner(partner);
            URL url = getClass().getClassLoader().getResource("resources/views/AddInvoice.fxml");
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
        }*/
    }

    public void handlePayInvoice(){

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

    public void handleAuthorizedMenu() throws Exception{
        URL url = getClass().getClassLoader().getResource("resources/views/Authorized.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent parent = fxmlLoader.load();
        AuthorizedController authorizedController = fxmlLoader.getController();
        authorizedController.setClub(club);
        authorizedController.setPrimaryStage(primaryStage);
        primaryStage.setScene(new Scene(parent));
        parent.requestFocus();

        authorizedController.getPartnersAuthorizedTable().getItems().clear();
        for (Partner thisPartner: club.getPartners()) {
            authorizedController.getPartnersAuthorizedTable().getItems().add(thisPartner);
        }

    }

    public void handleRemoveFocus(){
        Parent parent = txtSearchPartner.getParent();
        parent.requestFocus();
    }

    public void fillTableInvoices(Partner partner){
        invoicesTable.getItems().clear();
        comboBoxPartner.getItems().clear();
        this.partner = partner;
        if (partner != null){
            for (Invoice thisInvoice: partner.getInvoices()) {
                invoicesTable.getItems().add(thisInvoice);
            }
            comboBoxPartner.getItems().add("Todos");
            comboBoxPartner.getItems().add(partner.getName());
            for (Object thisAuthorized: partner.getAuthorized()) {
                comboBoxPartner.getItems().add(String.valueOf(thisAuthorized));
            }
            comboBoxPartner.getSelectionModel().select(0);
        }

    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TableView<Partner> getPartnersInvoicesTable() {
        return partnersInvoicesTable;
    }
}
