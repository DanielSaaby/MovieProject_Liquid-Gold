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
public class Movie
{
    private final int id;
    private String name;
    private double rating;
    private String filelink;
    private int lastview;

    public Movie(int id, String name, double rating, String filelink, int lastview)
    {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.filelink = filelink;
        this.lastview = lastview;
    }

    public int getId()
    {
        return id;
    }

    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getRating()
    {
        return rating;
    }

    public void setRating(double rating)
    {
        this.rating = rating;
    }

    public String getFilelink()
    {
        return filelink;
    }

    public void setFilelink(String filelink)
    {
        this.filelink = filelink;
    }

    public int getLastview()
    {
        return lastview;
    }

    public void setLastview(int lastview)
    {
        this.lastview = lastview;
    }
    
    
}
