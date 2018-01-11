/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;


import BE.Category;
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

    public CategoryManager() throws IOException
    {
        categorydao = new CategoryDAO();
        
    }
    
    public void createCategory(String name) throws SQLException
    {
        categorydao.createCategory(name);
    }
    
    
    public List<Movie> getAllMovieCategory(Category selectedCategory) throws SQLException, IOException
    {
        return categorydao.getAllMovieCategory(selectedCategory);
    }

    public List<Category> getAllCategory() throws SQLException 
    {
        return categorydao.getAllCategory();
    }
    
}
