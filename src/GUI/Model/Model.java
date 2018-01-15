/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import BLL.MovieManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Daniels PC
 */
public class Model
{
    private final ObservableList<Movie> obsListMovie;
    private final ObservableList<Category> obsListCategory;
    private final ObservableList<Movie> obsListMovieCategory;
    
    MovieManager moviemanager;
    CategoryManager categorymanager;
    
    
    public Model() throws SQLException, IOException
    {
        this.moviemanager = new MovieManager();
        this.categorymanager = new CategoryManager();
        
        this.obsListMovie = FXCollections.observableArrayList();
        this.obsListCategory = FXCollections.observableArrayList();
        this.obsListMovieCategory = FXCollections.observableArrayList();
        
        obsListMovie.addAll(moviemanager.getAllMovies());
        obsListCategory.addAll(categorymanager.getAllCategory());
    }
    
    public ObservableList<Movie> getAllMovie()
    {
        return obsListMovie;
    }
    
    public ObservableList<Category> getAllCategory()
    {
        return obsListCategory;
    }
    
    public ObservableList<Movie> getAllMovieCategory()
    {
        return obsListMovieCategory;
    }
    
    public void createMovie(String name, double rating, String filelink) throws SQLException
    {
        moviemanager.createMovie(name, rating, filelink);
        obsListMovie.clear();
        obsListMovie.addAll(moviemanager.getAllMovies());
        
    }
    
    public void createCategory(String name) throws SQLException
    {
        categorymanager.createCategory(name);
        obsListCategory.clear();
        obsListCategory.addAll(categorymanager.getAllCategory());
    }

    public Movie getLatestMovie() throws SQLException 
    {
        Movie movie = moviemanager.getLatestMovie();
        return movie;
    }

    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws SQLException 
    {
        moviemanager.assignMovieCategory(category, movie, isNewMovie);
    }

    public void getAllMovieCategory(Category selectedCategory) throws SQLException, IOException 
    {
        obsListMovieCategory.addAll(categorymanager.getAllMovieCategory(selectedCategory));
        
        
            
        
    }
    
    public void deleteCategory(Category selectedCategory) throws SQLException
    {
        categorymanager.deleteCategory(selectedCategory);
        obsListCategory.clear();
        obsListCategory.addAll(categorymanager.getAllCategory());
    }

    public void removeMovie(Movie selectedMovie, Category selectedCategory) throws SQLException, IOException
    {
        moviemanager.removeMovie(selectedMovie);
        obsListMovieCategory.clear();
        obsListMovieCategory.addAll(categorymanager.getAllMovieCategory(selectedCategory));
        
    }

    public void removeDublicates() 
    {
        obsListMovieCategory.setAll(moviemanager.removeDublicates(obsListMovieCategory));
    }

    public void filterRating(ObservableList<Movie> minRatingList) 
    {
        obsListMovieCategory.clear();
        obsListMovieCategory.addAll(minRatingList);
    }


    public ObservableList<String> getAllCatForMovie(Movie selectedMovie) throws SQLException
    {
        ObservableList<String> obsListAllCatForMovie = FXCollections.observableArrayList();
        obsListAllCatForMovie.addAll(categorymanager.getAllCatForMovie(selectedMovie));
        return obsListAllCatForMovie;
    }

    public void updatePersonalRating(int newRating, Movie movie) throws SQLException 
    {
        moviemanager.updatePersonalRating(newRating, movie);
    }
    
    
}
