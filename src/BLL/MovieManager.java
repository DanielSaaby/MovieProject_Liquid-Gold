/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Category;
import BE.Movie;
import DAL.MovieDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Comparator.comparingInt;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import javafx.collections.ObservableList;

/**
 *
 * @author Daniels PC
 */
public class MovieManager
{


    private MovieDAO moviedao;

    public MovieManager() throws IOException
    {
        moviedao = new MovieDAO();
        
    }
    
    public void createMovie(String name, double rating, String filelink) throws SQLException
    {
        moviedao.createMovie(name, rating, filelink);
    }
    
    
    public List<Movie> getAllMovies() throws SQLException
    {
        return moviedao.getAllMovies();
    }
    public Movie getLatestMovie() throws SQLException 
    {
        Movie movie = moviedao.getLatestMovie();
        return movie;
    }    

    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws SQLException 
    {
        moviedao.assignMovieCategory(category, movie, isNewMovie);
       
    }

    public void removeMovie(Movie selectedMovie) throws SQLException
    {
        moviedao.removeMovie(selectedMovie);
    }

    public List<Movie> removeDublicates(ObservableList<Movie> obsListMovieCategory) 
    {
        List<Movie> unique = obsListMovieCategory.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Movie::getId))), ArrayList::new));
        return unique;
    }

    public void updatePersonalRating(int newRating, Movie movie) throws SQLException 
    {
        moviedao.updatePersonalRating(newRating, movie);
    }




        
        
    }
    
    
    
    
    

