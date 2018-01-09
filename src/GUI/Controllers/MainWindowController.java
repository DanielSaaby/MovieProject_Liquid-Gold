/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Model.Model;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Daniels PC
 */
public class MainWindowController implements Initializable
{

    @FXML
    private TableView catMovieTableView;
    @FXML
    private TableColumn categoryTableColumn;
    
 
    private TableView<Category> categoryTableView;
    private TableColumn<Category, String> categoryTableColumnn;
    
    private Model model;

    public MainWindowController() throws SQLException, IOException 
    {
        this.model = new Model();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
       catMovieTableView = categoryTableView;
       categoryTableColumn = categoryTableColumnn;
       
       catMovieTableView.setItems(model.getAllCategory());
       categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
    }    
  

    @FXML
    private void addCategoryEvent(ActionEvent event)
    {
        
    }

    @FXML
    private void deleteCategoryEvent(ActionEvent event)
    {
        
    }
    
}
