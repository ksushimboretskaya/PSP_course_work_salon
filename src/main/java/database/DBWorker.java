package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Services;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBWorker implements databaseInterface {
    private static DBWorker db = null;
    private static final String url = "jdbc:mysql://localhost:3306/mydb";
    private static final String user = "root";
    private static final String password = "Qwerty123456";
    private PreparedStatement preparedStatement;
    private Connection connection;
    private int userid = 0;
    private boolean flag = false;
    private int count = 0;
    private static ArrayList<User> userInfo;

    public Connection getConnection() {
        return connection;
    }

    private DBWorker() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить драйвер");
            Logger.getLogger(DBWorker.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static DBWorker getInstance() throws SQLException {
        if (db == null) {
            db = new DBWorker();
        }
        return db;
    }

    public boolean logIN(String email, String pass) {
        flag = false;
        try {
            preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE (email,password) = (?,?)");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("id");
                flag = true;
            }

        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    public boolean RegisterNewUser(String firstName, String lastName, String email, String pass) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO users(firstName, lastName, email, password) VALUES (?,?,?,?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pass);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DBWorker.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public ArrayList<User> userProfileInfo(String email) {
        userInfo = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT user.name,user.surname,user.email from users where user.email = (?)");
            preparedStatement.setString(1, email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userInfo.add(new User(rs.getString(1), rs.getString(2), rs.getString(3)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return userInfo;
    }

    public boolean insertSalon(String salonName, String salonPhone, String salonWebsite, String salonAddress) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO salons(salonName, salonPhone, salonWebsite, salonAddress) VALUES (?,?,?,?)");
            preparedStatement.setString(1, salonName);
            preparedStatement.setString(2, salonPhone);
            preparedStatement.setString(3, salonWebsite);
            preparedStatement.setString(4, salonAddress);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean addRecords(String salonName, String userName, String serviceName, String serviceCost, String date) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO records(salonName, clientLastNAme, serviceName, serviceCost, date) VALUES (?,?,?,?, ?)");
            preparedStatement.setString(1, salonName);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, serviceName);
            preparedStatement.setString(4, serviceCost);
            preparedStatement.setString(5, date);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateUser(String firstName, String lastName, String email, String pass) {
        try {
            preparedStatement = connection.prepareStatement("UPDATE users SET lastName=?, email=?, password=? where firstName = ? ");
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, pass);
            preparedStatement.setString(4, firstName);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DBWorker.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean insertEmployee(String firstName, String lastName, String service) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO employee( employee_firstName, employee_lastName, employee_service) VALUES (?,?,?)");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, service);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            flag = false;
        }
        return flag;
    }

    public boolean insertServices(String name, String cost) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO services(serviceName, serviceCost) VALUES (?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, cost);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }

    public int salonsCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM salons ");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count(*)");
                flag = true;
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return count;
    }

    public int servicesCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM services");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count(*)");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return count;
    }

    public int employeeCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM employee");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count(*)");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return count;
    }

    public ObservableList<Services> findService(String service) {
        ObservableList<Services> dataList = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement("select serviceName, serviceCost from services  where serviceName = (?)");
            preparedStatement.setString(1, service);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                dataList.add(new Services(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return dataList;
    }

    public boolean deleteRecord(String email) {
        flag = false;
        try {
            preparedStatement = connection.prepareStatement("DELETE from user WHERE email=(?)");
            preparedStatement.setString(1, email);
            preparedStatement.executeUpdate();
            flag = true;
        } catch (Exception e) {
            flag = false;
            e.getMessage();
        }
        return flag;
    }
}