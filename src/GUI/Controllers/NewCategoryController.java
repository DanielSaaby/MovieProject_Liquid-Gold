/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

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

    private void okAddNewCategory(ActionEvent event)
    {
        String categoryName = addNewCategoryTextField.getText();

        try
        {
            model.createCategory(categoryName);
        } 
        catch(SQLException ex) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();
        }
        
        Stage stage = (Stage) saveNewCategory.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveNewCategoryEvent(ActionEvent event)
    {
    }

    @FXML
    private void cancelNewCategoryEvent(ActionEvent event)
    {
    }
    
        public void setModel(Model model) 
    {
        this.model = model;
    }

}
