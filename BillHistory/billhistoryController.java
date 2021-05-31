package BillHistory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

//import delete.UserBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import jdbcc.ConnectToDatabase;

public class billhistoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radAllPaid;

    @FXML
    private RadioButton radAllUnpaid;

    @FXML
    private TextField txtMob;

    @FXML
    private RadioButton radCPaid;

    @FXML
    private RadioButton radCUnpaid;

    @FXML
    private TableView<UserBean> table;

    @FXML
    void fetchAll(ActionEvent event) 
    {
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mob"));//bean wali field ka name
    	mob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> noofdays=new TableColumn<UserBean, String>("No Of Days");
    	noofdays.setCellValueFactory(new PropertyValueFactory<>("noofdays"));//bean wali field ka name
    	noofdays.setMinWidth(100);
    	
    	TableColumn<UserBean, String> amount=new TableColumn<UserBean, String>("Amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean wali field ka name
    	amount.setMinWidth(150);
    	
    	TableColumn<UserBean, String> dob=new TableColumn<UserBean, String>("Date Of Billing");
    	dob.setCellValueFactory(new PropertyValueFactory<>("dob"));//bean wali field ka name
    	dob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> Status=new TableColumn<UserBean, String>("Status");
    	Status.setCellValueFactory(new PropertyValueFactory<>("Status"));//bean wali field ka name
    	Status.setMinWidth(105);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(mob,noofdays,amount,dob,Status);

    	table.setItems(getAllRecords());
    }
    
    ObservableList<UserBean> getAllRecords()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	int status=0;
    	try
    	{
    	pstmt=con.prepareStatement("select * from billing where Status=?");
    	if(radAllPaid.isSelected())
    	{
    		status=1;
    	}
    	else {
			status=0;
		}
    	pstmt.setInt(1, status);
    	ResultSet rs=pstmt.executeQuery();
    	while(rs.next())
    		{
    			
    			String mob=rs.getString("mob");
    			String noofdays=rs.getString("noofdays");
    			String dob=rs.getString("dob");
    			String amount=rs.getString("amount");
    			UserBean ref=new UserBean(mob,noofdays,amount,dob,String.valueOf(status));
    			
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
    void fetchCust(ActionEvent event) 
    {
    	TableColumn<UserBean, String> mob=new TableColumn<UserBean, String>("Mobile");
    	mob.setCellValueFactory(new PropertyValueFactory<>("mob"));//bean wali field ka name
    	mob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> noofdays=new TableColumn<UserBean, String>("No Of Days");
    	noofdays.setCellValueFactory(new PropertyValueFactory<>("noofdays"));//bean wali field ka name
    	noofdays.setMinWidth(100);
    	
    	TableColumn<UserBean, String> amount=new TableColumn<UserBean, String>("Amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean wali field ka name
    	amount.setMinWidth(150);
    	
    	TableColumn<UserBean, String> dob=new TableColumn<UserBean, String>("Date Of Billing");
    	dob.setCellValueFactory(new PropertyValueFactory<>("dob"));//bean wali field ka name
    	dob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> Status=new TableColumn<UserBean, String>("Status");
    	Status.setCellValueFactory(new PropertyValueFactory<>("Status"));//bean wali field ka name
    	Status.setMinWidth(105);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(mob,noofdays,amount,dob,Status);

    	table.setItems(getCustomer());
    }
    
    ObservableList<UserBean> getCustomer()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	int status=0;
    	try
    	{
    	pstmt=con.prepareStatement("select * from billing where Status=? and mob=?");
    	if(radCPaid.isSelected())
    	{
    		status=1;
    	}
    	else {
			status=0;
		}
    	pstmt.setInt(1, status);
    	pstmt.setString(2, txtMob.getText());
    	ResultSet rs=pstmt.executeQuery();
    	while(rs.next())
    		{
    			
    			String mob=rs.getString("mob");
    			String noofdays=rs.getString("noofdays");
    			String dob=rs.getString("dob");
    			String amount=rs.getString("amount");
    			UserBean ref=new UserBean(mob,noofdays,amount,dob,String.valueOf(status));
    			
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    Connection con;
    PreparedStatement pstmt;
    @FXML
    void initialize() 
    {
       con=ConnectToDatabase.getConnection();

    }
}
