package model;

import java.util.ArrayList;

public class Club {

    //instance variables
    private ArrayList<Partner> partners;

    public Club() {
        this.partners = new ArrayList<>();
    }

    public Partner validatePartner(String id){
        Partner partner = null;
        for (Partner thisPartner: partners) {
            if (thisPartner.getId().equals(id)){
                partner = thisPartner;
            }
        }
        return  partner;
    }

    public void affiliatePartner(String id, String name)throws Exception{
        Partner partner = validatePartner(id);
        if (partner == null) {
            Partner newPartner = new Partner(id,name);
            partners.add(newPartner);
        }else{
            throw new Exception("El socio ya existe.");
        }
    }

    public ArrayList<Partner> getPartners() {
        return partners;
    }

    @Override
    public String toString() {
        return "Club{" +
                "partners=" + partners +
                '}';
    }
}
