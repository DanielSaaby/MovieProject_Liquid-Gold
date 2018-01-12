/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.Category;
import BE.Movie;
import GUI.Model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniels PC
 */
public class MainWindowController implements Initializable
{
    private Model model;

    @FXML
    private TableView<Category> catMovieTableView;
    @FXML
    private TableColumn<Category, String> categoryTableColumn;

    @FXML
    private TableView<Movie> movieTableView;
    @FXML
    private TableColumn<Movie, String> movieTableColumn;
    
    
  
    @FXML
    private Button addCategoryBtn;
    @FXML
    private AnchorPane movieInfoAp;
    @FXML
    private Label movieTitleLbl;
    @FXML
    private AnchorPane movieSelectionAp;
    @FXML
    private Label movieRatingLbl;
    @FXML
    private Label movieLastviewLbl;
    
    
    
    
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
       catMovieTableView.setItems(model.getAllCategory());
       categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
       catMovieTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
   
        movieTableView.setItems(model.getAllMovieCategory());
        movieTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        movieTableView.setPlaceholder(new Label("This category is empty"));
   
        movieSelectionAp.setVisible(false);
        movieInfoAp.setVisible(false);

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
    private void addMovieBtn(ActionEvent event) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovie.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        AddMovieController amc = fxmlLoader.getController();
        amc.setModel(model);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1)); 
        stage.show();         
    }
    
    
    @FXML
    private void deleteCategoryEvent(ActionEvent event) throws SQLException
    {
        if(!catMovieTableView.getSelectionModel().isEmpty())
        {
            Category selectedCategory = catMovieTableView.getSelectionModel().getSelectedItem();
            model.deleteCategory(selectedCategory);
            
            movieSelectionAp.setVisible(true);

        }
    }

    @FXML
    private void removeMovieEvent(ActionEvent event) throws SQLException, IOException 
    {
        if(!catMovieTableView.getSelectionModel().isEmpty())
        { 
            if(!movieTableView.getSelectionModel().isEmpty())
            { 
                Category selectedCategory = catMovieTableView.getSelectionModel().getSelectedItem();
                Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
                model.removeMovie(selectedMovie, selectedCategory);
        
            }  
        }
    }

    @FXML
    private void deleteMovieEvent(ActionEvent event) 
    {
        
    }

    @FXML
    private void selectCategoryMouseEvent(MouseEvent event) throws SQLException, IOException 
    {
        if(!catMovieTableView.getSelectionModel().isEmpty())
        {   
            
            
                movieTableView.getItems().clear();
                Category selectedCategory = catMovieTableView.getSelectionModel().getSelectedItem();
                model.getAllMovieCategory(selectedCategory);
 
                movieSelectionAp.setVisible(true);
            
        }
    }

    @FXML
    private void selectMovieMouseEvent(MouseEvent event)
    {
        if(!movieTableView.getSelectionModel().isEmpty())
        {
            Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
            
            movieTitleLbl.setText(selectedMovie.getName());          
            movieRatingLbl.setText(Double.toString(selectedMovie.getRating()) + " /10");
            movieLastviewLbl.setText(Integer.toString(selectedMovie.getLastview()));
            
            movieInfoAp.setVisible(true);
            
           
        }
    }

    @FXML
    private void playMovieBtn(ActionEvent event) 
    {
        
    }
    
}


