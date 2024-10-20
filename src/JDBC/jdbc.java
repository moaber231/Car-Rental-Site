package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;  
import java.lang.Exception; 
import SWING.loginform;
public class jdbc {
    public static void main(String[] args) {
        String userName = "root";
        String password = "Csd4861";
        String url = "jdbc:mysql://localhost:3306/hy360";
        String query = "SELECT * FROM vehicles";

        try {
            Connection con = DriverManager.getConnection(url, userName, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                String vehicleData = "";
           
                for (int i = 1; i <= 8; i++) {
                    vehicleData += result.getString(i) + ':';
                }	
                System.out.println(vehicleData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try  
        {  
            
            loginform form = new loginform();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int width = screenSize.width;
            int height = screenSize.height;
            form.setSize(width,height);  
            form.setVisible(true);    
        }  
        catch(Exception e)  
        {     
          
            JOptionPane.showMessageDialog(null, e.getMessage());  
        }  
    }  
    }

