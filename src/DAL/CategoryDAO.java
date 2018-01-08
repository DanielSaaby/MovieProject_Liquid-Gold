/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BE.Category;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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
public class CategoryDAO
{
    private DatabaseConnector dbconnector;

    
    public CategoryDAO() throws IOException
    {
        dbconnector = new DatabaseConnector();
    }
    
    public Category createCategory(String name) throws SQLException
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

        }
    }
    
    public List<Category> getAllCategory() throws SQLException
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
        }
    }

    private Category getCategoryFromResultSetRow(ResultSet rs) throws SQLException
    {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        
        Category category = new Category(id, name);
        return category;
    }
    
}
