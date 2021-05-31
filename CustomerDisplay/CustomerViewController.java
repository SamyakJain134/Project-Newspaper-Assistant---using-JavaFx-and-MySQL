
package CustomerDisplay;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import CustomerDisplay.UserBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import jdbcc.ConnectToDatabase;

public class CustomerViewController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    
    @FXML // fx:id="comboPaper"
    private ComboBox<String> comboPaper; // Value injected by FXMLLoader
    @FXML // fx:id="comboHawk"
    private ComboBox<String> comboHawk; // Value injected by FXMLLoader

    @FXML // fx:id="doPaper"
    private Button doPaper; // Value injected by FXMLLoader

    @FXML // fx:id="table"
    private TableView<UserBean> table; // Value injected by FXMLLoader

    @FXML
    void doHawker(ActionEvent event) 
    {
    	
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,num,addr,paper);
    	ObservableList<UserBean> ary=gethawkers();
    	table.setItems(ary);
    }

    @FXML
    void doPaper(ActionEvent event) 
    {
    	
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,num,addr,paper,hawker);
    	ObservableList<UserBean> ary=getpapers();
    	table.setItems(ary);
    }
    
    ObservableList<UserBean> getpapers()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	try
    	{
    	pstmt=con.prepareStatement("select * from customer where paper like ?");
    	String pString=comboPaper.getSelectionModel().getSelectedItem();
    	pstmt.setString(1, "%"+pString+"%");
    	//System.out.println(pString);
    	ResultSet rs=pstmt.executeQuery();
    	while(rs.next())
    		{
    			//System.out.println(rs.getString("name"));
    			String name=rs.getString("name");
    			String num=rs.getString("num");
    			String addr=rs.getString("addr");
    			String paper=rs.getString("paper");
    			String hawker=rs.getString("hawker");
    			UserBean ref=new UserBean(name,num,addr,paper,hawker);
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    
    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    ObservableList<UserBean> getAllRecords()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	try
    	{
    	pstmt=con.prepareStatement("select * from customer");
    	ResultSet rs=pstmt.executeQuery();
    	while(rs.next())
    		{
    			String name=rs.getString("name");
    			String num=rs.getString("num");
    			String addr=rs.getString("addr");
    			String paper=rs.getString("paper");
    			String hawker=rs.getString("hawker");
    			UserBean ref=new UserBean(name, num, addr, hawker, paper);
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ObservableList<UserBean> gethawkers()
    {
    	
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	try
    	{
    	pstmt=con.prepareStatement("select * from customer where hawker=?");
    	pstmt.setString(1, comboHawk.getSelectionModel().getSelectedItem());
    	ResultSet rs=pstmt.executeQuery();
    	while(rs.next())
    		{
    		String name=rs.getString("name");
			String num=rs.getString("num");
			String addr=rs.getString("addr");
			String paper=rs.getString("paper");
			String hawker=rs.getString("hawker");
			UserBean ref=new UserBean(name, num, addr, hawker, paper);
			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    @FXML
    void doShowAll(ActionEvent event) 
    {

    	
    	//AudioClip var=new AudioClip(source)
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,num,addr,hawker,paper);
    	ObservableList<UserBean> ary=getAllRecords();
    	table.setItems(ary);
    }
    Connection con;
    PreparedStatement pstmt;
    TableColumn<UserBean, String> name,num,addr,paper,hawker;

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    	//obj=dataConnect.Connect.getConnection();
        con=ConnectToDatabase.getConnection();
        ArrayList<String> hawklst=new ArrayList<String>();
        ArrayList<String> paperlst=new ArrayList<String>();
        try {
			pstmt=con.prepareStatement("select distinct hawker from customer");
			ResultSet table=pstmt.executeQuery();
			while (table.next()) {
				String hawker=table.getString("hawker");
				hawklst.add(hawker);
			}
			comboHawk.getItems().addAll(hawklst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			pstmt=con.prepareStatement("select * from papers");
			ResultSet table=pstmt.executeQuery();
			while(table.next()) {
				String paper=table.getString("paper");
				paperlst.add(paper);
			}
			comboPaper.getItems().addAll(paperlst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        comboHawk.getSelectionModel().select(0);
        comboPaper.getSelectionModel().select(0);
        name=new TableColumn<UserBean, String>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean wali field ka name
    	name.setMinWidth(110);
    	
    	num=new TableColumn<UserBean, String>("Mobile");
    	num.setCellValueFactory(new PropertyValueFactory<>("num"));//bean wali field ka name
    	num.setMinWidth(105);
    	
    	hawker=new TableColumn<UserBean, String>("Hawker");
    	hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));//bean wali field ka name
    	hawker.setMinWidth(100);
    	
    	addr=new TableColumn<UserBean, String>("Address");
    	addr.setCellValueFactory(new PropertyValueFactory<>("addr"));//bean wali field ka name
    	addr.setMinWidth(150);
    	
    	paper=new TableColumn<UserBean, String>("Papers");
    	paper.setCellValueFactory(new PropertyValueFactory<>("paper"));//bean wali field ka name
    	paper.setMinWidth(150);

    }
}
