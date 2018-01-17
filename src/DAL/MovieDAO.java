/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import BE.Movie;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Daniels PC
 */
public class MovieDAO
{
    private Movie movie;
    private DatabaseConnector dbconnector;

    
    public MovieDAO() throws IOException
    {
        dbconnector = new DatabaseConnector();
        
    }
    
    public Movie createMovie(String name, double rating, String filelink) throws SQLException
    {
        
    
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "INSERT INTO Movie VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, name);
            statement.setInt(2, 0);
            statement.setDouble(3, rating);
            statement.setString(4, filelink);
            statement.setDate(5, null);
            
            
            if(statement.executeUpdate() == 1)
            {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                Movie m = new Movie(id, name, rating, 0, filelink, null);
                return m;
            }
            throw new RuntimeException("Can't create movie");
        }
        

    }
    
    public List<Movie> getAllMovies() throws SQLException
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM Movie";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            List<Movie> allMovies = new ArrayList<>();
            
            while(rs.next())
            {
                Movie movie = getMovieFromResultSetRow(rs);
                allMovies.add(movie);
            }
            return allMovies;
        }
    }

    private Movie getMovieFromResultSetRow(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int ratingP = rs.getInt("ratingPersonal");
        double rating = rs.getDouble("ratingIMDB");
        String filelink = rs.getString("filelink");
        Date lastview = rs.getDate("lastview");
        
        Movie movie = new Movie(id, name, rating , ratingP, filelink, lastview);
        return movie;
    }

    public Movie getLatestMovie() throws SQLException 
    {        
        try (Connection con = dbconnector.getConnection())
        {
            
            
            String sql ="SELECT * From Movie Where id=(SELECT max(id) From Movie);";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next())
            {
                 movie = getMovieFromResultSetRow(rs);
            }
            
            
        }
        return movie;

        
    }
    
    
    public Movie getMovieById(int id) throws SQLException
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM Movie WHERE id = ?;";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, id);


            
                ResultSet rs = statement.executeQuery();
                rs.next();
                
                Movie m = getMovieFromResultSetRow(rs);
                return m;
            

        }
    }
    

    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws SQLException 
    {
        Boolean NewMovie = isNewMovie;
        
        try (Connection con = dbconnector.getConnection())
        {
            
            if(NewMovie !=true)
            {
                String sql ="DELETE FROM CatMovie WHERE movieid =(?)";

                PreparedStatement statement = con.prepareStatement(sql);
                //Virker måske ik med movie object nullPointer
                statement.setInt(1, movie.getId());
                statement.executeUpdate();
                
            }
           
            String sql ="INSERT INTO CatMovie VALUES (?, ?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
                        
            statement.setInt(1, category.getId());
            statement.setInt(2, movie.getId());

            
            statement.executeUpdate();
            
        
        }        
        
        
    }

    public void removeMovie(Movie selectedMovie) throws SQLException
    {
        try (Connection con = dbconnector.getConnection()) 
        {
            String sql = "DELETE FROM CatMovie WHERE movieid = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, selectedMovie.getId());
            
            statement.executeUpdate();
        
        
        }
         
            
        
    }

    public void updatePersonalRating(int newRating, Movie movie) throws SQLException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "UPDATE Movie SET ratingPersonal = (?) WHERE id = (?);";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, newRating);
            statement.setInt(2, movie.getId());
            
            statement.executeUpdate();
        }
    }

    public void deleteMovie(Movie selectedMovie) throws SQLException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "DELETE FROM CatMovie WHERE movieid = (?); DELETE FROM Movie WHERE id = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, selectedMovie.getId());
            statement.setInt(2, selectedMovie.getId());
            
            statement.executeUpdate();            
        }
    }

    public void setLastView(Movie selectedMovie) throws SQLException, ParseException 
    {
        Date date = new Date();
        
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        
        
               
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "UPDATE Movie SET lastview = (?) WHERE id = (?);";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setDate(1, sqlDate);
            statement.setInt(2, selectedMovie.getId());
            
            statement.executeUpdate();
        }
    }
    
    
    
}
