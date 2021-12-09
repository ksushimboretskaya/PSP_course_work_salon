package models;

import java.io.Serializable;

public class Salon implements Serializable {
    private int id;
    private String salonName;
    private String salonPhone;
    private String salonWebsite;
    private String salonAddress;

    public Salon(int id, String salonName, String salonPhone, String salonWebsite, String salonAddress) {
        this.id = id;
        this.salonName = salonName;
        this.salonPhone = salonPhone;
        this.salonWebsite = salonWebsite;
        this.salonAddress = salonAddress;
    }

    public Salon(String salonName, String salonPhone, String salonWebsite, String salonAddress) {
        this.salonName = salonName;
        this.salonPhone = salonPhone;
        this.salonWebsite = salonWebsite;
        this.salonAddress = salonAddress;
    }

    public Salon(String salonName) {
        this.salonName = salonName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public String getSalonPhone() {
        return salonPhone;
    }

    public void setSalonPhone(String salonPhone) {
        this.salonPhone = salonPhone;
    }

    public String getSalonWebsite() {
        return salonWebsite;
    }

    public void setSalonWebsite(String salonWebsite) {
        this.salonWebsite = salonWebsite;
    }

    public String getSalonAddress() {
        return salonAddress;
    }

    public void setSalonAddress(String salonAddress) {
        this.salonAddress = salonAddress;
    }
}
