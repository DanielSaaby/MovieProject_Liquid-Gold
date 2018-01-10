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
import java.sql.SQLException;
import java.util.List;

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
    
    public void createMovie(String name, double rating, String filelink, int lastview) throws SQLException
    {
        moviedao.createMovie(name, rating, filelink, lastview);
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
}
