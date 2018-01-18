/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import BE.ESException;
import BE.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniels PC
 */
public class CategoryDAO
{
    private DatabaseConnector dbconnector;

    /**
     *
     * @throws IOException
     */
    public CategoryDAO() throws ESException 
    {
        try 
        {
            dbconnector = new DatabaseConnector();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not get connection to database", ex);
        }
    }
    
    /**
     * Creates a category in the database with the given string parameter
     * @param name
     * @return
     * @throws SQLException
     */
    public Category createCategory(String name) throws ESException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "INSERT INTO Category VALUES (?)";
            
            PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, name);
            
            if(statement.executeUpdate() == 1)
            {
                ResultSet rs = statement.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                Category c = new Category(id, name);
                return c;
            }
            throw new RuntimeException("Can't create category");

        } catch (SQLException ex) 
        {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not create category due to connection error", ex);
            
        }
    }
   

    /**
     * returns a list of all the categories from the database
     * @return
     * @throws BE.ESException
     * 
     */
    public List<Category> getAllCategory() throws ESException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM Category";
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            List<Category> allCategory = new ArrayList<>();
            
            while(rs.next())
            {
                Category category = getCategoryFromResultSetRow(rs);
                allCategory.add(category);
            }
            return allCategory;
        } catch (SQLException ex) 
        {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not retrieve the categories due to connection error", ex);
        }
    }
    

    private Category getCategoryFromResultSetRow(ResultSet rs) throws ESException 
    {
        try 
        {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            
            Category category = new Category(id, name);
            return category;
        } catch (SQLException ex) 
        {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not retrieve category from resulset in database du to connection error", ex);
            
            
        }
    }

    /**
     * returns a list of movies that is in the given selected category
     * @param selectedCategory
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public List<Movie> getAllMovieCategory(Category selectedCategory) throws ESException 
    {
     
        
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM CatMovie WHERE categoryid = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            
            statement.setInt(1, selectedCategory.getId());
            
            ResultSet rs = statement.executeQuery(); 
            MovieDAO moviedao = new MovieDAO();
            
            List<Movie> allMovies = new ArrayList<>();
            
            while(rs.next())
            {
                int movieid = rs.getInt("movieid");
                Movie movie = moviedao.getMovieById(movieid);
                allMovies.add(movie);
                
            }
            return allMovies;  
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("Could not retrieve movies due to connection error", ex);
        }


    }
    
    /**
     * deletes the category object from the database
     * @param selectedCategory
     */
    public void deleteCategory(Category selectedCategory) throws ESException
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "DELETE FROM CatMovie WHERE categoryid = ?; DELETE FROM Category WHERE id = ?;";

            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, selectedCategory.getId());
            statement.setInt(2, selectedCategory.getId());
            
            statement.executeUpdate();        
        }
        catch (SQLException ex)
        {
            Logger.getLogger(MovieDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not delete category due to connection error", ex);
            
        }
    
}

    /**
     * returns a list of all the category names the movie object consists of
     * @param selectedMovie
     * @return
     * @throws SQLException
     */
    public List<String> getAllCatForMovie(Movie selectedMovie) throws ESException
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM CatMovie WHERE movieid = (?)";
            
            PreparedStatement statement = con.prepareStatement(sql);
            
            statement.setInt(1, selectedMovie.getId());
            
            
            ResultSet rs = statement.executeQuery();
            
            List<String> allCategory = new ArrayList<>();
            
            while(rs.next())
            {
                int categoryid = rs.getInt("categoryid");
                Category category = getCategoryById(categoryid);
                allCategory.add(category.getName()+"\n");
            }
            return allCategory;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not retrive the categories for this movie due to connection error", ex);
        }        
    }
    
    /**
     * returns a category specified by the category id
     * @param id
     * @return
     * @throws SQLException
     */
    public Category getCategoryById(int id) throws ESException 
    {
        try (Connection con = dbconnector.getConnection())
        {
            String sql = "SELECT * FROM Category WHERE id = ?;";

            PreparedStatement statement = con.prepareStatement(sql);

            statement.setInt(1, id);


            
                ResultSet rs = statement.executeQuery();
                rs.next();

                Category m = getCategoryFromResultSetRow(rs);
                return m;    
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ESException("could not retrieve the category from the database due to connection error", ex);
        }
    }
}
