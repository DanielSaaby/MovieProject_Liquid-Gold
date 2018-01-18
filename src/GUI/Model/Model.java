/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Model;

import BE.Category;
import BE.ESException;
import BE.Movie;
import BLL.CategoryManager;
import BLL.MovieManager;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import static java.util.Comparator.comparingInt;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
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
    
    /**
     *
     * @throws SQLException
     * @throws IOException
     */
    public Model() throws ESException
    {
        this.moviemanager = new MovieManager();
        this.categorymanager = new CategoryManager();
        
        this.obsListMovie = FXCollections.observableArrayList();
        this.obsListCategory = FXCollections.observableArrayList();
        this.obsListMovieCategory = FXCollections.observableArrayList();
        
        obsListMovie.addAll(moviemanager.getAllMovies());
        obsListCategory.addAll(categorymanager.getAllCategory());
    }
    
    /**
     * returns an ObservableList with all movies
     * @return
     */
    public ObservableList<Movie> getAllMovie()
    {
        return obsListMovie;
    }
    
    /**
     * returns a list with all categories
     * @return
     */
    public ObservableList<Category> getAllCategory()
    {
        return obsListCategory;
    }
    
    /**
     * returns a list of all the movies specified by a category
     * @return
     */
    public ObservableList<Movie> getAllMovieCategory()
    {
        return obsListMovieCategory;
    }
    
    /**
     * creates a movie with the given parameters
     * @param name
     * @param rating
     * @param filelink
     * @throws SQLException
     */
    public void createMovie(String name, double rating, String filelink) throws ESException
    {
        moviemanager.createMovie(name, rating, filelink);
        obsListMovie.clear();
        obsListMovie.addAll(moviemanager.getAllMovies());
        
    }
    
    /**
     * creates a category with the give parameters
     * @param name
     * @throws SQLException
     */
    public void createCategory(String name) throws ESException
    {
        categorymanager.createCategory(name);
        obsListCategory.clear();
        obsListCategory.addAll(categorymanager.getAllCategory());
    }

    /**
     * returns the latest created movie
     * @return
     * @throws SQLException
     */
    public Movie getLatestMovie() throws ESException 
    {
        Movie movie = moviemanager.getLatestMovie();
        return movie;
    }

    /**
     * creates a relation between a movie and category object
     * @param category
     * @param movie
     * @param isNewMovie
     * @throws SQLException
     */
    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws ESException 
    {
        moviemanager.assignMovieCategory(category, movie, isNewMovie);
    }

    /**
     * adds all categories to an ObservableList
     * @param selectedCategory
     * @throws SQLException
     * @throws IOException
     */
    public void getAllMovieCategory(Category selectedCategory) throws ESException 
    { 
        if(!categorymanager.getAllMovieCategory(selectedCategory).isEmpty())
        {
            obsListMovieCategory.addAll(categorymanager.getAllMovieCategory(selectedCategory));
        }
    }
    
    /**
     * deletes a specified category and updates the ObservableList
     * @param selectedCategory
     * @throws SQLException
     */
    public void deleteCategory(Category selectedCategory) throws ESException
    {
        categorymanager.deleteCategory(selectedCategory);
        obsListCategory.clear();
        obsListCategory.addAll(categorymanager.getAllCategory());
    }

    /**
     *  removes the relation between the movie object and category object
     * @param selectedMovie
     * @param selectedCategory
     * @throws SQLException
     * @throws IOException
     */
    public void removeMovie(Movie selectedMovie, Category selectedCategory) throws ESException 
    {
        moviemanager.removeMovie(selectedMovie, selectedCategory);
        obsListMovieCategory.remove(selectedMovie);
        
    }

    /**
     * removes all dublicates in list
     */
    public void removeDublicates() 
    {
        obsListMovieCategory.setAll(moviemanager.removeDublicates(obsListMovieCategory));
    }

    /**
     * filters rating
     * @param minRatingList
     */
    public void filterRating(ObservableList<Movie> minRatingList) 
    {
        obsListMovieCategory.clear();
        obsListMovieCategory.addAll(minRatingList);
    }

    /**
     *
     * @param selectedMovie
     * @return
     * @throws SQLException
     */
    public ObservableList<String> getAllCatForMovie(Movie selectedMovie) throws ESException
    {
        ObservableList<String> obsListAllCatForMovie = FXCollections.observableArrayList();
        obsListAllCatForMovie.addAll(categorymanager.getAllCatForMovie(selectedMovie));
        return obsListAllCatForMovie;
    }

    /**
     * updates personal rating for the movie object
     * @param newRating
     * @param movie
     * @throws SQLException
     */
    public void updatePersonalRating(int newRating, Movie movie) throws ESException
    {
        moviemanager.updatePersonalRating(newRating, movie);
    }

    /**
     * deletes the specified movie
     * @param selectedMovie
     * @throws SQLException
     */
    public void deleteMovie(Movie selectedMovie) throws ESException 
    {
        moviemanager.deleteMovie(selectedMovie);
        obsListMovieCategory.remove(selectedMovie);
     }

    /**
     * set last view for movie 
     * @param selectedMovie
     * @throws SQLException
     * @throws ParseException
     */
    public void setLastView(Movie selectedMovie) throws ESException 
    {
        moviemanager.setLastView(selectedMovie);
    }

    /**
     * returns movie by the given parameter
     * @param id
     * @return
     * @throws SQLException
     */
    public Movie getMovieById(int id) throws ESException 
    {
        return moviemanager.getMovieById(id);
    }

    /**
     * returns true if movie hasn't been viewed in over two years
     * @param movie
     * @return
     */
    public Boolean checkOutdatedMovies(Movie movie) 
    {
         return moviemanager.checkOutdatedMovies(movie);
    }

    /**
     * clears the obsListMovieCategory ObservableList
     */
    public void clearObsList() 
    {
        obsListMovieCategory.clear();
    }
    
    
}
