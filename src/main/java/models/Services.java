package models;

import java.io.Serializable;

public class Services implements Serializable {
    private int id;
    private String serviceName;
    private String serviceCost;

    public Services(int id, String serviceName, String serviceCost) {
        this.id = id;
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public Services(String serviceName, String serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}