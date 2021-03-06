/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controllers;

import BE.Category;
import BE.ESException;
import BE.Movie;
import GUI.Model.Model;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Daniels PC
 */
public class MainWindowController implements Initializable {

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
    private TableColumn<Movie, Double> movieRatingTableColumn;

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

    @FXML
    private TextField searchTxtField;
    @FXML
    private ComboBox<Integer> minRatingComboBox;
    @FXML
    private Label allCatForMovieLbl;
    @FXML
    private ComboBox<Integer> personalRatingComboBox;

    /**
     *
     * @throws SQLException
     * @throws IOException
     */
    public MainWindowController() 
    {
        try 
        {
            this.model = new Model();
        } catch (ESException ex) 
        {
            showAlertBox(ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        catMovieTableView.setItems(model.getAllCategory());
        categoryTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        catMovieTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        movieTableView.setItems(model.getAllMovieCategory());
        movieTableColumn.setCellValueFactory(new PropertyValueFactory("name"));
        movieRatingTableColumn.setCellValueFactory(new PropertyValueFactory("rating"));
        movieTableView.setPlaceholder(new Label("This category is empty"));

        movieSelectionAp.setVisible(false);
        movieInfoAp.setVisible(false);

        minRatingComboBox.getItems().removeAll(minRatingComboBox.getItems());
        minRatingComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        minRatingComboBox.getSelectionModel().select(0);

        personalRatingComboBox.getItems().removeAll(personalRatingComboBox.getItems());
        personalRatingComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        checkOutdatedMovies();
        categorySelektion();
    }

    @FXML
    private void addCategoryEvent(ActionEvent event)  
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/NewCategory.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            NewCategoryController ncc = fxmlLoader.getController();
            ncc.setModel(model);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            showAlertBox(ex.getMessage());
        }
    }

    @FXML
    private void addMovieBtn(ActionEvent event)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/AddMovie.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            AddMovieController amc = fxmlLoader.getController();
            amc.setModel(model);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            showAlertBox(ex.getMessage());
        }
    }

    @FXML
    private void deleteCategoryEvent(ActionEvent event) 
    {
        if (!catMovieTableView.getSelectionModel().isEmpty()) {
            try {
                Category selectedCategory = catMovieTableView.getSelectionModel().getSelectedItem();
                model.deleteCategory(selectedCategory);
                
                movieSelectionAp.setVisible(false);
                movieInfoAp.setVisible(false);
            } catch (ESException ex) {
                showAlertBox(ex.getMessage());
            }

        }
    }

    @FXML
    private void removeMovieEvent(ActionEvent event) 
    {
        if (!catMovieTableView.getSelectionModel().isEmpty()) {
            if (!movieTableView.getSelectionModel().isEmpty()) {
                Category selectedCategory = catMovieTableView.getSelectionModel().getSelectedItem();
                Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
                try 
                {
                    model.removeMovie(selectedMovie, selectedCategory);
                } catch (ESException ex) 
                {
                    showAlertBox(ex.getMessage());
                    
                }
                movieInfoAp.setVisible(false);

            }
        }
    }

    @FXML
    private void deleteMovieEvent(ActionEvent event) 
    {
        if (!movieTableView.getSelectionModel().isEmpty()) {
            try {
                Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
                model.deleteMovie(selectedMovie);
                movieInfoAp.setVisible(false);
            } catch (ESException ex) 
            {
                showAlertBox(ex.getMessage());
            }

        }
    }

    private void selectCategoryClick() 
    {
        if (!catMovieTableView.getSelectionModel().isEmpty()) {

            model.clearObsList();

            for (Category selectedCategory : catMovieTableView.getSelectionModel().getSelectedItems()) {
                if (selectedCategory != null) {
                    try {
                        model.getAllMovieCategory(selectedCategory);
                    } catch (ESException ex) 
                    {
                        showAlertBox(ex.getMessage());
                    }
                }
            }
            //model.removeDublicates();
            movieSelectionAp.setVisible(true);
        }
    }

    @FXML
    private void selectMovieMouseEvent(MouseEvent event) throws ESException {
        if (!movieTableView.getSelectionModel().isEmpty()) {
            Movie selectedMovie = model.getMovieById(movieTableView.getSelectionModel().getSelectedItem().getId());

            movieTitleLbl.setText(selectedMovie.getName());
            movieRatingLbl.setText(Double.toString(selectedMovie.getRating()) + " /10");

            if (selectedMovie.getLastview() != null) {
                movieLastviewLbl.setText(selectedMovie.getLastview().toString());
            } else {
                movieLastviewLbl.setText("Not viewed yet");
            }

            allCatForMovieLbl.setText(model.getAllCatForMovie(selectedMovie).toString().replace("[", " ").replace("]", "").replace(",", ""));
            personalRatingComboBox.getSelectionModel().select(selectedMovie.getRatingP());

            movieInfoAp.setVisible(true);

        }
    }

    @FXML
    private void playMovieBtn(ActionEvent event) throws ESException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GUI/View/MediaWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            MediaWindowController mwc = fxmlLoader.getController();
            mwc.setModel(model);
            mwc.setFilePath(movieTableView.getSelectionModel().getSelectedItem().getFilelink());
            mwc.launchMovie();
            Stage stage = new Stage();
            stage.setTitle(movieTableView.getSelectionModel().getSelectedItem().getName());
            
            stage.setScene(new Scene(root1));
            stage.show();
            
            model.setLastView(movieTableView.getSelectionModel().getSelectedItem());
            Movie updatedMovie = model.getMovieById(movieTableView.getSelectionModel().getSelectedItem().getId());
            movieLastviewLbl.setText(updatedMovie.getLastview().toString());
        } catch (IOException ex) 
        {
            Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
            showAlertBox(ex.getMessage());
            
        }
    }

    @FXML
    private void searchEvent(KeyEvent event) 
    {
        FilteredList filter = new FilteredList(movieTableView.getItems(), e -> true);
        searchTxtField.textProperty().addListener((observable, oldValue, newValue) -> {

            filter.setPredicate((Predicate<? super Movie>) (Movie movie) -> {

                if (newValue.isEmpty() || newValue == null) {
                    return true;
                } else if (movie.getName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                }

                return false;
            });
            SortedList sort = new SortedList(filter);
            sort.comparatorProperty().bind(movieTableView.comparatorProperty());

            movieTableView.setItems(sort);
        });

    }

    @FXML
    private void filterRatingEvent(ActionEvent event) 
    {
        searchTxtField.clear();
        searchTxtField.deselect();

        double minRating = minRatingComboBox.getValue();

        ObservableList<Movie> minRatingList = FXCollections.observableArrayList();

        for (Movie movie : movieTableView.getItems()) {
            if (movie.getRating() >= minRating) {
                minRatingList.add(movie);
            }

        }
        model.filterRating(minRatingList);
    }

    @FXML
    private void updatePersonalRatingEvent(ActionEvent event) 
    {

        int newRating = personalRatingComboBox.getSelectionModel().getSelectedItem();
        Movie movie = movieTableView.getSelectionModel().getSelectedItem();

        try {
            model.updatePersonalRating(newRating, movie);
        } catch (ESException ex) 
        {
            showAlertBox(ex.getMessage());
        }

    }

    static void showAlertBox(String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error has ocurred");
        alert.setContentText(message);
        alert.showAndWait(); 
    }
    
    private void checkOutdatedMovies()
    {
        for (Movie movie : model.getAllMovie()) {
            if (movie.getLastview() != null) {
                if (movie.getRatingP() < 6) {

                    if (model.checkOutdatedMovies(movie)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Outdated movie");
                        alert.setContentText("the movie " + movie.getName() + " has not been viewed in 2 years");
                        alert.showAndWait();
                    }
                }
            }
        }        
    }
    
    private void categorySelektion()
    {
        catMovieTableView.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Category>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Category> c) {
                try {
                    if (!c.getList().isEmpty()) {

                        model.clearObsList();
                        searchTxtField.clear();
                        minRatingComboBox.getSelectionModel().select(0);
                        for (Category selectedCategory : c.getList()) {
                            if (selectedCategory != null) {
                                model.getAllMovieCategory(selectedCategory);
                            }
                        }
                        model.removeDublicates();
                        movieSelectionAp.setVisible(true);
                    }
                } catch (ESException ex) {
                    Logger.getLogger(MainWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }
            

}
