package library;

import java.util.ArrayList;
import java.util.List;

/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    //rep.:
    private String title;
    private List<String> authors;
    private final int year;  
    
    // Rep invariant:    
    //   title is a title of the book. Must contain at least one non-space character
    //   authors Names of the authors of the book. Must have at least one name, and each name must contain 
    //   at least one non-space character.
    //   year Year when this edition was published in the conventional (Common Era) calendar. Must be nonnegative.    
    // Abstraction function:   
    //    represents a book at library identified by its title, author list, and publication year.  Alphabetic case and author 
    //    order are significant, so a book written by "Fred" is different than a book written by "FRED"    
    // Safety from rep exposure:
    //   All fields are private;
    //   title and authors.item are Strings, so are guaranteed immutable;
    //   year is final int, so is guaranteed immutable
    //   authors is a mutable List<String>, so Book() constructor and getAuthors()
    //   make defensive copies to avoid sharing the rep's List<String> object with clients.
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.authors = new ArrayList<String>(authors);
        this.year = year;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert title.length() > 0 : "incorrect title length";
        assert !title.equals(" ");
        
        assert authors.size() > 0;
        for (String item : authors) {
            assert item.length() > 0;
            assert !item.equals(" ");
        }
        assert year >= 0;
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return this.title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return new ArrayList<String>(this.authors);
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        String result =  "Title: " + title + " Authors:";
        for (String author : authors)
            result += " " + author + ",";
        result += "Year: " + Integer.toString(year);
        return result;        
    }

     @Override
     public boolean equals(Object that) {
         if (!(that instanceof Book)) return false;
         Book book = (Book) that;
         return book.hashCode() == this.hashCode();         
     }
     
     @Override
     public int hashCode() {
         int result = 17;
         result = 37 * result + title.hashCode();
         result = 37 * result + year;
         for (String author : authors)
             result = 37 * result + author.hashCode();
         return result;
     }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
