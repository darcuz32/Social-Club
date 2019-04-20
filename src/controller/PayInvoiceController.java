package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Club;
import model.Invoice;
import model.Partner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PayInvoiceController {
    @FXML
    private Label lblPartner;

    @FXML
    private Label lblClient;

    @FXML
    private Label lblConcept;

    @FXML
    private Label lblAmount;

    public InvoicesController invoicesController;

    public Partner partner;

    public Invoice invoice;

    public Club club;

    public void initialize(){

    }

    public void handleDonePayInvoice(){

        partner.getInvoices().removeIf(thisInvoice -> thisInvoice.equals(invoice));
        try {

            String filename = "club.tmp";

            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(club);

            out.close();
            file.close();
        } catch(IOException ex) {
            System.out.println("IOException is caught\n"+ex.getMessage());
        }catch (Exception e){
            System.out.println(e);
            return;
        }
        invoicesController.fillTableInvoices("Todos");
        invoicesController.setComboBoxValue("Todos");
        ((Stage) lblPartner.getScene().getWindow()).close();
        invoicesController.handleRemoveFocus();
    }

    public void handleCancelPayment(){
        ((Stage) lblPartner.getScene().getWindow()).close();
    }

    public void handleRemoveFocus(){
        Parent parent = lblPartner.getParent();
        parent.requestFocus();
    }

    public void setInvoicesController(InvoicesController invoicesController) {
        this.invoicesController = invoicesController;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
        lblPartner.setText(partner.getName());
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
        lblAmount.setText(invoice.getFormatedAmount());
        lblClient.setText(invoice.getName());
        lblConcept.setText(invoice.getConcept());
    }

    public void setClub(Club club) {
        this.club = club;
    }
}
