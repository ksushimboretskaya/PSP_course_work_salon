package database;

import models.User;

import java.time.LocalDate;
import java.util.ArrayList;

public interface databaseInterface {
    boolean logIN(String email, String pass);
    boolean RegisterNewUser(String firstName,String lastName,String email,String pass);
    boolean updateUser(String firstName,String lastName,String email,String pass);
    boolean insertSalon(String salonName, String salonPhone, String salonWebsite, String salonAddress);
    boolean insertServices(String serviceName, String serviceCost);
    boolean insertEmployee(String firstName, String lastName, String service);
    boolean deleteRecord(String email);
    ArrayList<User> userProfileInfo(String email);
    int salonsCount();
    int servicesCount();
    int employeeCount();
}
