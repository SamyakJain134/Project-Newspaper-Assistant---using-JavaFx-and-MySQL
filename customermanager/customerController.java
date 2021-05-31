package customermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import jdbcc.ConnectToDatabase;

public class customerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtname;


    @FXML
    private ComboBox<String> txtnum;

    @FXML
    private TextField txtadd;

    @FXML
    private ListView<String> listpaper;

    @FXML
    private ListView<String> listprice;

    @FXML
    private ComboBox<String> comboarea;

    @FXML
    private TextField txthawker;

    @FXML
    void doClear(ActionEvent event) 
    {
    	
    }

    @FXML
    void doDelete(ActionEvent event) 
    {

    }
    String allpaper="";
    String allprice="";
    @FXML
    void doSearch(ActionEvent event) 
    {
    	listpaper.getSelectionModel().clearSelection();
    	listprice.getSelectionModel().clearSelection();
    	try 
    	{
			pstmt=con.prepareStatement("select * from customer where num=?");
			pstmt.setString(1,txtnum.getEditor().getText());
			ResultSet table =pstmt.executeQuery();
			if(table.next())
			{
				String name=table.getString("name");
				String address=table.getString("addr");
				String hawker=table.getString("hawker");
				allpaper=table.getString("paper");
				allprice=table.getString("price");
				String ary[]=allpaper.split(",");
				for(String string : ary)
					listpaper.getSelectionModel().select(string);
				String aryy[]=allprice.split(",");
				for(String string : aryy)
					listprice.getSelectionModel().select(string);
				txtname.setText(name);
				txtadd.setText(address);
				txthawker.setText(hawker);
			}				
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
		}
    }
    @FXML
    void findHawker(ActionEvent event) 
    {
    	
    	try 
    	{
    		String s="";
        	String area=comboarea.getSelectionModel().getSelectedItem();
			pstmt=con.prepareStatement("select name from hawkers where area like ?");
			pstmt.setString(1, "%"+area+"%");
			ResultSet table =pstmt.executeQuery();
			while(table.next())
			{
				s=table.getString("name");
			}
			txthawker.setText(s);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doStore(ActionEvent event) 
    {
    	try 
    	{
			pstmt=con.prepareStatement("insert into customer values(?,?,?,?,?,?,?)");
			pstmt.setString(1, txtname.getText());
			pstmt.setString(2, txtnum.getEditor().getText());
			pstmt.setString(3, txtadd.getText());
			String allpaper="";
			ObservableList<String> papers=listpaper.getSelectionModel().getSelectedItems();
			for(String string : papers)
			{
				allpaper=allpaper+string+",";
			}
			pstmt.setString(4, allpaper);
			String allprice="";
			ObservableList<Integer> indices=listpaper.getSelectionModel().getSelectedIndices();
			for(int i=0;i<indices.size();i++)
			{
				int index=indices.get(i);
				String p=price.get(index);
				allprice=allprice+p+",";
			}
			pstmt.setString(5, allprice);
			
			
			pstmt.setString(6, txthawker.getText());
		
			LocalDate obj=LocalDate.now();
			java.sql.Date obj1=java.sql.Date.valueOf(obj);
			pstmt.setDate(7, obj1);
			pstmt.executeUpdate();
			//Alert alert=new Alert(AlertType.INFORMATION);
			//alert.setTitle("Successful");
			//alert.setContentText("Saved");
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void doUpdate(ActionEvent event) 
    {
    	String item=(String) listpaper.getSelectionModel().getSelectedItem();
    	try 
    	{
			pstmt=con.prepareStatement("update customer set num=?, addr=?, paper=?, price=?, hawker=? where name=?");
			pstmt.setString(6, txtname.getText());
			pstmt.setInt(1, Integer.parseInt(txtnum.getEditor().getText()));
			pstmt.setString(2, txtadd.getText());
			//pstmt.setInt(3, Integer.parseInt(listpaper.getText()));
			//pstmt.setString(4, item);
			
			pstmt.setString(5, txthawker.getText());
			
			pstmt.executeUpdate();
			//lblpath.setText("Updated");
			
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}
    }
    PreparedStatement pstmt;
    Connection con;
    ArrayList<String> price;
    
    @FXML
    void initialize() 
    {
    	con=ConnectToDatabase.getConnection();
    	
    	
    	ArrayList<String> papers=new ArrayList<String>();
    	price=new ArrayList<String>();
		try 
		{
			pstmt=con.prepareStatement("select * from papers");
			ResultSet table =pstmt.executeQuery();
			while(table.next())
				{
					papers.add((table.getString("paper")));
					price.add((table.getString("price")));	
				}
				listpaper.getItems().addAll(papers);
				listprice.getItems().addAll(price);
				listpaper.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ArrayList<String> list=new ArrayList<String>(Arrays.asList("Select Area","Phase 3","Baba Farid Nagar","Kamla Nehru Nagar","Patel Nagar","Mittal Mall","Model Town","Ajit Road","Bhagu Road","Bharat Nagar"));
    	comboarea.getItems().addAll(list);
    	comboarea.getSelectionModel().select(0);
    	
    	
    	ArrayList<String> mobile=new ArrayList<String>();
		try 
		{
			pstmt=con.prepareStatement("select * from customer");
			ResultSet table =pstmt.executeQuery();
			while(table.next())
				{
					mobile.add((table.getString("num")));	
				}
			txtnum.getItems().addAll(mobile);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
