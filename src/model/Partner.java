package model;

import java.util.ArrayList;

public class Partner {

    //instance variables
    private String id;
    private String name;
    private ArrayList<Invoice> invoices;
    private ArrayList<String> authorized;

    public Partner(String id, String name) {
        this.id = id;
        this.name = name;
        this.authorized = new ArrayList();
        this.invoices = new ArrayList();
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

    public ArrayList getInvoices() {
        return invoices;
    }

    public void setInvoices(Invoice invoices) {
        this.invoices.add(invoices);
    }

    public ArrayList getAuthorized() {
        return authorized;
    }

    public int getAuthorizedSize() {
        return authorized.size();
    }

    public int getInvoicesSize() {
        return invoices.size();
    }

    public void setAuthorized(String authorized) {
        this.authorized.add(authorized);
    }

    public void addAuthorized(String nameAuthorized){

    }

    @Override
    public String toString() {
        return "Partner{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", invoices=" + invoices.toString() +
                ", invoices size=" + invoices.size() +
                ", authorized=" + authorized.size() +
                '}';
    }
}
