package com.example.gab.movieorganizer;

/**
 * Created by Gab on 4/7/2015.
 */
public class Utilities {
    public static boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
