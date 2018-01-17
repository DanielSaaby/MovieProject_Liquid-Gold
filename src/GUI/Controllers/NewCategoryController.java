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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniels PC
 */
public class NewCategoryController implements Initializable
{

    private Model model;

    @FXML
    private TextField addNewCategoryTextField;
    @FXML
    private Button saveNewCategory;
    @FXML
    private Button cancelNewCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }



    @FXML
    private void saveNewCategoryEvent(ActionEvent event) throws SQLException
    {

        Boolean canMakeCategory = true;
        
        if(!addNewCategoryTextField.getText().isEmpty())
        {   
            String catName = addNewCategoryTextField.getText();
            
            for (Category category : model.getAllCategory()) 
            {
                if(category.getName().equalsIgnoreCase(catName))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Duplication error");
                    alert.setContentText("A category with that name already exists");

                    alert.showAndWait(); 
                    canMakeCategory = false;           
                } 
            }
            if(canMakeCategory != false)
            {
                String categoryName = addNewCategoryTextField.getText();
       
                model.createCategory(categoryName);
    
                Stage stage = (Stage) saveNewCategory.getScene().getWindow();
                stage.close();               
            }
        }       
        else 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing Category Name");
            alert.setContentText("Please enter a category name");

            alert.showAndWait();
        }
    }

    @FXML
    private void cancelNewCategoryEvent(ActionEvent event)
    {
        Stage stage = (Stage) cancelNewCategory.getScene().getWindow();
        stage.close();
    }
    
    public void setModel(Model model) 
    {
        this.model = model;
    }

}
