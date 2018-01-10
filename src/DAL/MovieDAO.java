/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Movie;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    public Movie createMovie(String name, double rating, String filelink, int lastview) throws SQLException
    {
        
    
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "INSERT INTO Movie VALUES (?, ?, ?, ?)";
            
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, name);
            statement.setDouble(2, rating);
            statement.setString(3, filelink);
            statement.setInt(4, lastview);
            
            if(statement.executeUpdate() == 1)
            {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                Movie m = new Movie(id, name, rating, filelink, lastview);
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
        double rating = rs.getDouble("rating");
        String filelink = rs.getString("filelink");
        int lastview = rs.getInt("lastview");
        
        Movie movie = new Movie(id, name, rating, filelink, lastview);
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
    
    
    
}
