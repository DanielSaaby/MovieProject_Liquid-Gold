/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Kent Juul
 */
public class AddMovieControllerController implements Initializable
{

    @FXML
    private TextField movieTitletxtField;
    @FXML
    private TextField ratingTxtField;
    @FXML
    private TextField genreTxtField;
    @FXML
    private TextField filePathTxt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    @FXML
    private void saveMovieBtn(ActionEvent event)
    {
    }

    @FXML
    private void cancelAddMovieBtn(ActionEvent event)
    {
    }

    @FXML
    private void chooseFileBtn(ActionEvent event)
    {
    }
    
}
