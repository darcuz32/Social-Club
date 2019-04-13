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
import model.Partner;

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

    public void initialize(){

        TableColumn<Partner, String> columnId= new TableColumn<>("Cédula");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnId.prefWidthProperty().bind(partnersTable.widthProperty().divide(4));

        TableColumn<Partner, String> columnName= new TableColumn<>("Nombre");
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnName.prefWidthProperty().bind(partnersTable.widthProperty().divide(2));

        TableColumn<Partner, String> columnAuthorized = new TableColumn<>("Autorizados");
        columnAuthorized.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAuthorizedSize())));
        columnAuthorized.prefWidthProperty().bind(partnersTable.widthProperty().divide(4));

        partnersTable.getColumns().addAll(columnId,columnName, columnAuthorized);


    }

    public void handleAffiliatePartner(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/AffiliatePartner.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            AffiliatePartnerController affiliatePartnerController = fxmlLoader.getController();
            affiliatePartnerController.setPartnersController(this);
            Scene scene = new Scene(parent);
            Stage dialogo = new Stage();
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.setScene(scene);
            dialogo.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TableView<Partner> getPartnersTable() {
        return partnersTable;
    }
}
