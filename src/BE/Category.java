/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Daniels PC
 */
public class Category
{
    private final int id;
    private String name;

    /**
     * 
     * @param id
     * @param name
     */
    public Category(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     *returns the id of the Category object
     * @return
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns the name of the Category object
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * sets the name of the Category Object
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    
    
}
