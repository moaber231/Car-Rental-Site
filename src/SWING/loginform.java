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


class Myframe extends JFrame implements ActionListener{
	
	Container c;
	JLabel userlabel,passwlabel;
	JTextField user;
	JPasswordField pass;
	JButton btnL, btnS;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int IdCustomer;
	public static String StringIdCustomer;
    int width = screenSize.width;
    int height = screenSize.height;
	 Myframe()
	 {
		setTitle("LoginForm");
		setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = screenSize.width;
	    int height = screenSize.height;
		setSize(width,height);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		c=getContentPane();
		c.setLayout(null);
		
		userlabel=new JLabel("username");
		
		
		
		passwlabel=new JLabel("password");
		
		userlabel.setBounds(10, 50, 100, 20);
		passwlabel.setBounds(10, 100, 100, 20);
		
		
		c.add(userlabel);
		c.add(passwlabel);

		user= new JTextField();
		user.setBounds(120, 50, 120, 20);
		c.add(user);
		
		pass=new JPasswordField();
		pass.setBounds(120,100,120,20);
		c.add(pass);
		
		btnL=new JButton("Login");
		btnL.setBounds(10,150,100,20);
		c.add(btnL);
		
		btnS=new JButton("SignUp");
		btnS.setBounds(150, 150, 100, 20);
		c.add(btnS);
		
        btnS.addActionListener(e -> {
            SignInFrame regForm = new SignInFrame();
            regForm.setVisible(true);
            dispose(); 
        });
		
		btnL.addActionListener(this);
		setVisible(true);
		
		setVisible(true);
}
	 
public static Integer getCustomerIdByUsername(String username) {
    String url = "jdbc:mysql://localhost:3306/hy360"; 
    String dbUsername = "root"; 
    String dbPassword = "Csd4861"; 
    String query = "SELECT idCustomer FROM customer WHERE username = ?"; 
    Integer customerId = null;

    try (Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, username); 
        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            customerId = result.getInt("idCustomer"); 
            System.out.println("Customer ID: " + customerId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return customerId; 
}
	
	public void actionPerformed(ActionEvent ae)
	{
		PreparedStatement Prepared = null;
    	ResultSet set=null;
    	Connection conn =null;
    	 try {
             conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hy360", "root", "Csd4861");
             String query = "SELECT * FROM customer WHERE username = ? AND password = ?";
             Prepared = conn.prepareStatement(query);
             Prepared.setString(1, user.getText());
             Prepared.setString(2, pass.getText());
             set = Prepared.executeQuery();
             if (set.next()) {
                if("root".equals(user.getText()))
                {
                	JOptionPane.showMessageDialog(null, "Login successful as an administrator!");
                	RootPage newPage = new RootPage();
                    newPage.setVisible(true);
                    dispose(); 
                }
                else if(user.getText().isEmpty() || pass.getText().isEmpty() )
                {
                	
                }
                else {
                	
                
                 JOptionPane.showMessageDialog(null, "Login successful!");
                  IdCustomer=getCustomerIdByUsername(user.getText());
                  StringIdCustomer=getCustomerIdByUsername(user.getText()).toString();
                 	 MainPage page = new MainPage();  
                 page.setVisible(true);  
                  page.setSize(width,height) ;
               
                 JLabel wel_label = new JLabel("Welcome: "+user.getText());  
                 page.getContentPane().add(wel_label);
                 dispose();
                }
             } else {
                 JOptionPane.showMessageDialog(null, "Invalid username or password");
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             try {
                 if (set != null) {
                	 set.close();
                 }
                 if (Prepared != null) {
                	 Prepared.close();
                 }
                 if (conn != null) {
                	 conn.close();
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }

	}
	
}
class RootPage extends JFrame {
	private JComboBox<String> comboBox;
    private JLabel label1, label2, label3, label4, label5, label6,label7,label8;
    private JTextField field1, field2, field3, field4, field5, field6,field7,field8;

    public RootPage() {
        setTitle("Root Page");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] vehicleTypes = {"CARS", "MOTORCYCLES", "BICYCLES", "SCOOTER"};
        comboBox = new JComboBox<>(vehicleTypes);
        comboBox.addActionListener(this::comboBoxActionPerformed);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.add(new JLabel("Select Vehicle Type:"));
        panel.add(comboBox);
        add(panel, BorderLayout.NORTH);

        createFieldsPanel();
        setLocationRelativeTo(null);
    }

    private void createFieldsPanel() {
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(0, 2, 10, 10));

        label1 = new JLabel("Brand:");
        field1 = new JTextField();
        label2 = new JLabel("Model:");
        field2 = new JTextField();
        label3 = new JLabel("Kilometers:");
        field3 = new JTextField();
        label4 = new JLabel("Registration Number:");
        field4 = new JTextField();
        label5 = new JLabel("Type:");
        field5 = new JTextField();
        label6 = new JLabel("Capacity:");
        field6 = new JTextField();
        label7 = new JLabel("Color:");
        field7 = new JTextField();
        label8 = new JLabel("Cost:");
        field8 = new JTextField();
        

        fieldsPanel.add(label1);
        fieldsPanel.add(field1);
        fieldsPanel.add(label2);
        fieldsPanel.add(field2);
        fieldsPanel.add(label3);
        fieldsPanel.add(field3);
        fieldsPanel.add(label4);
        fieldsPanel.add(field4);
        fieldsPanel.add(label5);
        fieldsPanel.add(field5);
        fieldsPanel.add(label6);
        fieldsPanel.add(field6);
        fieldsPanel.add(label7);
        fieldsPanel.add(field7);
        fieldsPanel.add(label8);
        fieldsPanel.add(field8);

        add(fieldsPanel, BorderLayout.CENTER);

        JButton registerButton = new JButton("Register Vehicle");
        registerButton.addActionListener(this::registerButtonActionPerformed);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void comboBoxActionPerformed(ActionEvent e) {
        String selectedType = (String) comboBox.getSelectedItem();
        enableFieldsBasedOnType(selectedType);
    }

    private void enableFieldsBasedOnType(String selectedType) {
        label1.setVisible(true);
        field1.setVisible(true);
        label2.setVisible(true);
        field2.setVisible(true);
        label3.setVisible(true);
        field3.setVisible(true);
        label4.setVisible(true);
        field4.setVisible(true);
        label5.setVisible(true);
        field5.setVisible(true);
        label6.setVisible(true);
        field6.setVisible(true);

        switch (selectedType) {
            case "CARS":
                break;
            case "MOTORCYCLES":
                label6.setVisible(false);
                field6.setVisible(false);
                break;
            case "BICYCLES":
                label2.setVisible(false);
                field2.setVisible(false);
                label3.setVisible(false);
                field3.setVisible(false);
                label4.setVisible(false);
                field4.setVisible(false);
                label5.setVisible(false);
                field5.setVisible(false);
                label6.setVisible(false);
                field6.setVisible(false);
                break;
            case "SCOOTER":
                label2.setVisible(false);
                field2.setVisible(false);
                label4.setVisible(false);
                field4.setVisible(false);
                label5.setVisible(false);
                field5.setVisible(false);
                label6.setVisible(false);
                field6.setVisible(false);
                break;
        }
    }
    public static int MaxIdVehicle() {
        String url = "jdbc:mysql://localhost:3306/hy360"; 
        String userName = "root"; 
        String password = "Csd4861"; 
        String query = "SELECT MAX(idVehicle) AS max_value FROM Vehicles;"; 
        

        try (Connection con = DriverManager.getConnection(url, userName, password);
             PreparedStatement stmt = con.prepareStatement(query)) { 

            
            ResultSet result = stmt.executeQuery();

            if (result.next()) { 
              
                return result.getInt("max_value");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; 
    }
    public static void AddCar(String brand,String model,int kilometers,String regNumber,String Type,String Category,int Cost,String Color,int Capacity) {
    	 String url = "jdbc:mysql://localhost:3306/hy360"; 
         String userName = "root"; 
         String password = "Csd4861"; 
         String query = "INSERT INTO cars (Brand, Model, Kilometers, RestritationNumber, CarType,idVehicle,Capacity) VALUES (?, ?, ?, ?, ?,?,? );"; 
         String queryVehicle = "INSERT INTO vehicles (Color, Category, Cost,idVehicle) VALUES (?, ?, ?,?);"; 
         
         try (Connection con = DriverManager.getConnection(url, userName, password);
                 PreparedStatement stmt = con.prepareStatement(queryVehicle)) { 
            	int max=MaxIdVehicle();
                stmt.setString(1, Color); 
                stmt.setString(2, Category);
                stmt.setInt(3, Cost);
                stmt.setInt(4, max+1);
                //stmt.setInt(6,max+1);
                int result = stmt.executeUpdate();

                if (result>0) { 
                	System.out.println( "Vehicle registered successfully!");
                } 
                else {
                	System.out.println("false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
         try (Connection con = DriverManager.getConnection(url, userName, password);
              PreparedStatement stmt1 = con.prepareStatement(query)) { 
         	int max=MaxIdVehicle();
             stmt1.setString(1, brand); 
             stmt1.setString(2, model);
             stmt1.setInt(3, kilometers);
             stmt1.setString(4, regNumber);
             stmt1.setString(5, Type);
             stmt1.setInt(6, max);
             stmt1.setInt(7, Capacity);
      
             int result = stmt1.executeUpdate();

             if (result>0) { 
             	System.out.println( "Vehicle registered successfully!");
             } 
             else {
             	System.out.println("false");
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
    }
    public static void AddMoto(String brand,String model,int kilometers,String regNumber,String Type,String Category,int Cost,String Color) {
        String url = "jdbc:mysql://localhost:3306/hy360"; 
        String userName = "root"; 
        String password = "Csd4861"; 
        String query = "INSERT INTO motorcycles (Brand, Model, Kilometers, RestritationNumber, MotoType,idVehicle) VALUES (?, ?, ?, ?, ?,? );"; 
        String queryVehicle = "INSERT INTO vehicles (Color, Category, Cost,idVehicle) VALUES (?, ?, ?,?);"; 
        
        try (Connection con = DriverManager.getConnection(url, userName, password);
                PreparedStatement stmt = con.prepareStatement(queryVehicle)) { 
           	int max=MaxIdVehicle();
               stmt.setString(1, Color); 
               stmt.setString(2, Category);
               stmt.setInt(3, Cost);
               stmt.setInt(4, max+1);
             
               int result = stmt.executeUpdate();

               if (result>0) { 
               	System.out.println( "Vehicle registered successfully!");
               } 
               else {
               	System.out.println("false");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        try (Connection con = DriverManager.getConnection(url, userName, password);
             PreparedStatement stmt1 = con.prepareStatement(query)) { 
        	int max=MaxIdVehicle();
            stmt1.setString(1, brand); 
            stmt1.setString(2, model);
            stmt1.setInt(3, kilometers);
            stmt1.setString(4, regNumber);
            stmt1.setString(5, Type);
            stmt1.setInt(6, max);
 
            int result = stmt1.executeUpdate();

            if (result>0) { 
            	System.out.println( "Vehicle registered successfully!");
            } 
            else {
            	System.out.println("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public static void AddBice(String Brand,String Color,String Category,int Cost) {
    	String url = "jdbc:mysql://localhost:3306/hy360"; 
        String userName = "root"; 
        String password = "Csd4861"; 
        String query = "INSERT INTO bicycles (Name,idVehicle) VALUES (?, ? );"; 
        String queryVehicle = "INSERT INTO vehicles (Color, Category, Cost,idVehicle) VALUES (?, ?, ?,?);"; 
        
        try (Connection con = DriverManager.getConnection(url, userName, password);
                PreparedStatement stmt = con.prepareStatement(queryVehicle)) { 
           	int max=MaxIdVehicle();
               stmt.setString(1, Color);
               stmt.setString(2, Category);
               stmt.setInt(3, Cost);
               stmt.setInt(4, max+1);
             
               int result = stmt.executeUpdate();

               if (result>0) { 
               	System.out.println( "Vehicle registered successfully!");
               } 
               else {
               	System.out.println("false");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        try (Connection con = DriverManager.getConnection(url, userName, password);
             PreparedStatement stmt1 = con.prepareStatement(query)) { 
        	int max=MaxIdVehicle();
            stmt1.setString(1, Brand); 
            
            stmt1.setInt(2, max);
         
            int result = stmt1.executeUpdate();

            if (result>0) { 
            	System.out.println( "Vehicle registered successfully!");
            } 
            else {
            	System.out.println("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void AddScoo(String Brand,int Kilometers,String Color,String Category,int Cost) {
    	String url = "jdbc:mysql://localhost:3306/hy360"; 
        String userName = "root"; 
        String password = "Csd4861"; 
        String query = "INSERT INTO scooter (Name,idVehicle,Kilometers) VALUES (?, ?,? );"; 
        String queryVehicle = "INSERT INTO vehicles (Color, Category, Cost,idVehicle) VALUES (?, ?, ?,?);"; 
        
        try (Connection con = DriverManager.getConnection(url, userName, password);
                PreparedStatement stmt = con.prepareStatement(queryVehicle)) { 
           	int max=MaxIdVehicle();
               stmt.setString(1, Color);
               stmt.setString(2, Category);
               stmt.setInt(3, Cost);
               stmt.setInt(4, max+1);
        
               int result = stmt.executeUpdate();

               if (result>0) { 
               	System.out.println( "Vehicle registered successfully!");
               } 
               else {
               	System.out.println("false");
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
        try (Connection con = DriverManager.getConnection(url, userName, password);
             PreparedStatement stmt1 = con.prepareStatement(query)) { 
        	int max=MaxIdVehicle();
            stmt1.setString(1, Brand); 
            
            stmt1.setInt(2, max);
            stmt1.setInt(3, Kilometers);
           
            int result = stmt1.executeUpdate();

            if (result>0) { 
            	System.out.println( "Vehicle registered successfully!");
            } 
            else {
            	System.out.println("false");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void registerButtonActionPerformed(ActionEvent e) {
    	String selectedType = (String) comboBox.getSelectedItem();
    	if (selectedType.equals("CARS") &&
                (field1.getText().isEmpty() || field2.getText().isEmpty() ||
                        field3.getText().isEmpty() || field4.getText().isEmpty() ||
                        field5.getText().isEmpty() || field6.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out for cars", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        } else if (selectedType.equals("MOTORCYCLES") &&
                (field1.getText().isEmpty() || field2.getText().isEmpty() ||
                        field3.getText().isEmpty() || field4.getText().isEmpty() ||
                        field5.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out for motorcycles", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (selectedType.equals("BICYCLES") && field1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "The single field must be filled out for bicycles", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (selectedType.equals("SCOOTER") &&
                (field1.getText().isEmpty() || field3.getText().isEmpty() )) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out for scooters", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    	
    	if (selectedType.equals("MOTORCYCLES"))
    	{
    		AddMoto(field1.getText(),field2.getText(),Integer.parseInt(field3.getText()),field4.getText(),field5.getText(),"motorcycles",Integer.parseInt(field8.getText()),field7.getText());
    	}
    	else if (selectedType.equals("CARS"))
    	{
    		AddCar(field1.getText(),field2.getText(),Integer.parseInt(field3.getText()),field4.getText(),field5.getText(),"car",Integer.parseInt(field8.getText()),field7.getText(),Integer.parseInt(field6.getText()));
    	}
    	else if (selectedType.equals("BICYCLES"))
    	{
    		AddBice(field1.getText(),field7.getText(),"bicycle",Integer.parseInt(field8.getText()));
    	}
    	else if (selectedType.equals("SCOOTER"))
    	{
    		AddScoo(field1.getText(),Integer.parseInt(field3.getText()),field7.getText(),"scooter",Integer.parseInt(field8.getText()));
    	}
    	

        System.out.println("Registration Button Clicked:");
        System.out.println("Brand: " + field1.getText());
        System.out.println("Model: " + field2.getText());
        System.out.println("Kilometers: " + field3.getText());
        System.out.println("Registration Number: " + field4.getText());
        System.out.println("Type: " + field5.getText());
        System.out.println("Capacity: " + field6.getText());
    }
}


class loginform{
	public static void main(String args[])
	{
		Myframe frame = new Myframe();
		
	}
}


