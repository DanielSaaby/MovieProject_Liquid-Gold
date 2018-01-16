/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

import java.util.Date;

/**
 *
 * @author Daniels PC
 */
public class Movie
{
    private final int id;
    private String name;
    private double rating;
    private int ratingP;
    private String filelink;
    private Date lastview;

    public Movie(int id, String name, double rating, int ratingP, String filelink, Date lastview)
    {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ratingP = ratingP;
        this.filelink = filelink;
        this.lastview = lastview;
    }

    public int getRatingP() {
        return ratingP;
    }

    public void setRatingP(int ratingP) {
        this.ratingP = ratingP;
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

    public Date getLastview()
    {
        return lastview;
    }

    public void setLastview(Date lastview)
    {
        this.lastview = lastview;
    }
    
    
}
