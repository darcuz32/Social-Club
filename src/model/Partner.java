package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Partner extends RecursiveTreeObject<Partner> {

    //instance variables
    private String id;
    private String name;
    private ArrayList<Invoice> invoices;
    private ArrayList<String> authorized;

    public Partner(String id, String name) {
        this.id = id;
        this.name = name;
        this.authorized = new ArrayList();
        this.invoices = new ArrayList<>();
    }

    public Partner(String id, String name, ArrayList<String> authorized, ArrayList<Invoice> invoices) {
        this.id = id;
        this.name = name;
        this.authorized = authorized;
        this.invoices = invoices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }

    public ArrayList<String> getAuthorized() {
        return authorized;
    }

    public int getAuthorizedSize() {
        return authorized.size();
    }

    public int getInvoicesSize() {
        return invoices.size();
    }

    public String validateAuthorized(String name){
        String validAuthorized = null;
        for (String thisAuthorized: authorized) {
            String patternString = ".*"+name.trim()+".*";

            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcherAuthorized = pattern.matcher(thisAuthorized);

            boolean matchesAuthorized = matcherAuthorized.matches();
            if (matchesAuthorized){
                validAuthorized = thisAuthorized;
            }
        }
        return  validAuthorized;
    }

    public void addAuthorized(String name) throws Exception{
        String authorized = validateAuthorized(name);
        if (authorized == null) {
            this.authorized.add(name);
        }else{
            throw new Exception("Esta persona ya se encuentra registrada como autorizada de este socio.");
        }
    }

    public void addInvoice(String name, String concept, Double amount){
        Invoice invoice = new Invoice(name, concept, amount);
        this.invoices.add(invoice);
    }

    public void checkInvoice(Invoice invoice) throws Exception{
        if (invoice == null){
            throw new Exception("Seleccione una factura.");
        }
    }



    @Override
    public String toString() {
        return "Partner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", invoices=" + invoices.toString() +
                ", invoices size=" + invoices.size() +
                ", authorized=" + authorized +
                '}';
    }
}
