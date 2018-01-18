/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import GUI.Model.Model;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author frederik
 */
public class MediaWindowController implements Initializable 
{
    private Model model;
    private String filelink;
    private MediaPlayer mediaplayer;
    
    @FXML
    private MediaView mediaView;
    @FXML
    private Button exitBtn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider seekSlider;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {


    }    

    void setModel(Model model) 
    {
        this.model = model;
    }

    void setFilePath(String filelink) 
    {
        this.filelink = filelink;
    }

    void launchMovie() 
    {
        if(filelink !=null)
        {
            Media media = new Media(filelink);
            mediaplayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaplayer);
            
                DoubleProperty width = mediaView.fitWidthProperty();
                DoubleProperty height = mediaView.fitHeightProperty();
                
                width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
                
                volumeSlider.setValue(mediaplayer.getVolume() * 100);
                volumeSlider.valueProperty().addListener(new InvalidationListener() 
                {
                    @Override
                    public void invalidated(Observable observable) 
                    {
                        mediaplayer.setVolume(volumeSlider.getValue()/100);
                    }
                });
                
                mediaplayer.currentTimeProperty().addListener(new ChangeListener<Duration>() 
                {
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) 
                    {
                        seekSlider.setValue(newValue.toSeconds());
                        
                    }
            });
                
                seekSlider.setOnMouseClicked(new EventHandler<MouseEvent>() 
                {
                    @Override
                    public void handle(MouseEvent event) 
                    {
                        
                        mediaplayer.seek(Duration.seconds(seekSlider.getValue()));
                    }
                    
            });
        
            mediaplayer.play();
            
        }       
    }

    @FXML
    private void playEvent(ActionEvent event) 
    {
        mediaplayer.play();
        
    }

    @FXML
    private void pauseEvent(ActionEvent event) 
    {
        mediaplayer.pause();
    }

    @FXML
    private void stopEvent(ActionEvent event) 
    {
        mediaplayer.stop();
    }

    @FXML
    private void exitEvent(ActionEvent event) 
    {
        mediaplayer.dispose();
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();         
    }
    
}
