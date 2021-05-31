package BillCollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import jdbcc.ConnectToDatabase;

public class collectorController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combonum;

    @FXML
    private ListView<String> listdate;

    @FXML
    private ListView<String> listprice;

    @FXML
    private TextField txtamount;

    @FXML
    void doFetching(ActionEvent event) 
    {
    	float sum=0;
    	String mob=combonum.getSelectionModel().getSelectedItem();
    	try 
    	{
			pstmt=con.prepareStatement("select dob,amount from billing where mob=? and Status=0");
			pstmt.setString(1, mob);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				Date d=rs.getDate("dob");
				String s=d.toString();
				listdate.getItems().add(s);
				
				Float p=rs.getFloat("amount");
				sum+=p;
				String r=p.toString();
				listprice.getItems().add(r);
			}
			
			txtamount.setText(String.valueOf(sum));
			
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    @FXML
    void doPayBill(ActionEvent event) 
    {
    	String mob=combonum.getSelectionModel().getSelectedItem();
    	try
    	{
			pstmt=con.prepareStatement("update billing set Status=1 where mob=? and Status=0");
			pstmt.setString(1, mob);
			pstmt.executeUpdate();
			//show.setText("Transaction Successfull");
    	}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    PreparedStatement pstmt;
    Connection con;
    void getMobInCombo()
    {
    	try 
    	{
			pstmt=con.prepareStatement("select num from customer");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next())
			{
				String s=rs.getString("num");
				combonum.getItems().add(s);
			}
			
		}
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	con=ConnectToDatabase.getConnection();
    	getMobInCombo();
    }
}
