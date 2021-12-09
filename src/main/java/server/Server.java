package server;

import database.DBWorker;
import javafx.application.Application;
import models.*;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server  implements Runnable {

    private static final String FILE = "client.txt";
    private Socket factSocket;
    private DBWorker db = DBWorker.getInstance();
    private ObjectInputStream inStream;
    private ObjectOutputStream outStream;
    private static String currentUser;


    private String messServ = null;


    Server(Socket socket) throws IOException, SQLException {
        factSocket = socket;

        try {
            inStream = new ObjectInputStream(factSocket.getInputStream());
            outStream = new ObjectOutputStream(factSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String doing = getAddress() + "\t" + "on" + "\t" + new java.util.Date().toString();
        System.out.println(doing);
        writeInFile(doing);
    }

    public void close() {
        try {
            outStream.flush();
            inStream.close();
            outStream.close();
            factSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private String readMessage() {
        try {
            messServ = (String) inStream.readObject();
        } catch (Exception e) {
            System.err.println("Клиент отключился");

        }
        return messServ;
    }

    private void sendMessage(String messServ) {
        try {
            outStream.flush();
            outStream.writeObject(messServ);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object readObject() {
        Object object = null;
        try {
            object = inStream.readObject();
        } catch (Exception e) {
            System.out.println("Нет данных в потоке");
            //e.printStackTrace();
        }

        return object;
    }


    private void sendObject(Object object) {
        try {
            outStream.flush();
            outStream.writeObject(object);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }


    private String getAddress() {
        return factSocket.getInetAddress().toString();
    }

    private void writeInFile(String doing) throws IOException {

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(FILE, true)))) {

            pw.println(doing);

        }
    }

    @Override
    public void run() {
        boolean workFlag = true;
        do {
            messServ = readMessage();

            switch (messServ) {
                case "Shutdown": {
                    try {
                        String doing = getAddress() + "\t" + "off" + "\t" + new java.util.Date().toString();
                        System.out.println(doing);
                        writeInFile(doing);
                        close();
                        System.exit(0);

                    } catch (IOException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    close();
                    workFlag = false;
                }
                break;
                case "LogIn": {
                    User user = (User) readObject();
                    try {
                        currentUser = user.getEmail();
                    } catch (NullPointerException ex) {
                        System.out.println("User is not found");
                    }
                    try {
                        if (db.logIN(user.getEmail(), user.getPassword())) {
                            sendMessage("Good");
                        } else sendMessage("Fail");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "SignIn": {
                    User user = (User) readObject();
                    try {
                        if (db.RegisterNewUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "AddSalon": {
                    Salon salon = (Salon) readObject();
                    try {
                        if (db.insertSalon(salon.getSalonName(), salon.getSalonPhone(), salon.getSalonWebsite(), salon.getSalonAddress())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "AddService": {
                    Services services = (Services) readObject();
                    try {
                        if (db.insertServices(services.getServiceName(), services.getServiceCost())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "AddRecord": {
                    Records records = (Records) readObject();
                    try {
                        if (db.addRecords(records.getSalonName(), records.getClientLastName(), records.getServiceName(), records.getServiceCost(), records.getDate())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "DeleteRecords": {
                    try {
                        if (db.deleteRecord(currentUser)) {
                            sendMessage("Good");
                        } else sendMessage("bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "FindService": {
                    String findService = (String) readObject();
                    try {
                        // sendObject(db.findService(findService));
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "MakeProfile": {
                    sendObject(db.userProfileInfo(currentUser));
                }
                break;
                case "AddEmp": {
                    Employee employee = (Employee) readObject();
                    try {
                        if (db.insertEmployee(employee.getEmployee_firstName(), employee.getEmployee_lastName(), employee.getEmployee_service())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                case "UpdateUser": {
                    User user = (User) readObject();
                    try {
                        if (db.updateUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword())) {
                            sendMessage("Good");
                        } else sendMessage("Bad");
                    } catch (Exception ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                default:
                    close();
                    workFlag = false;
            }
        } while (workFlag);
    }
}