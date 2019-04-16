package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import model.Partner;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PartnersController {

    @FXML
    private JFXTreeTableView<Partner> partnersTable;

    @FXML
    JFXButton btnAffiliatePartner = new JFXButton("JFoenix Button");

    @FXML
    private MenuItem btnPartnersMenu;

    @FXML
    private MenuItem btnAuthorizedMenu;

    @FXML
    private MenuItem btnInvoicesMenu;

    @FXML
    private Button btnShow;

    public Club club;

    public void initialize(){

        JFXTreeTableColumn<Partner, String> columnId= new JFXTreeTableColumn<>("Cédula");
        columnId.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnId.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getId());
            else return columnId.getComputedValue(param);
        });
        columnId.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.20));
        columnId.setStyle("-fx-alignment: CENTER;");

        JFXTreeTableColumn<Partner, String> columnName= new JFXTreeTableColumn<>("Nombre");
        columnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnName.getComputedValue(param);
        });
        columnName.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.5));
        columnName.setStyle("-fx-alignment: CENTER;");

        JFXTreeTableColumn<Partner, String> columnAuthorized = new JFXTreeTableColumn<>("Autorizados");
        columnAuthorized.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnAuthorized.validateValue(param)) return new SimpleStringProperty(String.valueOf(param.getValue().getValue().getAuthorizedSize()));
            else return columnAuthorized.getComputedValue(param);
        });
        columnAuthorized.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.15));
        columnAuthorized.setStyle("-fx-alignment: CENTER;");

        JFXTreeTableColumn<Partner, String> columnInvoices = new JFXTreeTableColumn<>("Facturas");
        columnInvoices.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnInvoices.validateValue(param)) return new SimpleStringProperty(String.valueOf(param.getValue().getValue().getInvoicesSize()));
            else return columnInvoices.getComputedValue(param);
        });
        columnInvoices.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.147));
        columnInvoices.setStyle("-fx-alignment: CENTER;");

        partnersTable.getColumns().setAll(columnId, columnName, columnAuthorized, columnInvoices);


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

    public JFXTreeTableView<Partner> getPartnersTable() {
        return partnersTable;
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
