package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
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
import model.Club;
import model.Invoice;
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

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField txtSearchPartner;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public void initialize(){

        JFXTreeTableColumn<Partner, String> columnId= new JFXTreeTableColumn<>("Cédula");
        columnId.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnId.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getId());
            else return columnId.getComputedValue(param);
        });
        columnId.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.20));
        columnId.setStyle("-fx-alignment: CENTER;");
        columnId.getStyleClass().add("columns");

        JFXTreeTableColumn<Partner, String> columnName= new JFXTreeTableColumn<>("Nombre");
        columnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnName.getComputedValue(param);
        });
        columnName.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.5));
        columnName.setStyle("-fx-alignment: CENTER;");
        columnName.getStyleClass().add("columns");

        JFXTreeTableColumn<Partner, String> columnAuthorized = new JFXTreeTableColumn<>("Autorizados");
        columnAuthorized.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnAuthorized.validateValue(param)) return new SimpleStringProperty(String.valueOf(param.getValue().getValue().getAuthorizedSize()));
            else return columnAuthorized.getComputedValue(param);
        });
        columnAuthorized.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.15));
        columnAuthorized.setStyle("-fx-alignment: CENTER;");
        columnAuthorized.getStyleClass().add("columns");

        JFXTreeTableColumn<Partner, String> columnInvoices = new JFXTreeTableColumn<>("Facturas");
        columnInvoices.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnInvoices.validateValue(param)) return new SimpleStringProperty(String.valueOf(param.getValue().getValue().getInvoicesSize()));
            else return columnInvoices.getComputedValue(param);
        });
        columnInvoices.prefWidthProperty().bind(partnersTable.widthProperty().multiply(0.147));
        columnInvoices.setStyle("-fx-alignment: CENTER;");
        columnInvoices.getStyleClass().add("columns");

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
            dialog.getIcons().add(new Image("resources/images/cs.png"));
            dialog.setScene(scene);
            dialog.setResizable(false);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSearchPartner(){
        String txtToSearch = txtSearchPartner.getText();
        clubToSearch = club.searchPartner(txtToSearch);
        partnersTable.setRoot(null);
        ObservableList<Partner> partners = FXCollections.observableArrayList(clubToSearch.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersTable.setRoot(root);
        partnersTable.setShowRoot(false);
    }

    public void handleShow(){
        JOptionPane.showMessageDialog(null, club.toString());
    }

    public void handleAuthorizedMenu(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/Authorized.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent;
            parent = fxmlLoader.load();
            AuthorizedController authorizedController = fxmlLoader.getController();
            authorizedController.setClub(club);
            authorizedController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(parent, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));
            parent.requestFocus();

            authorizedController.fillPartnersTable();
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

    public void fillPartnersTable(){
        ObservableList<Partner> partners = FXCollections.observableArrayList(club.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
       partnersTable.setRoot(root);
       partnersTable.setShowRoot(false);
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
        Parent parent = partnersTable.getParent();
        parent.requestFocus();
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
