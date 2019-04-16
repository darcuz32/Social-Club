package model;

import java.text.NumberFormat;

public class Invoice {

    //instance variables
    private String name;
    private String concept;
    private double amount;

    public Invoice(String name, String concept, double amount) {
        this.name = name;
        this.concept = concept;
        this.amount = amount;
    }

    public Invoice() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public double getAmount() {
        return amount;
    }

    public String getFormatedAmount() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amount);
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "name='" + name + '\'' +
                ", concept='" + concept + '\'' +
                ", amount=" + amount +
                '}';
    }
}
