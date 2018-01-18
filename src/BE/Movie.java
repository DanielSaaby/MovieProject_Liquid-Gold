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

    /**
     *
     * @param id
     * @param name
     * @param rating
     * @param ratingP
     * @param filelink
     * @param lastview
     */
    public Movie(int id, String name, double rating, int ratingP, String filelink, Date lastview)
    {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.ratingP = ratingP;
        this.filelink = filelink;
        this.lastview = lastview;
    }

    /**
     * returns the personal rating of the movie object
     * @return
     */
    public int getRatingP() {
        return ratingP;
    }

    /**
     * set the personal rating of the movie object 
     * @param ratingP
     */
    public void setRatingP(int ratingP) {
        this.ratingP = ratingP;
    }

    /**
     * returns the id for the movie object
     * @return
     */
    public int getId()
    {
        return id;
    }

    /**
     * returns the name of the movie object
     * @return
     */
    public String getName()
    {
        return name;
    }

    /**
     * set the name of the movie object
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * returns the IMDB rating for the movie object
     * @return
     */
    public double getRating()
    {
        return rating;
    }

    /**
     * set the IMDB rating for the movie object
     * @param rating
     */
    public void setRating(double rating)
    {
        this.rating = rating;
    }

    /**
     * return the filelink of the movie object
     * @return
     */
    public String getFilelink()
    {
        return filelink;
    }

    /**
     * set the filelink of the movie object
     * @param filelink
     */
    public void setFilelink(String filelink)
    {
        this.filelink = filelink;
    }

    /**
     * returns the lastview of the movie object
     * @return
     */
    public Date getLastview()
    {
        return lastview;
    }

    /**
     * set the lastview of the movie object
     * @param lastview
     */
    public void setLastview(Date lastview)
    {
        this.lastview = lastview;
    }
    
    
}
