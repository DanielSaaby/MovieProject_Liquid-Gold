/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import BE.ESException;
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
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniels PC
 */
public class MovieDAO
{
    private Movie movie;
    private DatabaseConnector dbconnector;

    /**
     *
     * @throws IOException
     */
    public MovieDAO() throws ESException 
    {
        try {
            dbconnector = new DatabaseConnector();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not connect to database", ex);
        }
        
    }
    
    /**
     * Creates a movie in the datebase with the given paramiters
     * @param name
     * @param rating
     * @param filelink
     * @return
     * @throws SQLException
     */
    public Movie createMovie(String name, double rating, String filelink) throws ESException 
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
        } catch (SQLException ex) 
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not create movie due to connection error", ex);
        }
        

    }
    
    /**
     *  returns a list of all the movies in the database
     * @return
     * @throws SQLException
     */
    public List<Movie> getAllMovies() throws ESException 
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
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not retrieve all movies due to connection error", ex);
        }
    }

    private Movie getMovieFromResultSetRow(ResultSet rs) throws ESException 
    {
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int ratingP = rs.getInt("ratingPersonal");
            double rating = rs.getDouble("ratingIMDB");
            String filelink = rs.getString("filelink");
            Date lastview = rs.getDate("lastview");
            
            Movie movie = new Movie(id, name, rating , ratingP, filelink, lastview);
            return movie;
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not retrieve movie from resultset due to connection error", ex);
        }
    }

    /**
     * returns the latest created movie object in the database
     * @return
     * @throws SQLException
     */
    public Movie getLatestMovie() throws ESException 
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
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not retrieve the latest movie due to connection error", ex);
        }
        return movie;

        
    }
    
    /**
     * returns a movie from the database specified by the movie id
     * @param id
     * @return
     * @throws SQLException
     */
    public Movie getMovieById(int id) throws ESException
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
            

        } catch (SQLException ex) 
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not retrieve movie due to connection error", ex);
        }
    }
    
    /**
     * creates a relation between the movie object and category object
     * @param category
     * @param movie
     * @param isNewMovie
     * @throws SQLException
     */
    public void assignMovieCategory(Category category, Movie movie, Boolean isNewMovie) throws ESException
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
            
        
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not assign the given category to the movie due to connection error", ex);
        }        
        
        
    }

    /**
     * removes the specified movie object from the database
     * @param selectedMovie
     * @throws SQLException
     */
    public void removeMovie(Movie selectedMovie, Category selectedCategory) throws ESException
    {
        try (Connection con = dbconnector.getConnection()) 
        {
            String sql = "DELETE FROM CatMovie WHERE movieid = (?) AND categoryid = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            
            statement.setInt(1, selectedMovie.getId());
            statement.setInt(2, selectedCategory.getId());
            
            statement.executeUpdate();
        
        
        } catch (SQLException ex) 
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not remove from database due to connection error", ex);
        }
         
            
        
    }

    /**
     * updates the personal rating of the movie object in the database
     * @param newRating
     * @param movie
     * @throws SQLException
     */
    public void updatePersonalRating(int newRating, Movie movie) throws ESException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "UPDATE Movie SET ratingPersonal = (?) WHERE id = (?);";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, newRating);
            statement.setInt(2, movie.getId());
            
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not update personal rating due to connection error", ex);
        }
    }

    /**
     * deletes the movie object and all of its relations from the database
     * @param selectedMovie
     * @throws SQLException
     */
    public void deleteMovie(Movie selectedMovie) throws ESException
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "DELETE FROM CatMovie WHERE movieid = (?); DELETE FROM Movie WHERE id = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, selectedMovie.getId());
            statement.setInt(2, selectedMovie.getId());
            
            statement.executeUpdate();            
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not delete movie due to connection error", ex);
        }
    }

    /**
     * set the lastviewed date for the movie object
     * @param selectedMovie
     * @throws SQLException
     * @throws ParseException
     */
    public void setLastView(Movie selectedMovie) throws ESException 
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
        } catch (SQLException ex) {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not update the date for the movies lastview due to connection", ex);
        }
    }
    
    
    
}
