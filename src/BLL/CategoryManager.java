/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;


import BE.Category;
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
    
    
    public List<Category> getAllCategory() throws SQLException
    {
        return categorydao.getAllCategory();
    }

    public void deleteCategory(Category selectedCategory)
    {
        categorydao.deleteCategory(selectedCategory);
    }
    

}
