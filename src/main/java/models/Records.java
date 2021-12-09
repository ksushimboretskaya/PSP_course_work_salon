package models;

import java.io.Serializable;

public class Records implements Serializable {
    private  int id;
    private String salonName;
    private String clientLastName;
    private String serviceName;
    private String serviceCost;
    private String date;

    public Records(int id, String salonName, String clientLastName, String serviceName, String serviceCost, String date) {
        this.id = id;
        this.salonName = salonName;
        this.clientLastName = clientLastName;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.date = date;
    }

    public Records(String salonName, String clientLastName, String serviceName, String serviceCost, String date) {
        this.salonName = salonName;
        this.clientLastName = clientLastName;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
        this.date = date;
    }

    public Records(String salon, String service, String date) {
        salonName=salon;
        serviceName = service;
        this.date = date;
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

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
