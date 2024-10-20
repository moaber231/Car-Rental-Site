package SWING;

import javax.swing.*;

import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MainPage extends JFrame implements ActionListener {
    private JComboBox<String> mainComboBox;
    private JComboBox<String> dependentComboBox;
    private JPanel panel;
    private Map<String, String[]> dependentOptions;
    private static List<String> RentedVehicles = new ArrayList<>();
    
    private String userName = "root";
    private String password = "Csd4861";
    private String url = "jdbc:mysql://localhost:3306/hy360";
    private String query;
    private List<JCheckBox> checkBoxList = new ArrayList<>();
    private static JPanel checkboxPanel;
    JComboBox<String> hourComboBox;
	JComboBox<String> minuteComboBox ;
	 static String idVehicle;
	  String vehiclePrice ;
	static String userDATEInput;
	
	JButton btnL, btnS,btnA ,btnSt;
	
	int collumns;
	int columnNumber;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int width = screenSize.width;
	    int height = screenSize.height;
   public MainPage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        dependentOptions = new HashMap<>();
        dependentOptions.put("CARS", new String[]{"Sedan", "SUV", "Coupe", "Sports","Hatchback","Convertible"});
        dependentOptions.put("MOTORCYCLES", new String[]{"Sport Bike","Cruiser","Touring Bike","Dual-Sport"});
		dependentOptions.put("BICYCLES", new String[]{" "});
        dependentOptions.put("SCOOTER", new String[]{" "});
        mainComboBox = new JComboBox<>(new String[]{"CARS", "MOTORCYCLES", "BICYCLES", "SCOOTER"});
        mainComboBox.addActionListener(this);
        dependentComboBox = new JComboBox<>();
        dependentComboBox.addActionListener(e -> refreshCheckBoxList());
        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.add(mainComboBox);
        panel.add(dependentComboBox);
        panel.add(checkboxPanel);
        
        
        
    		
		btnL=new JButton("Επιστροφή");
		btnL.setBounds(10,150,100,20);
		panel.add(btnL);
		
		btnS=new JButton("Δήλωση Βλάβης");
		btnS.setBounds(300, 300, 100, 100);
		panel.add(btnS);
		
		btnA=new JButton("Δήλωση Ατυχήματος");
		btnA.setBounds(300, 300, 100, 100);
		panel.add(btnA);
		
		btnSt=new JButton("Κατασταση Ενοικιασεων");
		btnSt.setBounds(10,150,100,20);
		panel.add(btnSt);
		
		btnL.addActionListener(e -> returnVehicle());
		setVisible(true);
		
		btnS.addActionListener(e->RepairVehicle());
		setVisible(true);
		
		btnA.addActionListener(e->CrashVehicle());
		setVisible(true);
		
		btnSt.addActionListener(e->RentStatus());
		setVisible(true);
		
		setVisible(true);


        add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        mainComboBox.setSelectedIndex(0);
    }

  public static void RentStatus()
  {
	  String userDATEInputSTART,userDATEInputEND;
	  List<String> matchingDurations = new ArrayList<>();
	  userDATEInputSTART = JOptionPane.showInputDialog(null, "Enter START date: ");  
		userDATEInputEND = JOptionPane.showInputDialog(null, "Enter END date: ");
	    String StartDateHourMinute=userDATEInputSTART;
	    String EndDateHourMinute=userDATEInputEND;
	    System.out.println(StartDateHourMinute);
	    System.out.println(EndDateHourMinute);
	    boolean status=false ;
		int StartYear,EndYear;
		int StartMonth,EndMonth;
		int StartDate,EndDate;
		String wholeOutput="";
		StartYear=Integer.parseInt(""+userDATEInputSTART.charAt(8)+userDATEInputSTART.charAt(9));
		EndYear=Integer.parseInt(""+userDATEInputEND.charAt(8)+userDATEInputEND.charAt(9));		
		
		StartMonth=Integer.parseInt(""+userDATEInputSTART.charAt(3)+userDATEInputSTART.charAt(4));
		EndMonth=Integer.parseInt(""+userDATEInputEND.charAt(3)+userDATEInputEND.charAt(4));	
		
		
		StartDate=Integer.parseInt(""+userDATEInputSTART.charAt(0)+userDATEInputSTART.charAt(1));
		EndDate=Integer.parseInt(""+userDATEInputEND.charAt(0)+userDATEInputEND.charAt(1));	
		
		String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "SELECT Duration FROM rentals"; 
	    String name = null; 
	
	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 
	        ResultSet result = stmt.executeQuery();
	
	        while (result.next()) {
	            int Year;
	            int Month;
	            int Date;
	            name = result.getString("Duration");
	            Year = Integer.parseInt("" + name.charAt(8) + name.charAt(9));
	            Month = Integer.parseInt("" + name.charAt(3) + name.charAt(4));
	            Date = Integer.parseInt("" + name.charAt(0) + name.charAt(1));
	            if (isDateInRange(StartYear, StartMonth, StartDate, EndYear, EndMonth, EndDate, Year, Month, Date)) {
	            	wholeOutput+=name+"\n";
	            }
	        }
	        JOptionPane.showMessageDialog(null, wholeOutput,"This the Date in the area field requested", JOptionPane.INFORMATION_MESSAGE);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
  }
  
  public static boolean isDateInRange(int startYear, int startMonth, int startDate,
          int endYear, int endMonth, int endDate,
	          int testYear, int testMonth, int testDate) {
	LocalDate startDateObj = LocalDate.of(startYear, startMonth, startDate);
	LocalDate endDateObj = LocalDate.of(endYear, endMonth, endDate);
	LocalDate testDateObj = LocalDate.of(testYear, testMonth, testDate);

	
	return testDateObj.isAfter(startDateObj) && testDateObj.isBefore(endDateObj)
	|| testDateObj.isEqual(startDateObj) || testDateObj.isEqual(endDateObj);
	}
@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mainComboBox) {
            String selectedItem = (String) mainComboBox.getSelectedItem();
            if (selectedItem != null) {
                updateDependentComboBox(selectedItem);
            }
        }
    }
       public static JPanel createTimeSelectionPanel() {
        JPanel timeSelectionPanel = new JPanel(new FlowLayout());

        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i); 
        }
        JComboBox<String> hourComboBox = new JComboBox<>(hours);

        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        JComboBox<String> minuteComboBox = new JComboBox<>(minutes);

        timeSelectionPanel.add(new JLabel("Hour:"));
        timeSelectionPanel.add(hourComboBox);
        timeSelectionPanel.add(new JLabel("Minute:"));
        timeSelectionPanel.add(minuteComboBox);

        return timeSelectionPanel;
    }
	private void updateDependentComboBox(String selectedItem) {
        dependentComboBox.removeAllItems();

        String[] items = dependentOptions.get(selectedItem);
        if (items != null) {
            for (String item : items) {
                dependentComboBox.addItem(item);
            }
        }
    }
     public String getQueryForTypeAndCategory(String type, String category) {
        if (type.equals("CARS")) {
            collumns = 7; 
            return "SELECT * FROM CARS WHERE CarType = '" + category + "'";
        } else if (type.equals("MOTORCYCLES")) {
            collumns = 6; 
             return "SELECT * FROM MOTORCYCLES WHERE MotoType = '" + category + "'";
        }
        else if (type.equals("BICYCLES")) {
            collumns = 3; 
             return "SELECT * FROM BICYCLES ";
        }
        else if (type.equals("SCOOTER")) {
            collumns = 4; 
             return "SELECT * FROM SCOOTER ";
        }
        return null;
    }
    public static String ReturnNameCustomer(int id) {
    		String url = "jdbc:mysql://localhost:3306/hy360"; 
		    String userName = "root"; 
		    String password = "Csd4861"; 
		    String query = "SELECT NameLastName FROM customer WHERE idCustomer = ?"; 
		    String name = null; 
		
		    try (Connection con = DriverManager.getConnection(url, userName, password);
		         PreparedStatement stmt = con.prepareStatement(query)) { 
		
		        stmt.setInt(1, id); 
		        ResultSet result = stmt.executeQuery();
		
		        if (result.next()) { 
		            name = result.getString("NameLastName"); 
		            System.out.println("Customer Name: " + name); 
		        } else {
		            System.out.println("Customer with ID " + id + " not found."); 
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return name; 
}
	public static String ReturnPriceVehicle(int id) {
    String url = "jdbc:mysql://localhost:3306/hy360"; 
    String userName = "root"; 
    String password = "Csd4861"; 
    String query = "SELECT Cost FROM vehicles WHERE idVehicle = ?"; 
    String cost = null; 

    try (Connection con = DriverManager.getConnection(url, userName, password);
         PreparedStatement stmt = con.prepareStatement(query)) { 

        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) { 
            cost = result.getString("Cost");
            System.out.println("Vehicle Cost: " + cost); 
        } else {
            System.out.println("Vehicle with ID " + id + " not found."); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cost; 
}
public static String ReturnInsuranceCostVehicle(int id) {
    String url = "jdbc:mysql://localhost:3306/hy360"; 
    String userName = "root"; 
    String password = "Csd4861"; 
    String query = "SELECT InsuranceCost FROM vehicles WHERE idVehicle = ?"; 
    String InsuranceCost = null; 

    try (Connection con = DriverManager.getConnection(url, userName, password);
         PreparedStatement stmt = con.prepareStatement(query)) { 

        stmt.setInt(1, id); 
        ResultSet result = stmt.executeQuery();

        if (result.next()) { 
            InsuranceCost = result.getString("InsuranceCost"); 
            System.out.println("Vehicle Cost: " + InsuranceCost); 
        } else {
            System.out.println("Vehicle with ID " + id + " not found."); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return InsuranceCost; 
}
	public static int CalculateTotalCost(int id)
	{	
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "SELECT Date FROM rentals WHERE idVehicle = ?"; 
	    String Date = null; 
	    String Duration = null;
	    int totlcost;
		int startdate;
		int enddate;
	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 
	    	System.out.println("id before:"+id);
	        stmt.setInt(1, id); 
	        
	        ResultSet result = stmt.executeQuery();
	
	        if (result.next()) { 
	    	    

	        	Date = result.getString("Date"); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	
	    query = "SELECT Duration FROM rentals WHERE idVehicle = ?";
	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 
	
	        stmt.setInt(1, id);
	        ResultSet result = stmt.executeQuery();
	
	        if (result.next()) { 
	            Duration = result.getString("Duration"); 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	      System.out.println("DURATION :");	
	    System.out.println(Duration);
	    System.out.println(Duration.charAt(3));
	    System.out.println(Duration.charAt(4));
	    System.out.println("id after:"+id); 
	    startdate=Integer.parseInt(""+Date.charAt(8)+Date.charAt(9));
	    enddate= Integer.parseInt(""+Duration.charAt(3)+Duration.charAt(4));
	    int Year1,Year2;
	     String y2;
	     int Month1,Month2;
	     y2=""+Duration.charAt(6)+Duration.charAt(7)+Duration.charAt(8)+Duration.charAt(9);
	      Year1=Integer.parseInt(""+Date.charAt(0)+Date.charAt(1)+Date.charAt(2)+Date.charAt(3));
	      Year2=Integer.parseInt(y2);
	      Month1= Integer.parseInt(""+Date.charAt(5)+Date.charAt(6));
	      Month2= Integer.parseInt(""+Duration.charAt(0)+Duration.charAt(1));
	      String query1 = "SELECT hour FROM rentals WHERE idVehicle = ?"; 
	      String query2 = "UPDATE rentals SET RentalDuration = ? WHERE idVehicle = ?";
	      try (Connection con = DriverManager.getConnection(url, userName, password);
	           PreparedStatement stmt = con.prepareStatement(query1);
	           PreparedStatement stmt2 = con.prepareStatement(query2);) { 
	          stmt.setInt(1, id); 
	          
	          String hour = null;
	          String subHour = null;
	          ResultSet result = stmt.executeQuery();
	          int duration = ((Year2 - Year1) * 365 + (Month2 - Month1) * 30 + (enddate - startdate)) * 24;
	          System.out.println(duration);
	          
	          if (result.next()) { 
	              hour = result.getString("hour"); 
	              subHour = hour.substring(0, 2);
	          }
	          int startHour = Integer.parseInt(subHour);
	          String endhour = Duration.substring(10,12);
	          
	          int endHour = Integer.parseInt(endhour);
	          System.out.println("end="+endHour);
	          System.out.println("start"+startHour);
	          duration += (endHour - startHour);
	          System.out.println(duration);
	          stmt2.setInt(1, duration);
	          stmt2.setInt(2, id);
	          
	          int rowsAffected = stmt2.executeUpdate();
	          System.out.println(rowsAffected + " rows updated.");
	      } catch (SQLException e) {
	          e.printStackTrace();
	      }

	      
	      
	     return( (Year2-Year1)*365+(Month2-Month1)*30+(enddate-startdate))*Integer.parseInt(ReturnPriceVehicle(id)); 
	}
public static String ReturnPriceAvailability(int id) {
    String url = "jdbc:mysql://localhost:3306/hy360"; 
    String userName = "root"; 
    String password = "Csd4861"; 
    String query = "SELECT Availability FROM vehicles WHERE idVehicle = ?"; 
    String Availability = null; 
    try (Connection con = DriverManager.getConnection(url, userName, password);
         PreparedStatement stmt = con.prepareStatement(query)) { 

        stmt.setInt(1, id);
        ResultSet result = stmt.executeQuery();

        if (result.next()) { 
            Availability = result.getString("Availability"); 
            System.out.println("Vehicle Cost: " + Availability); 
        } else {
            System.out.println("Vehicle with ID " + id + " not found."); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Availability; 
}
	public static void decreaseAvailability(int id) {
    String url = "jdbc:mysql://localhost:3306/hy360";
    String userName = "root";
    String password = "Csd4861";
    String query = "UPDATE vehicles SET Availability = Availability - 1 WHERE idVehicle = ? AND Availability > 0";

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) {
	
	        stmt.setInt(1, id);
	        int affectedRows = stmt.executeUpdate();  
	
	        if (affectedRows > 0) {
	            System.out.println("Updated the availability successfully for vehicle ID: " + id);
	        } else {
	            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static void increaseAvailability(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360";
	    String userName = "root";
	    String password = "Csd4861";
	    String query = "UPDATE vehicles SET Availability = Availability + 1 WHERE idVehicle = ? AND Availability = 0";

		    try (Connection con = DriverManager.getConnection(url, userName, password);
		         PreparedStatement stmt = con.prepareStatement(query)) {
		
		        stmt.setInt(1, id);
		        int affectedRows = stmt.executeUpdate();  
		
		        if (affectedRows > 0) {
		            System.out.println("Updated the availability successfully for vehicle ID: " + id);
		        } else {
		            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	public static String increaseHasInsurance(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "UPDATE vehicles SET HasInsurance = 1 WHERE idVehicle = ? ";
	    String InsuranceCost = null; 

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 

	    	 stmt.setInt(1, id);
		        int affectedRows = stmt.executeUpdate();  
		
		        if (affectedRows > 0) {
		            System.out.println("Updated the insurance successfully for vehicle ID: " + id);
		        } else {
		            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    return InsuranceCost; 
	}
	public static String decreaseHasInsurance(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "UPDATE vehicles SET HasInsurance = 0 WHERE idVehicle = ? ";
	    String InsuranceCost = null; 

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 

	    	 stmt.setInt(1, id);
		        int affectedRows = stmt.executeUpdate();  
		
		        if (affectedRows > 0) {
		            System.out.println("Updated the insurance successfully for vehicle ID: " + id);
		        } else {
		            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    return InsuranceCost; 
	}
	public static boolean returnHasInsurance(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "SELECT HasInsurance FROM vehicles WHERE idVehicle = ? ";
	    

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 

	    	 stmt.setInt(1, id);
		     ResultSet result=stmt.executeQuery();
		     int hasInsurance=-1;
		     if(result.next())hasInsurance=Integer.parseInt(result.getString(1));
		    if(hasInsurance==1)return true;
		    else return false;
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    return false; 
	}
	public static void MakeDaysToFix(int id,int DaysToFix) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "UPDATE vehicles SET DaysToFix = ? WHERE idVehicle = ? ";

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 
	    	
	    	 stmt.setInt(1, DaysToFix);
	    	 stmt.setInt(2, id);
		        int affectedRows = stmt.executeUpdate();  
		
		        if (affectedRows > 0) {
		            System.out.println("Updated the insurance successfully for vehicle ID: " + id);
		        } else {
		            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	     
	}
	public static void ReturnRentedVehicles(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String queryCar = " SELECT * FROM CARS WHERE idVehicle IN (SELECT idVehicle FROM rentals "
	    		+ "WHERE idCustomer=?);";
	    String queryMoto = " SELECT * FROM motorcycles WHERE idVehicle IN (SELECT idVehicle FROM rentals "
	    		+ "WHERE idCustomer=?);";
	    String queryBic = " SELECT * FROM bicycles WHERE idVehicle IN (SELECT idVehicle FROM rentals "
	    		+ "WHERE idCustomer=?);";
	    String queryScoo = " SELECT * FROM scooter WHERE idVehicle IN (SELECT idVehicle FROM rentals "
	    		+ "WHERE idCustomer=?);";
	    String Model = null; 

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt0 = con.prepareStatement(queryCar);
	    		PreparedStatement stmt1 = con.prepareStatement(queryMoto);
	    		PreparedStatement stmt2 = con.prepareStatement(queryBic);
	    		PreparedStatement stmt3 = con.prepareStatement(queryScoo);) { 

	        stmt0.setInt(1, id); 
	        stmt1.setInt(1, id); 
	        stmt2.setInt(1, id); 
	        stmt3.setInt(1, id); 
	        ResultSet result0 = stmt0.executeQuery();
	        ResultSet result1 = stmt1.executeQuery();
	        ResultSet result2 = stmt2.executeQuery();
	        ResultSet result3 = stmt3.executeQuery();
	        while (result0.next()) { 
	        	
	        		RentedVehicles.add(result0.getString("idVehicle")+" "+ result0.getString("Brand")+" "+result0.getString("Model")); 
	        
	        } 
	        while (result1.next()) { 
	        	RentedVehicles.add(result1.getString("idVehicle")+" "+ result1.getString("Brand")+" "+result1.getString("Model"));
	        }
	        while (result2.next()) { 
	        	RentedVehicles.add(result2.getString("idVehicle")+" "+ result2.getString("Name")); 
	        }
	        while (result3.next()) { 
	        	RentedVehicles.add(result3.getString("idVehicle")+" "+ result3.getString("Name")); 
	        }	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	}
	public static int changeVehicle(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "SELECT * FROM VEHICLES WHERE Availability!=0 AND Category IN (SELECT Category FROM vehicles WHERE idVehicle = ?); ";	    

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	    		PreparedStatement stmt = con.prepareStatement(query);)
	    		 { 
	    	stmt.setInt(1, id);
	    	ResultSet result = stmt.executeQuery();
		       if(result.next())
		       {
		    	   return Integer.parseInt(result.getString("idVehicle"));
		       }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    return -1; 
	}
	public static void deleteFromRentals(int id) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861"; 
	    String query = "DELETE FROM RENTALS WHERE idVehicle = ? ";
	   

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 

	    	 stmt.setInt(1, id);
		        int affectedRows = stmt.executeUpdate();  
		
		        if (affectedRows > 0) {
		            System.out.println("Updated the insurance successfully for vehicle ID: " + id);
		        } else {
		            System.out.println("No rows updated. Check if the ID is correct and availability is greater than 0.");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	 
	}
	public static String ReturnNameFromVehicle(int Newid,int OldId) {
	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	    String userName = "root"; 
	    String password = "Csd4861";
	    String Name=null;
	    String query = "SELECT * FROM VEHICLES WHERE idVehicle = ?;";
	    ResultSet result1;

	    try (Connection con = DriverManager.getConnection(url, userName, password);
	         PreparedStatement stmt = con.prepareStatement(query)) { 

	    	 stmt.setInt(1, Newid);
	    	 result1 = stmt.executeQuery();  
	    	 String result=null;
	    	 if(result1.next()) {
	    		 result=result1.getString(3);	    		 
	    	 }
	    	
	    	 System.out.println("result"+result);
	    	 if(result==null)
	    	 {
	    		 deleteFromRentals(OldId);
	    		 MakeDaysToFix(OldId,3);
	    		 return null;
	    	 }
	    	 else if(result.equals("car"))Name="CARS";
	    	 else if(result.equals("motorycycles"))Name="MOTORCYCLES";
	    	 else if(result.equals("bicycle"))Name="BICYCLES";
	    	 else if(result.equals("scooter"))Name = "SCOOTER";
	    		
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	    String query1 = "UPDATE rentals SET idVehicle = ? WHERE idVehicle=?;";
	    try (Connection con = DriverManager.getConnection(url, userName, password);
		         PreparedStatement stmt = con.prepareStatement(query1);
	    		) { 

		    	 stmt.setInt(1, Newid);
		    	 stmt.setInt(2, OldId);
		    	 int affectedrows = stmt.executeUpdate();  
		    	 decreaseAvailability(Newid);
		    	 if (affectedrows > 0) {
		                System.out.println("Update successful!");
		            } else {
		                System.out.println("No records were updated.");
		            }
	    }	catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    String query2 = "SELECT * FROM "+Name+" WHERE idVehicle = ?;";
	    try (Connection con = DriverManager.getConnection(url, userName, password);
		         PreparedStatement stmt = con.prepareStatement(query2)) { 

		    	 stmt.setInt(1, Newid);
		    	 ResultSet result = stmt.executeQuery();  
		    	 String VehicleName="";
		    	 if(result.next())
		    	 {
		    		 for(int i=1;i<9;i++)VehicleName+= result.getString(i)+ " ";
		    	 }
		    		 System.out.println(VehicleName);
		    		 return VehicleName;
			        
			    } catch (SQLException e) {
			        e.printStackTrace();
			    }
	    return null;
	 
	}
     private void refreshCheckBoxList() {
        String selectedType = (String) mainComboBox.getSelectedItem();
        String selectedCategory = (String) dependentComboBox.getSelectedItem();
        
        if (selectedType != null && selectedCategory != null) {
            try (Connection con = DriverManager.getConnection(url, userName, password);
                 Statement statement = con.createStatement();
                 ResultSet result = statement.executeQuery(getQueryForTypeAndCategory(selectedType, selectedCategory))) {

                checkboxPanel.removeAll();
                checkBoxList.clear();
				 
                while (result.next()) {
                    String Availability=ReturnPriceAvailability(Integer.parseInt(result.getString(2)));
                	String vehicleData = "";
                	if(Integer.parseInt(Availability)!=0)
                	{
	                    for (int i = 1; i <= collumns; i++) {
	                        vehicleData += result.getString(i) + " ";
	                    }
                    JCheckBox checkBox = new JCheckBox(vehicleData);
					checkBox.addItemListener(new ItemListener() {
					    @Override
					    public void itemStateChanged(ItemEvent e) {
					        if (e.getStateChange() == ItemEvent.SELECTED) {
                              	JCheckBox sourceCheckBox = (JCheckBox) e.getSource();
        						String checkBoxText = sourceCheckBox.getText();
        						char c=checkBoxText.charAt(3);
        						char d=checkBoxText.charAt(4);
        						if(d==' ')idVehicle=Character.toString(c);
        						else idVehicle=""+c+d;       						
					        	int response = JOptionPane.showConfirmDialog(
					                checkboxPanel, 
					                "Are you sure you want to rent this?",
					                "Confirmation", 
					                JOptionPane.YES_NO_OPTION, 
					                JOptionPane.QUESTION_MESSAGE
					            );
								
					            if (response == JOptionPane.YES_OPTION) {
                                  userDATEInput = JOptionPane.showInputDialog(null, "Enter a date for the rental:");  
                                  while(!isValidDate(userDATEInput) && !isAfterCurrentValidDate(userDATEInput))
                                  {
                                     userDATEInput = JOptionPane.showInputDialog(null, "Your Date was not Valid \n Enter a date for the rental:");
                               
								   }
										  				
							    JPanel timeSelectionPanel = createTimeSelectionPanel();
							    int result = JOptionPane.showConfirmDialog(null, timeSelectionPanel, 
							            "Select Rental Time", JOptionPane.OK_CANCEL_OPTION);
							  
							    if (result == JOptionPane.OK_OPTION) {
									   
							    	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
								    LocalTime nowTime = LocalTime.now();
								    hourComboBox = (JComboBox<String>) timeSelectionPanel.getComponent(1);
								    minuteComboBox = (JComboBox<String>) timeSelectionPanel.getComponent(3);
								    String selectedHour = (String) hourComboBox.getSelectedItem();
								    String selectedMinute = (String) minuteComboBox.getSelectedItem();
								    String selectedTime = selectedHour + ":" + selectedMinute;
								    LocalTime userTime = LocalTime.parse(selectedTime, timeFormatter);
								    JOptionPane.showMessageDialog(null, "Valid time selected: " + selectedTime);
								    checkBox.setEnabled(false);
								    LocalTime currentTime = LocalTime.now();
							        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
							        String formattedTime = currentTime.format(formatter);
								    System.out.println("YOUR VEHICLE IS RENTED AT: " + selectedTime);
									    try
									    {
                                            PreparedStatement prepared = null;
										    Connection con = DriverManager.getConnection(url, userName, password);
                 							Statement statement = con.createStatement();
                 							String DateHourMinute=userDATEInput+selectedTime;
                 							String sql = "INSERT INTO rentals (NameCustomer, DATE, Price ,idVehicle, idCustomer , Duration,hour,RentalDuration) VALUES "
                 									+ "(?, ?, ?, ?, ?, ?,?,?)";
							                
							                 prepared=con.prepareStatement(sql);
							        		 prepared.setString(1, ReturnNameCustomer(Myframe.IdCustomer));
							        		 prepared.setString(2, LocalDate.now().toString());
							        		 prepared.setInt(3, Integer.parseInt(ReturnPriceVehicle(Integer.parseInt(idVehicle))));
							        		
							        		 prepared.setInt(4, Integer.parseInt(idVehicle));
							        		 prepared.setString(5, Myframe.StringIdCustomer);
							        		 prepared.setString(6, DateHourMinute);
							        		 prepared.setString(7, formattedTime);
							        		 prepared.setInt(8, 0);
							        		 incrementRentCounter(Integer.parseInt(idVehicle));
							 				 decreaseAvailability(Integer.parseInt(idVehicle));
							        		 prepared.executeUpdate();
					
						                }
						                 catch (SQLException el) {
									            el.printStackTrace();
									     }
						                
								 	 }
							    int insuranceResponse = JOptionPane.showConfirmDialog(
				                        checkboxPanel,
				                        "Do you want to add insurance to your rental?",
				                        "Insurance Confirmation",
				                        JOptionPane.YES_NO_OPTION,
				                        JOptionPane.QUESTION_MESSAGE
				                );
									   
							                if(insuranceResponse == JOptionPane.YES_OPTION)
							                {
                                                    JOptionPane.showMessageDialog(null, CalculateTotalCost(Integer.parseInt(idVehicle))+Integer.parseInt(ReturnInsuranceCostVehicle(Integer.parseInt(idVehicle))));
                                                    increaseHasInsurance(Integer.parseInt(idVehicle));
							                }
							                else
							                {
                                                	JOptionPane.showMessageDialog(null, CalculateTotalCost(Integer.parseInt(idVehicle)));
                                            }

					             
					              System.out.println("YOUR VEHICLE IS RENTED");     
					            } else {
					                checkBox.setSelected(false);
					            }
					        }
					    }
					});

                    checkBoxList.add(checkBox);
                    checkboxPanel.add(checkBox);
                    }
                }

                checkboxPanel.revalidate();
                checkboxPanel.repaint();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
     public static void incrementRentCounter(int idVehicle) {
    	 	 String URL = "jdbc:mysql://localhost:3306/hy360";
    	    String USER = "root";
    	     String PASSWORD = "Csd4861";
    	     Connection connection = null;
    	    String sql = "UPDATE vehicles SET RentCounter = RentCounter + 1 WHERE idVehicle = ?";

         try {
             connection = DriverManager.getConnection(URL, USER, PASSWORD);
             try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                 preparedStatement.setInt(1, idVehicle);
                 int rowsAffected = preparedStatement.executeUpdate();
                 if (rowsAffected > 0) {
                     System.out.println("RentCounter incremented for vehicle with idVehicle " + idVehicle);
                 } else {
                     System.out.println("No vehicle found with idVehicle " + idVehicle);
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         } finally {
             try {
                 if (connection != null) {
                     connection.close();
                 }
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
     }
	
     public static boolean isValidDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateString);
            if(dateString.length()!=11)
            {
                return false;
            }
            
            return true;
        } catch (ParseException E) {
            return false;
        }
    }
  
    public static boolean isAfterCurrentValidDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return !date.isBefore(LocalDate.now())&&!date.isEqual(LocalDate.now());
        } catch (DateTimeParseException e) {
     
            return false;
        }
    }
  	
    
    public static void returnVehicle() {
        JFrame newFrame = new JFrame("Return Vehicle");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new FlowLayout());
        RentedVehicles.clear();
        ReturnRentedVehicles(Integer.parseInt(Myframe.StringIdCustomer));
        String[] options = new String[RentedVehicles.size()];
        int i = 0;
        for (i = 0; i < RentedVehicles.size(); i++) {
            options[i] = RentedVehicles.get(i);
        }

        JComboBox<String> comboBox = new JComboBox<>(options);
        newFrame.add(comboBox);

        

        JButton returnButton = new JButton("Return Vehicle");
        newFrame.add(returnButton);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                int response = JOptionPane.showConfirmDialog(
                        newFrame,
                        "Do you want to return this?",
                        "Return Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                		LocalDate currentDate = LocalDate.now();
                	    String Date = currentDate.toString();
	            	    String url = "jdbc:mysql://localhost:3306/hy360"; 
	            	    String userName = "root"; 
	            	    String password = "Csd4861"; 
                		String selectedVehicle = (String) comboBox.getSelectedItem();
                		if(selectedVehicle==null)
                		{
                			JOptionPane.showMessageDialog(newFrame,"You have no cars");
                			newFrame.dispose();
                		}else {
                			
                		
                		
                		int id=Integer.parseInt(selectedVehicle.substring(0,selectedVehicle.indexOf(' ')));
                		String Duration="";
                		int startdate;
                		int enddate;
                		int starthour;
                		int endhour;
                		String Durationquery = "SELECT Duration FROM rentals WHERE idVehicle = ?";
                		    try (Connection con = DriverManager.getConnection(url, userName, password);
                		         PreparedStatement stmt = con.prepareStatement(Durationquery)) { 
                		
                		        stmt.setInt(1, id); 
                		        ResultSet result = stmt.executeQuery();
                		
                		        if (result.next()) { 
                		            Duration = result.getString("Duration");
                		        }
                		    } catch (SQLException el) {
                		        el.printStackTrace();
                		    }
                		    
                		    startdate=Integer.parseInt(""+Date.charAt(8)+Date.charAt(9));
                		    starthour=  LocalDateTime.now().getHour();
                		    endhour=Integer.parseInt(""+Duration.charAt(10)+Duration.charAt(11));
                		    enddate= Integer.parseInt(""+Duration.charAt(3)+Duration.charAt(4));
                		    int Year1,Year2;
                		     String y2;
                		     int Month1,Month2;
                		     y2=""+Duration.charAt(6)+Duration.charAt(7)+Duration.charAt(8)+Duration.charAt(9);
                		      Year1=Integer.parseInt(""+Date.charAt(0)+Date.charAt(1)+Date.charAt(2)+Date.charAt(3));
                		      Year2=Integer.parseInt(y2);
                		      Month1= Integer.parseInt(""+Date.charAt(5)+Date.charAt(6));
                		      Month2= Integer.parseInt(""+Duration.charAt(0)+Duration.charAt(1));
                		  
                		      int timepassed=((Year2-Year1)*365+(Month2-Month1)*30+(enddate-startdate))*24+(endhour-starthour);
                		      System.out.println(timepassed);
	                		if(timepassed<0)
	        				{
	                			 JOptionPane.showMessageDialog(newFrame, "Your return has been confirmed.And extra cost: "+(-1)*timepassed*Integer.parseInt(ReturnPriceVehicle(id)));
	        				}
	                		else
	                		{
	                			JOptionPane.showMessageDialog(newFrame, "Your return has been confirmed.");//gkogkos elegxos wras
	                		}
                        MakeDaysToFix(id,1);
                        decreaseHasInsurance(id);
                        deleteFromRentals(id);
                        RentedVehicles.clear();
                        newFrame.dispose();
                		}
                    }
                 else {
                    JOptionPane.showMessageDialog(newFrame, "Return canceled.");
                }
            }
        });

        newFrame.setSize(300, 200);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }

    
    
    public static void RepairVehicle()
    {
    	JFrame newFrame = new JFrame("Repair Vehicle");
    	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new FlowLayout());
        RentedVehicles.clear();
        ReturnRentedVehicles(Integer.parseInt(Myframe.StringIdCustomer));
        String[] options = new String[RentedVehicles.size()];
        int i=0;
        for(i=0;i<RentedVehicles.size();i++)
        {
        	options[i]=RentedVehicles.get(i);
        }
        JComboBox<String> comboBox = new JComboBox<>(options);
        newFrame.add(comboBox);
        
        JButton DamageVehicle = new JButton("Declare Damaged Vehicle");
        newFrame.add(DamageVehicle);

        DamageVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                int response = JOptionPane.showConfirmDialog(
                        newFrame,
                        "Do you want to declare damage to this vehicle?",
                        "Damage Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                		String selectedVehicle = (String) comboBox.getSelectedItem();
                		if(selectedVehicle==null)
                		{
                			JOptionPane.showMessageDialog(newFrame,"You have no cars");
                			newFrame.dispose();
                		}
                		else {               		               		
                		int id=Integer.parseInt(selectedVehicle.substring(0,selectedVehicle.indexOf(' ')));
                		int newId=changeVehicle(id);
                		String VehilceName=ReturnNameFromVehicle(newId,id);
                		if(VehilceName==null)JOptionPane.showMessageDialog(newFrame,"No Vehicle to return");
                		else JOptionPane.showMessageDialog(newFrame, "Your return has been confirmed.\nNew vehicle:"+VehilceName);
                		decreaseHasInsurance(id);
                		MakeDaysToFix(id,3);
                		if(returnHasInsurance(id)==true)
                		{
                			increaseHasInsurance(newId);
                		}
                		deleteFromRentals(id);
                        newFrame.dispose();
                		}
                    }
                 else {
                    JOptionPane.showMessageDialog(newFrame, "Declare Damage Canceled.");
                }
            }
        });
        
        newFrame.setSize(300, 200);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }
    public static void CrashVehicle()
    {
    	JFrame newFrame = new JFrame("Crashed Vehicle");
    	newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new FlowLayout());
        RentedVehicles.clear();
        ReturnRentedVehicles(Integer.parseInt(Myframe.StringIdCustomer));
        String[] options = new String[RentedVehicles.size()];
        int i=0;
        for(i=0;i<RentedVehicles.size();i++)
        {
        	options[i]=RentedVehicles.get(i);
        }
        
        JComboBox<String> comboBox = new JComboBox<>(options);
        newFrame.add(comboBox);
        
        JButton DamageVehicle = new JButton("Declare Crashed Vehicle");
        newFrame.add(DamageVehicle);

        DamageVehicle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                int response = JOptionPane.showConfirmDialog(
                        newFrame,
                        "Do you want to declare crash to this vehicle?",
                        "Crash Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    

                		String selectedVehicle = (String) comboBox.getSelectedItem();
                		if(selectedVehicle==null)
                		{
                			JOptionPane.showMessageDialog(newFrame,"You have no cars");
                			newFrame.dispose();
                		}
                		else {
                			
                		
                		int id=Integer.parseInt(selectedVehicle.substring(0,selectedVehicle.indexOf(' ')));
                		if(returnHasInsurance(id)==true)
                		{
	                		int newId=changeVehicle(id);
	                		String VehilceName=ReturnNameFromVehicle(newId,id);
	                		if(VehilceName==null)JOptionPane.showMessageDialog(newFrame,"No Vehicle to return");
	                		else JOptionPane.showMessageDialog(newFrame, "Your return has been confirmed.\nNew vehicle:"+VehilceName);//gkogkos elegxos wras
	                        increaseHasInsurance(newId);
	                		MakeDaysToFix(id,3);
	                        decreaseHasInsurance(id);	                      
	                        newFrame.dispose();
                		}
                		else
                		{
                			JOptionPane.showMessageDialog(newFrame,"You Have No Insurance.\nYour Total is:"+3*CalculateTotalCost(id));
                			deleteFromRentals(id);
                			MakeDaysToFix(id,3);
                			newFrame.dispose();
                		}
                		}
                    }
                 else {
                    JOptionPane.showMessageDialog(newFrame, "Declare Crash canceled.");
                }
            }
        });
        newFrame.setSize(300, 200);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainPage();
    }
}