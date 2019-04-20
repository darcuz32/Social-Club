package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Club  implements java.io.Serializable  {

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

    public Club searchPartner(String txtToSearch) {
        Club clubToSearch = new Club();
        for (Partner thisPartner: partners) {
            String patternString = ".*"+txtToSearch+".*";

            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcherName = pattern.matcher(thisPartner.getName());
            Matcher matcherId = pattern.matcher(thisPartner.getId());


           boolean matchesName = matcherName.matches();
            boolean matchesId = matcherId.matches();


             if (matchesId || matchesName){
                Partner newPartner = new Partner(thisPartner.getId(), thisPartner.getName(), thisPartner.getAuthorized(), thisPartner.getInvoices());
                clubToSearch.partners.add(newPartner);
            }
        }
        return  clubToSearch;
    }

    public void affiliatePartner(String id, String name)throws Exception{
        Partner partner = validatePartner(id);
        if (partner == null) {
            Partner newPartner = new Partner(id,name);
            partners.add(newPartner);
        }else{
            throw new Exception("El socio con esta cédula ya existe.");
        }
    }

    public void checkPartner(Partner partner) throws Exception{
        if (partner == null){
            throw new Exception("Seleccione un socio.");
        }
    }


    public void addPartner(String id, String name){
        Partner newPartner = new Partner(id,name);
        partners.add(newPartner);
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
