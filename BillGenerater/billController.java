package BillGenerater;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateStringConverter;
import jdbcc.ConnectToDatabase;

public class billController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> combonum;
    
    @FXML
    private TextField txtdays;

    @FXML
    private ListView<String> listpaper;

    @FXML
    private ListView<String> listprice;

    @FXML
    private TextField txtbill;
LocalDate ldoj;
    @FXML
    void doGetDetails(ActionEvent event) 
    {
    	
    	try 
    	{
    		listpaper.getItems().clear();
        	listprice.getItems().clear();
        	txtbill.setText("");
        	txtdays.setText("");
			pstmt=con.prepareStatement("select * from customer where num=?");
			pstmt.setString(1, combonum.getEditor().getText());
			ResultSet table =pstmt.executeQuery();
			if(table.next())
			{
				java.sql.Date doj=table.getDate("doj");
				ldoj=doj.toLocalDate();
				String[] papers=table.getString("paper").split(",");
				listpaper.getItems().addAll(papers);
				for(String p:papers)
				{
					pstmt=con.prepareStatement("select * from papers where paper=?");
					String[] price=table.getString("price").split(",");
					listprice.getItems().addAll(price);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
long nodays=0;
float bill=0;
    @FXML
    void doPrepareBill(ActionEvent event) 
    {
    	bill=0;
    	ObservableList<String> ob=listprice.getItems();
    	for(String pr:ob)
    	{
    		bill=bill+Float.parseFloat(pr);
    	}
    	Duration diff=Duration.between(ldoj.atStartOfDay(), LocalDate.now().atStartOfDay());
    	long nodays=diff.toDays();
    	
    	bill=bill*nodays;
    	txtbill.setText(String.valueOf(bill));
    	txtdays.setText(String.valueOf(nodays+"Days"));
    	try 
    	{
			pstmt=con.prepareStatement("update customer set doj=DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY) where num=?");
			pstmt.setString(1, combonum.getEditor().getText());
			pstmt.executeUpdate();
		} catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doStore(ActionEvent event) 
    {
    	try 
    	{
    		pstmt=con.prepareStatement("insert into billing(mob,noofdays,dob,amount) values(?,?,?,?)");
    		pstmt.setString(1, combonum.getSelectionModel().getSelectedItem());
    		
    		pstmt.setLong(2, nodays);
    		LocalDate obj=LocalDate.now();
    		Date obj1=Date.valueOf(obj);
    		//Calendar c = Calendar.getInstance(); 
        	//c.setTime(obj1); 
        	//c.add(Calendar.DATE, 1);
        	//obj1=(Date) c.getTime();
        	//LocalDate object = obj1.toLocalDate();
        	//java.sql.Date obj11=java.sql.Date.valueOf(object);
			pstmt.setDate(3,obj1);
			
			pstmt.setFloat(4,bill);
			pstmt.executeUpdate();
			

		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    PreparedStatement pstmt;
    Connection con;
    @FXML
    void initialize() {
    	con=ConnectToDatabase.getConnection();
    	ArrayList<String> mobile=new ArrayList<String>();
		try 
		{
			pstmt=con.prepareStatement("select * from customer");
			ResultSet table =pstmt.executeQuery();
			while(table.next())
				{
					mobile.add((table.getString("num")));	
				}
			combonum.getItems().addAll(mobile);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
