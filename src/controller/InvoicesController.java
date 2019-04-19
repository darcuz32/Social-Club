package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Club;
import model.Invoice;
import model.Partner;

import java.io.IOException;
import java.net.URL;

public class InvoicesController {
    @FXML
    private JFXTreeTableView<Partner> partnersInvoicesTable;

    @FXML
    private JFXTreeTableView<Invoice> invoicesTable;

    @FXML
    private JFXTextField txtSearchPartner;

    @FXML
    private JFXComboBox comboBoxPartner;

    @FXML
    private StackPane stackPane;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public Partner partner;

    public Invoice invoice;

    public void initialize(){

        JFXTreeTableColumn<Partner, String> columnId = new JFXTreeTableColumn<>("Cédula");
        columnId.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnId.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getId());
            else return columnId.getComputedValue(param);
        });
        columnId.prefWidthProperty().bind(partnersInvoicesTable.widthProperty().multiply(0.2));
        columnId.setStyle("-fx-alignment: CENTER;");
        columnId.getStyleClass().add("columns");

        JFXTreeTableColumn<Partner, String> columnName = new JFXTreeTableColumn<>("Nombre");
        columnName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Partner, String> param) ->{
            if(columnName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnName.getComputedValue(param);
        });
        columnName.prefWidthProperty().bind(partnersInvoicesTable.widthProperty().multiply(0.8));
        columnName.setStyle("-fx-alignment: CENTER;");
        columnName.getStyleClass().add("columns");

        partnersInvoicesTable.getColumns().addAll(columnId,columnName);

        partnersInvoicesTable.setPlaceholder(new Label("Sin socios"));

        JFXTreeTableColumn<Invoice, String> columnInvoiceName = new JFXTreeTableColumn<>("Nombre");
        columnInvoiceName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Invoice, String> param) ->{
            if(columnInvoiceName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnInvoiceName.getComputedValue(param);
        });
        columnInvoiceName.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.4));
        columnInvoiceName.setStyle("-fx-alignment: CENTER;");
        columnInvoiceName.getStyleClass().add("columns");

        JFXTreeTableColumn<Invoice, String> columnConcept = new JFXTreeTableColumn<>("Concepto");
        columnConcept.setCellValueFactory((TreeTableColumn.CellDataFeatures<Invoice, String> param) ->{
            if(columnConcept.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getConcept());
            else return columnConcept.getComputedValue(param);
        });
        columnConcept.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.4));
        columnConcept.setStyle("-fx-alignment: CENTER;");
        columnConcept.getStyleClass().add("columns");

        JFXTreeTableColumn<Invoice, String> columnAmount = new JFXTreeTableColumn<>("Monto");
        columnAmount.setCellValueFactory((TreeTableColumn.CellDataFeatures<Invoice, String> param) ->{
            if(columnAmount.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getFormatedAmount());
            else return columnAmount.getComputedValue(param);
        });
        columnAmount.prefWidthProperty().bind(invoicesTable.widthProperty().multiply(0.2));
        columnAmount.setStyle("-fx-alignment: CENTER;");
        columnAmount.getStyleClass().add("columns");

        invoicesTable.getColumns().addAll(columnInvoiceName,columnConcept, columnAmount);

        invoicesTable.setPlaceholder(new Label("Sin facturas"));

        partnersInvoicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                fillComboBoxInvoices(newSelection.getValue());
            }else{
                fillComboBoxInvoices(null);
            }
        });
        comboBoxPartner.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> fillTableInvoices(newSelection));
        invoicesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null){
                invoice = newSelection.getValue();
            }
        });


    }

    public void handleSearchPartner(){
        String txtToSearch = txtSearchPartner.getText();
        clubToSearch = club.searchPartner(txtToSearch);
        partnersInvoicesTable.setRoot(null);
        ObservableList<Partner> partners = FXCollections.observableArrayList(clubToSearch.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersInvoicesTable.setRoot(root);
        partnersInvoicesTable.setShowRoot(false);
    }

    public void handleAddInvoice(){
        try {
            club.checkPartner(partner);
            URL url = getClass().getClassLoader().getResource("resources/views/RegisterConsumption.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            RegisterConsumptionController registerConsumptionController = fxmlLoader.getController();
            registerConsumptionController.setInvoicesController(this);
            registerConsumptionController.setPartner(partner);
            registerConsumptionController.fillComboBox(partner);
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

    public void handlePayInvoice(){
        try {
            club.checkPartner(partner);
            partner.checkInvoice(invoice);
            URL url = getClass().getClassLoader().getResource("resources/views/PayInvoice.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            PayInvoiceController payInvoiceController = fxmlLoader.getController();
            payInvoiceController.setInvoicesController(this);
            payInvoiceController.setPartner(partner);
            payInvoiceController.setInvoice(invoice);
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

    public void handlePartnersMenu(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/SocialClub.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = null;
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

    public void handleRemoveFocus(){
        Parent parent = txtSearchPartner.getParent();
        stackPane.setVisible(false);
        parent.requestFocus();
    }

    public void fillComboBoxInvoices(Partner partner){
        invoicesTable.setRoot(null);
        comboBoxPartner.getItems().clear();
        this.partner = partner;
        if (partner != null){
            comboBoxPartner.getItems().add("Todos");
            comboBoxPartner.getItems().add(partner.getName());
            for (Object thisAuthorized: partner.getAuthorized()) {
                comboBoxPartner.getItems().add(String.valueOf(thisAuthorized));
            }
            comboBoxPartner.getSelectionModel().select(0);
        }

    }

    public void fillTableInvoices(Object clients){
        invoicesTable.setRoot(null);
        Double totalAmount = 0.0;
        if (partner != null){
            ObservableList<Invoice> invoices = FXCollections.observableArrayList(partner.getInvoicesFiltered(String.valueOf(clients)));
            final TreeItem<Invoice> root = new RecursiveTreeItem<>(invoices, RecursiveTreeObject::getChildren);
            invoicesTable.setRoot(root);
            invoicesTable.setShowRoot(false);
        }
    }

    public void fillPartnersTable(){
        ObservableList<Partner> partners = FXCollections.observableArrayList(club.getPartners());
        final TreeItem<Partner> root = new RecursiveTreeItem<>(partners, RecursiveTreeObject::getChildren);
        partnersInvoicesTable.setRoot(root);
        partnersInvoicesTable.setShowRoot(false);
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

    public void setComboBoxValue(String name){
        ObservableList<String> items = comboBoxPartner.getItems();
        for(String thisOption : items){
            if (thisOption.equals(name)){
                comboBoxPartner.getSelectionModel().select(items.indexOf(thisOption));
            }
        }
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
