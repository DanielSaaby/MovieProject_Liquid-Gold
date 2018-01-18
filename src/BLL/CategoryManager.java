/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;


import BE.Category;
import BE.ESException;
import BE.Movie;
import DAL.CategoryDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Daniels PC
 */
public class CategoryManager
{
    private CategoryDAO categorydao;

    /**
     *
     * @throws IOException
     */
    public CategoryManager() throws ESException
    {
        categorydao = new CategoryDAO();
        
    }
    
    /**
     * creates a category with the given paramiter
     * @param name
     * @throws SQLException
     */
    public void createCategory(String name) throws ESException
    {
        categorydao.createCategory(name);
    }
    
    /**
     * returns a list of all the movies with relations to the category object
     * @param selectedCategory
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public List<Movie> getAllMovieCategory(Category selectedCategory) throws ESException
    {
        return categorydao.getAllMovieCategory(selectedCategory);
    }

    /**
     * returns a list of all categories
     * @return
     * @throws SQLException
     */
    public List<Category> getAllCategory() throws ESException 
    {
        return categorydao.getAllCategory();
    }

    /**
     * deletes the specified category
     * @param selectedCategory
     */
    public void deleteCategory(Category selectedCategory) throws ESException
    {
        categorydao.deleteCategory(selectedCategory);
    }

    /**
     *  returns a list of all the category names tied to the movie object
     * @param selectedMovie
     * @return
     * @throws SQLException
     */
    public List<String> getAllCatForMovie(Movie selectedMovie) throws ESException 
    {
        return categorydao.getAllCatForMovie(selectedMovie);
    }
    

}
