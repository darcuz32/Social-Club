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
import model.Authorized;
import model.Club;
import model.Partner;

import java.io.IOException;
import java.net.URL;

public class AuthorizedController {
    @FXML
    private JFXTreeTableView<Partner> partnersAuthorizedTable;

    @FXML
    private JFXTreeTableView<Authorized> authorizedTable;

    @FXML
    private JFXTextField txtSearchPartner;

    @FXML
    private StackPane stackPane;

    public Club club;

    public Club clubToSearch;

    public Stage primaryStage;

    public Partner partner;

    public String theme;

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

        partnersAuthorizedTable.setPlaceholder(new Label("Sin socios"));

        JFXTreeTableColumn<Authorized, String> columnAuthorizedName = new JFXTreeTableColumn<>("Nombre");
        columnAuthorizedName.setCellValueFactory((TreeTableColumn.CellDataFeatures<Authorized, String> param) ->{
            if(columnAuthorizedName.validateValue(param)) return new SimpleStringProperty(param.getValue().getValue().getName());
            else return columnAuthorizedName.getComputedValue(param);
        });
        columnAuthorizedName.prefWidthProperty().bind(authorizedTable.widthProperty().multiply(1));
        columnAuthorizedName.setStyle("-fx-alignment: CENTER;");
        columnAuthorizedName.getStyleClass().add("columns");

        authorizedTable.getColumns().add(columnAuthorizedName);

        authorizedTable.setPlaceholder(new Label("Sin autorizados"));

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
            addAuthorizedController.setClub(club);
            addAuthorizedController.setPartner(partner);
            Scene scene = new Scene(parent);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.getIcons().add(new Image("resources/images/cs.png"));
            dialog.setScene(scene);
            dialog.getScene().getStylesheets().clear();
            dialog.getScene().getStylesheets().add("resources/styles/"+theme);
            dialog.setResizable(false);
            dialog.show();
            handleRemoveFocus();
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
            partnersController.theme = this.theme;
            partnersController.setClub(club);
            partnersController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(parent, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));
            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add("resources/styles/"+theme);
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
            invoicesController.theme = this.theme;
            invoicesController.setClub(club);
            invoicesController.setPrimaryStage(primaryStage);
            primaryStage.setScene(new Scene(parent, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));
            primaryStage.getScene().getStylesheets().clear();
            primaryStage.getScene().getStylesheets().add("resources/styles/"+theme);
            parent.requestFocus();

            invoicesController.fillPartnersTable();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleConfig(){
        try {
            URL url = getClass().getClassLoader().getResource("resources/views/Config.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            ConfigController configController = fxmlLoader.getController();
            configController.theme = this.theme;
            configController.setClub(club);
            configController.setPrimaryStage(primaryStage);
            configController.setAuthorizedController(this);
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.getIcons().add(new Image("resources/images/cs.png"));
            dialog.setScene(scene);
            dialog.getScene().getStylesheets().clear();
            dialog.getScene().getStylesheets().add("resources/styles/"+theme);
            parent.requestFocus();
            dialog.setResizable(false);
            configController.setDialog(dialog);
            dialog.show();
            handleRemoveFocus();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            callExceptionDialog(e);
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
            dialog.getScene().getStylesheets().clear();
            dialog.getScene().getStylesheets().add("resources/styles/"+theme);
            parent.requestFocus();
            dialog.setResizable(false);
            dialog.show();
            handleRemoveFocus();
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
        handleRemoveFocus();
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
