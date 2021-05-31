package dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jdbcc.ConnectToDatabase;

public class dashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void BillCollector(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillCollector/collector.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void BillGenerator(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillGenerater/bill.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void BillHistory(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("BillHistory/billhistory.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void CustomerDisplayBoard(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("CustomerDisplay/CustomerView.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void CustomerManager(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("customermanager/customer.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void HawkerControlPanel(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("hawkers/hawker.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void PaperMaster(MouseEvent event) 
    {
    	try 
    	{
			Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("form1/papermaster.fxml"));
			Scene scene=new Scene(root);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.show();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    PreparedStatement pstmt;
    Connection con;
    @FXML
    void initialize() 
    {
    	con=ConnectToDatabase.getConnection();
    }
}
