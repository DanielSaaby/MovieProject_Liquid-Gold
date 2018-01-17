/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author frederik
 */
public class ESException extends Exception
{
    public ESException(String message)
    {
        super(message);
    }

    public ESException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ESException(Throwable cause)
    {
        super(cause);
    }

    
}
