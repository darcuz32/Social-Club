package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Club;
import model.Partner;

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
