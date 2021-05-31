package form1;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import jdbcc.ConnectToDatabase;

public class papermasterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label lblvalue;

    @FXML
    private ComboBox<String> combox;

    @FXML
    private TextField txtprice;

    @FXML
    private Button doFetch;
    
    @FXML
    void doFetch(ActionEvent event) 
    {
    	
    }

    @FXML
    void doDelete(ActionEvent event) 
    {
    	try 
    	{
			pstmt=con.prepareStatement("delete from papers where paper=?");
			pstmt.setString(1, combox.getEditor().getText());
			//pstmt.setInt(1, Integer.parseInt(comboRoll.getEditor().getText()));
			/*
			 * pstmt.setString(2, txtName.getText()); pstmt.setFloat(3,
			 * Float.parseFloat(txtPer.getText()));
			 */
			
			 
			int count=pstmt.executeUpdate();
			lblvalue.setText(count+"Record Deleted");
			
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}
    }

    @FXML
    void doNew(ActionEvent event) 
    {
    	try 
    	{
			pstmt=con.prepareStatement("select * from papers");
			ResultSet papr=pstmt.executeQuery();
			while(papr.next())
			{
				String name=papr.getString("paper");
				float price=papr.getFloat("price");
				System.out.println(name+"  "+price);
				lblvalue.setText(name);
				
			}
				
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}
    }

    @FXML
    void doSave(ActionEvent event) 
    {
    	//String item=(String) combox.getSelectionModel().getSelectedItem();
    	String itemm=(String) combox.getEditor().getText();
    	try 
    	{
			pstmt=con.prepareStatement("insert into papers values(?,?)");
			pstmt.setString(1, itemm);
			pstmt.setFloat(2, Float.parseFloat(txtprice.getText()));
			lblvalue.setText("Saved");
			//System.out.println(item);
			
			pstmt.executeUpdate();
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
    	try 
    	{
			pstmt=con.prepareStatement("update papers set price=? where paper=?");
			pstmt.setString(2,combox.getEditor().getText());
			//pstmt.setString(1, txtName.getText());
			pstmt.setFloat(1, Float.parseFloat(txtprice.getText()));
			 
			pstmt.executeUpdate();
			lblvalue.setText("Updated");
			
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}

    }
 PreparedStatement pstmt;
 Connection con;
    @FXML
    void initialize() {
    	//ArrayList<String> list=new ArrayList<String>(Arrays.asList("Dainik Bhaskar","Tribune","Hindu"));
    	//combox.getItems().addAll(list);
    	//combox.getSelectionModel().select(0);
    	con=ConnectToDatabase.getConnection();
    	//listitem.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	
    }
}
