/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;


import GUI.Model.Model;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniels PC
 */
public class MainWindowController implements Initializable
{
    private Model model;

    public MainWindowController()
    {
        this.model = model;
    }
    

    @FXML
    private TableView<?> catMovieTableView;
    @FXML
    private TableColumn<?, ?> categoryTableColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void addCategoryEvent(ActionEvent event) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/NewCategory.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        NewCategoryController ncc = fxmlLoader.getController();
        ncc.setModel(model);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1)); 
        stage.show();  
    }

    @FXML
    private void deleteCategoryEvent(ActionEvent event)
    {
    }
    
}
