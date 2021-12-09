package models;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String employee_firstName;
    private String employee_lastName;
    private String employee_service;

    public Employee(int id, String employee_firstName, String employee_lastName, String employee_service) {
        this.id = id;
        this.employee_firstName = employee_firstName;
        this.employee_lastName = employee_lastName;
        this.employee_service = employee_service;
    }

    public Employee(String employee_firstName, String employee_lastName, String employee_service) {
        this.employee_firstName = employee_firstName;
        this.employee_lastName = employee_lastName;
        this.employee_service = employee_service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_firstName() {
        return employee_firstName;
    }

    public void setEmployee_firstName(String employee_firstName) {
        this.employee_firstName = employee_firstName;
    }

    public String getEmployee_lastName() {
        return employee_lastName;
    }

    public void setEmployee_lastName(String employee_lastName) {
        this.employee_lastName = employee_lastName;
    }

    public String getEmployee_service() {
        return employee_service;
    }

    public void setEmployee_service(String employee_service) {
        this.employee_service = employee_service;
    }
}