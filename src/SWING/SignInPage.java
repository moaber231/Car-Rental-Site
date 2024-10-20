package SWING;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



class SignInFrame extends JFrame implements ActionListener{	
	Container c;
	JLabel UserNameLabel,passwordLabel, nameLabel, addressLabel, birthDateLabel, licenceLabel, cardNumberLabel;
	JTextField UserNameField,nameField, addressField, birthDateField, licenceField, cardNumberField;
	JPasswordField passwordField;
	JButton submit;
	SignInFrame()
	{
		setTitle("SignIn form");
		setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = screenSize.width;
	    int height = screenSize.height;
		setSize(width,height);
		setLocation(100,100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		
		UserNameLabel=new JLabel("username");
		passwordLabel=new JLabel("password");
		nameLabel=new JLabel("Name");
		addressLabel=new JLabel("Address");
		birthDateLabel=new JLabel("BirthDate");
		licenceLabel=new JLabel("License");
		cardNumberLabel=new JLabel("Card Number");

		
		UserNameLabel.setBounds(10, 50, 100, 20);
		passwordLabel.setBounds(10, 100, 100, 20);
		nameLabel.setBounds(10, 150, 100, 20);
		addressLabel.setBounds(10, 200, 100, 20);
		birthDateLabel.setBounds(10, 250, 100, 20);
		licenceLabel.setBounds(10, 300, 100, 20);
		cardNumberLabel.setBounds(10, 350, 100, 20);


		
		c.add(UserNameLabel);
		c.add(passwordLabel);
		c.add(nameLabel);
		c.add(addressLabel);
		c.add(birthDateLabel);
		c.add(licenceLabel);
		c.add(cardNumberLabel);


		UserNameField= new JTextField();
		UserNameField.setBounds(120, 50, 120, 20);
		c.add(UserNameField);
		
		passwordField=new JPasswordField();
		passwordField.setBounds(120,100,120,20);
		c.add(passwordField);
		
		nameField=new JTextField();
		nameField.setBounds(120,150,120,20);
		c.add(nameField);
		
		addressField=new JTextField();
		addressField.setBounds(120,200,120,20);
		c.add(addressField);
		
		birthDateField=new JTextField();
		birthDateField.setBounds(120,250,120,20);
		c.add(birthDateField);
		
		licenceField=new JTextField();
		licenceField.setBounds(120,300,120,20);
		c.add(licenceField);
		
		cardNumberField=new JTextField();
		cardNumberField.setBounds(120,350,120,20);
		c.add(cardNumberField);
		
		submit=new JButton("SignUp");
		submit.setBounds(10,400,100,20);
		c.add(submit);
		
		submit.addActionListener(this);
		setVisible(true);
}
	
	public void actionPerformed(ActionEvent e)
	{
        boolean usernameExists = true;
				PreparedStatement checkUserStmt = null;
				PreparedStatement insertStmt = null;
				ResultSet resultSet = null;
				Connection conn = null;
				try {
				    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hy360", "root", "Csd4861");
				    
				    if
		        	(UserNameField.getText().isEmpty() || passwordField.getText().isEmpty() ||nameField.getText().isEmpty() ||addressField.getText().isEmpty() 
		    		||birthDateField.getText().isEmpty() ||licenceField.getText().isEmpty() ||cardNumberField.getText().isEmpty())
		        {
		        	JOptionPane.showMessageDialog(this, "All fields must be filled out ", "Error", JOptionPane.ERROR_MESSAGE);
		        }
				    else
				    {
				    while (usernameExists) {
				        String checkQuery = "SELECT * FROM customer WHERE username = ?";
				        checkUserStmt = conn.prepareStatement(checkQuery);
				        checkUserStmt.setString(1, UserNameField.getText());
				        resultSet = checkUserStmt.executeQuery();
				
				        if (resultSet.next()) {
				            JOptionPane.showMessageDialog(null, "Username already exists. Please enter a different username.");
				            UserNameField.setText(JOptionPane.showInputDialog("Enter a new username:"));
				        }
				        
				    
				        else {
				            usernameExists = false;
				
				            String sql = "INSERT INTO customer (NameLastName, Address, CardNumber ,BirthDate, LicenceNumber , Password, UserName) VALUES (?, ?, ?, ?, ?, ?, ?)";
				            insertStmt = conn.prepareStatement(sql);
				
				            insertStmt.setString(1, nameField.getText());
				            insertStmt.setString(2, addressField.getText());
				            insertStmt.setString(3, cardNumberField.getText());
				            insertStmt.setString(4, birthDateField.getText());
				            insertStmt.setString(5, licenceField.getText());
				            insertStmt.setString(6, passwordField.getText());
				            insertStmt.setString(7, UserNameField.getText());
				            insertStmt.executeUpdate();
				
				            JOptionPane.showMessageDialog(null, "Customer successfully registered!");
				        }
				    }
				   }
				} catch (SQLException el) {
				    el.printStackTrace();
				} finally {
							     try {
			                 if (insertStmt != null) {
			                	 insertStmt.close();
			                 }
			                 if (checkUserStmt != null) {
			                	 checkUserStmt.close();
			                 }
			                 if (conn != null) {
			                	 conn.close();
			                 }
			             } catch (SQLException E) {
			                 E.printStackTrace();
			             }
				    try {
				        if (resultSet != null) resultSet.close();
				        if (checkUserStmt != null) checkUserStmt.close();
				        if (insertStmt != null) insertStmt.close();
				        if (conn != null) conn.close();
				    } catch (SQLException el) {
				        el.printStackTrace();
				    }
				}
        	loginform page = new loginform();  
	         }
}



public class SignInPage{
	public static void main(String args[])
	{
		SignInFrame frame = new SignInFrame();
		
	}
}
