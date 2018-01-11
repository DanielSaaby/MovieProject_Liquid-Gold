/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Model.Model;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frederik
 */
public class CategoryAssignmentController implements Initializable {
    
    private Model model;
    private Movie movie;
    private ObservableList<Category> movieCategoryObsList = FXCollections.observableArrayList();
    private ObservableList<Category> categoryObsList = FXCollections.observableArrayList();

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
    @FXML
    private Button saveCategoriesbtn;
    
    
    
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
    private void addCategoryBtn(ActionEvent event) 
    {
        
        if(!categoryTableView.getSelectionModel().isEmpty())
        {
            
            Category category = categoryTableView.getSelectionModel().getSelectedItem();
            movieCategoryObsList.add(category);
            
            movieCategoryTableView.setItems(movieCategoryObsList);
            movieCategoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
            
            categoryTableView.getItems().remove(category);
            
            
        }
    }

    @FXML
    private void removeCategoryBtn(ActionEvent event) 
    {
        if(!movieCategoryTableView.getSelectionModel().isEmpty())
        {
            Category category = movieCategoryTableView.getSelectionModel().getSelectedItem();
            categoryObsList.add(category);
            
            categoryTableView.setItems(categoryObsList);
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
            
            movieCategoryTableView.getItems().remove(category);      
        }
    }

    @FXML
    private void saveCategoriesBtn(ActionEvent event) throws SQLException 
    {
        for (Category category : movieCategoryObsList) 
        {
            Boolean isNewMovie = true;
            model.assignMovieCategory(category, movie, isNewMovie);
            
            Stage stage = (Stage) saveCategoriesbtn.getScene().getWindow();
            stage.close(); 
        }
    }

    void prep(String title) throws SQLException 
    {
       
       categoryObsList.addAll(model.getAllCategory());
       
       categoryTableView.setItems(categoryObsList);
       categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));         
       headerLbl.setText("Select Categories for " + title);      
       movieCategoryTableColumn.setText(title);
       
       movie = model.getLatestMovie();
    }


    
}
