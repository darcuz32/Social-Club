package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
