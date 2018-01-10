/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.Category;
import GUI.Model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author frederik
 */
public class CategoryAssignmentController implements Initializable {
    
    private Model model;

    @FXML
    private Label headerLbl;
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category, String> categoryTableColumn;
    @FXML
    private TableView<Category> movieCategoryTableView;
    @FXML
    private TableColumn<Category, String> movieCategoryTableColumn;
    
    
    
    void setModel(Model model)
    {
        this.model = model;
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
     
    }    

    @FXML
    private void addCategoryBtn(ActionEvent event) {
    }

    @FXML
    private void removeCategoryBtn(ActionEvent event) {
    }

    @FXML
    private void saveCategoriesBtn(ActionEvent event) {
    }

    void prep(String title) 
    {
       categoryTableView.setItems(model.getAllCategory());
       categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));   
       
       headerLbl.setText("Select Categories for " + title);
       
       movieCategoryTableColumn.setText(title);
    }


    
}
