/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.ESException;
import BE.Movie;
import GUI.Model.Model;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author frederik
 */
public class AddMovieController {
    
    private Model model;
    private String fileLink;

    @FXML
    private TextField movieTitletxtField;
    @FXML
    private TextField ratingTxtField;
    @FXML
    private TextField filePathTxt;
    @FXML
    private Button saveMovie;
    
    /**
     *
     * @param model
     */
    public void setModel(Model model) 
    {
        this.model = model;
    }

    @FXML
    private void saveMovieBtn(ActionEvent event)
    {
        if(!movieTitletxtField.getText().isEmpty() && !ratingTxtField.getText().isEmpty() && !filePathTxt.getText().isEmpty())
        {
            String title = movieTitletxtField.getText();
            double rating = Double.parseDouble(ratingTxtField.getText().replace(",", "."));
            Boolean canMakeMovie = true;
        
            if(!model.getAllMovie().isEmpty())
            {   
        
                for (Movie movie : model.getAllMovie()) 
                {
                    if(movie.getName().equalsIgnoreCase(title))
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Duplication error");
                        alert.setContentText("A movie with that title already exists");

                        alert.showAndWait(); 
                        canMakeMovie = false;           
                    } 
        
                }   
            }
        
            if(canMakeMovie != false)
            {
                try 
                {
                    try
                    { 
                        model.createMovie(title, rating, fileLink);
                    } catch (ESException ex)
                    {
                        MainWindowController.showAlertBox(ex.getMessage());
                    }
                    
                    Stage stage = (Stage) saveMovie.getScene().getWindow();
                    stage.close();
                    
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/CategoryAssignment.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    CategoryAssignmentController cac = fxmlLoader.getController();
                    cac.setModel(model);
                    cac.prep(title);
                    Stage newStage = new Stage();
                    newStage.setScene(new Scene(root1));
                    newStage.show();
                } catch (IOException ex) 
                {
                    MainWindowController.showAlertBox(ex.getMessage());
                }
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing information");
            alert.setContentText("Please fill out all fields");
            alert.showAndWait(); 
        }
}
        
    

    @FXML
    private void cancelAddMovieBtn(ActionEvent event) 
    {
        Stage stage = (Stage) saveMovie.getScene().getWindow();
        stage.close();        
    }

    @FXML
    private void chooseFileBtn(ActionEvent event) 
    {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mpeg4"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile !=null)
        {   
           
            filePathTxt.setText(selectedFile.getAbsolutePath());
            String fileName = selectedFile.getName().replace(".mp4", "");
            movieTitletxtField.setText(fileName);
            fileLink = selectedFile.toURI().toString();
        
        } 
    }
        
    
    
}
