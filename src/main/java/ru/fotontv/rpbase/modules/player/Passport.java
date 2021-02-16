package ru.fotontv.rpbase.modules.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Passport {
    private String pickUpCity = "-";
    private boolean isPickUpCity = false;
    private String dateOfReceipt = "-";
    private String placeOfResidence = "-";
    private List<String> criminalRecords = new ArrayList<>(Collections.singletonList("-"));
    private String profession = "-";

    public String getPickUpCity() {
        return pickUpCity;
    }

    public boolean isPickUpCity() {
        return isPickUpCity;
    }

    public void setPickUpCity(String pickUpCity) {
        this.pickUpCity = pickUpCity;
    }

    public void setIsPickUpCity(boolean isPickUpCity) {
        this.isPickUpCity = isPickUpCity;
    }

    public String getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(String dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public List<String> getCriminalRecords() {
        return criminalRecords;
    }

    public void setCriminalRecords(List<String> criminalRecords) {
        this.criminalRecords = criminalRecords;
    }

    public void addCriminalRecords(String records) {
        this.criminalRecords.remove("-");
        this.criminalRecords.add(records);
    }

    public void remCriminalRecords(String records) {
        this.criminalRecords.remove(records);
        if (this.criminalRecords.isEmpty())
            this.criminalRecords.add("-");
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
