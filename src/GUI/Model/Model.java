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
    
    MovieManager moviemanager;
    CategoryManager categorymanager;
    
    
    public Model() throws SQLException, IOException
    {
        this.moviemanager = new MovieManager();
        this.categorymanager = new CategoryManager();
        
        this.obsListMovie = FXCollections.observableArrayList();
        this.obsListCategory = FXCollections.observableArrayList();
        
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
    
    public void createMovie(String name, double rating, String filelink, int lastview) throws SQLException
    {
        moviemanager.createMovie(name, rating, filelink, lastview);
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
    
    
    
}
