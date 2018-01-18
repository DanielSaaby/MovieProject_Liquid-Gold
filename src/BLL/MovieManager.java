/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import BE.Category;
import BE.ESException;
import BE.Movie;
import DAL.MovieDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import static java.util.Comparator.comparingInt;
import java.util.Date;
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

    /**
     *
     * @throws IOException
     */
    public MovieManager() throws ESException
    {
        moviedao = new MovieDAO();
        
    }
    
    /**
     *
     * @param name
     * @param rating
     * @param filelink
     * @throws SQLException
     */
    public void createMovie(String name, double rating, String filelink) throws ESException
    {
        moviedao.createMovie(name, rating, filelink);
    }
    
    /**
     * returns a list of all the movies
     * @return
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws ESException 
    {
        return moviedao.getAllMovies();
    }

    /**
     * returns the latest created movie
     * @return
     * @throws SQLException
     */
    public Movie getLatestMovie() throws ESException
    {
        Movie movie = moviedao.getLatestMovie();
        return movie;
    }    

    /**
     * creates a relation between the specified category object and movie object
     * @param category
     * @param movie
     * @param isNewMovie
     * @throws SQLException
     */
    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws ESException 
    {
        moviedao.assignMovieCategory(category, movie, isNewMovie);
       
    }

    /**
     * removes the relation between the movie and category
     * @param selectedMovie
     * @throws SQLException
     */
    public void removeMovie(Movie selectedMovie, Category selectedCategory) throws ESException
    {
        moviedao.removeMovie(selectedMovie, selectedCategory);
    }

    /**
     * returns a list of the movies without any duplicates 
     * @param obsListMovieCategory
     * @return
     */
    public List<Movie> removeDublicates(ObservableList<Movie> obsListMovieCategory) 
    {
        List<Movie> unique = obsListMovieCategory.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Movie::getId))), ArrayList::new));
        return unique;
    }

    /**
     * updates the personal rating of the movie 
     * @param newRating
     * @param movie
     * @throws SQLException
     */
    public void updatePersonalRating(int newRating, Movie movie) throws ESException
    {
        moviedao.updatePersonalRating(newRating, movie);
    }

    /**
     * deletes the movie and all its relations 
     * @param selectedMovie
     * @throws SQLException
     */
    public void deleteMovie(Movie selectedMovie) throws ESException 
    {
        moviedao.deleteMovie(selectedMovie);
    }

    /**
     * set the last view of the movie object
     * @param selectedMovie
     * @throws SQLException
     * @throws ParseException
     */
    public void setLastView(Movie selectedMovie) throws ESException 
    {
        moviedao.setLastView(selectedMovie);
    }

    /**
     * returns a movie specified by id
     * @param id
     * @return
     * @throws SQLException
     */
    public Movie getMovieById(int id) throws ESException
    {
        return moviedao.getMovieById(id);
    }

    /**
     * returns a true if the the movie hasnt been viewed in two years 
     * @param movie
     * @return
     */
    public Boolean checkOutdatedMovies(Movie movie) 
    {
        long futureMili = Long.parseLong("63113904000");
        
        Date futureDate = new Date(movie.getLastview().getTime() + futureMili);
        Date movieDate = new Date();
        Date currentDate = new Date(movieDate.getTime());
        
        
        System.out.println(futureDate);
        System.out.println(currentDate);
                
        return currentDate.after(futureDate);
        
    }




        
        
    }
    
    
    
    
    

