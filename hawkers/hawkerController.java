package hawkers;

//import java.awt.Image;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
//import javafx.scene.image.*;
import javafx.stage.FileChooser;
import jdbcc.ConnectToDatabase;

public class hawkerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView aadharimage;

    
    @FXML
    private Label lblpath;
    

    @FXML
    private TextField txtnum;

    @FXML
    private TextField txtadd;
    @FXML
    private DatePicker date;
    
    @FXML
    private ComboBox<String> txtname;

    @FXML
    private ListView<String> listbox;

    @FXML
    private TextField txtsal;

    @FXML
    public void doChoosepic(ActionEvent event) 
    {
    	FileChooser file=new FileChooser();
    	File obj=file.showOpenDialog(null);
    	if(obj!=null)
    	{
    		try 
    		{
				FileInputStream fis=new FileInputStream(obj);
				Image img=new Image(fis);
				aadharimage.setImage(img);
				lblpath.setText(obj.getAbsolutePath());
				
				
			} 
    		catch (FileNotFoundException e) 
    		{
				
				e.printStackTrace();
			}
    		
    	}
    }

    @FXML
    void doNew(ActionEvent event) 
    {
		/*
		 * String txtname=""; String txtadd=""; String txtnum=""; String txtsal="";
		 * lblpath.setText("");
		 */
    }

    @FXML
    void doRemove(ActionEvent event) 
    {
    	try 
    	{
			pstmt=con.prepareStatement("delete from hawkers where name=?");
			pstmt.setString(1, txtname.getEditor().getText());
	 
			int count=pstmt.executeUpdate();
			lblpath.setText(count+"Record Deleted");
			
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}
    }
    @FXML
    void doShowAll(ActionEvent event) 
    {
    	try 
    	{
			pstmt=con.prepareStatement("select * from hawkers");
			ResultSet table =pstmt.executeQuery();
			while(table.next())
			{
				String name=table.getString("name");
				int mob=table.getInt("mob");
				String address=table.getString("addr");
				int salary=table.getInt("sal");
				String area=table.getString("area");
				String pic=table.getString("pic");
				System.out.println(name+"  "+mob+"  "+address+"  "+salary+"  "+area+"  "+pic);
			}
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    void doFetch(ActionEvent event) 
    {
    	listbox.getSelectionModel().clearSelection();
    	try 
    	{
			pstmt=con.prepareStatement("select * from hawkers where name=?");
			pstmt.setString(1,txtname.getEditor().getText());
			ResultSet table =pstmt.executeQuery();
			if(table.next())
			{
				txtnum.setText(String.valueOf(table.getInt("mob")));
				txtadd.setText(table.getString("addr"));
				txtsal.setText(String.valueOf(table.getInt("sal")));
				String areas=table.getString("area");
				String [] ar=areas.split(",");
				for(String a:ar)
					listbox.getSelectionModel().select(a);
				
				
				FileInputStream fis;
				try {
					fis = new FileInputStream(new File(table.getString("pic")));
					Image img=new Image(fis);
					aadharimage.setImage(img);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
    	String item=(String) listbox.getSelectionModel().getSelectedItem();
    	//String itemm=(String) combox.getEditor().getText();
    	//FileChooser file=new FileChooser();
    	//File obj=file.showOpenDialog(null);
    	try 
    	{
			pstmt=con.prepareStatement("insert into hawkers values(?,?,?,?,?,?,?)");
			pstmt.setString(1, txtname.getEditor().getText());
			pstmt.setInt(2, Integer.parseInt(txtnum.getText()));
			pstmt.setString(3, txtadd.getText());
			pstmt.setInt(4, Integer.parseInt(txtsal.getText()));
			pstmt.setString(5, item);
			
			pstmt.setString(6, lblpath.getText());
			
			LocalDate ldob=date.getValue();
			java.sql.Date swdob=java.sql.Date.valueOf(ldob);
			
			pstmt.setDate(7, swdob);
			
			
			
			
			
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
    	String item=(String) listbox.getSelectionModel().getSelectedItem();
    	try 
    	{
			pstmt=con.prepareStatement("update hawkers set mob=?, addr=?, sal=?, area=?, pic=?, date=? where name=?");
			pstmt.setString(7, txtname.getEditor().getText());
			pstmt.setInt(1, Integer.parseInt(txtnum.getText()));
			pstmt.setString(2, txtadd.getText());
			pstmt.setInt(3, Integer.parseInt(txtsal.getText()));
			pstmt.setString(4, item);
			
			pstmt.setString(5, lblpath.getText());
			
			LocalDate ldob=date.getValue();
			java.sql.Date swdob=java.sql.Date.valueOf(ldob);
			
			pstmt.setDate(6, swdob);
			 
			pstmt.executeUpdate();
			lblpath.setText("Updated");
			
		} 
    	catch (SQLException e) 
    	{
			
			e.printStackTrace();
		}
    }
Connection con;
PreparedStatement pstmt;
    @FXML
    void initialize() 
    {
    	con=ConnectToDatabase.getConnection();
    	ArrayList<String> list=new ArrayList<String>(Arrays.asList("Phase 3","Baba Farid Nagar","Kamla Nehru Nagar","Patel Nagar","Mittal Mall","Model Town","Ajit Road","Bhagu Road","Bharat Nagar"));
    	listbox.getItems().addAll(list);
    	listbox.getSelectionModel().select(0);
    	
    	listbox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	ArrayList<String> list2=new ArrayList<String>();
    		try 
    		{
    			pstmt=con.prepareStatement("select * from hawkers");
    			ResultSet table =pstmt.executeQuery();
    			while(table.next())
    				{
    					list2.add((table.getString("name")));
    				}
    				txtname.getItems().addAll(list2);
    		} 
    		catch (SQLException e) 
    		{
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
   
    }
}
