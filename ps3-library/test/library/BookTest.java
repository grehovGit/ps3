package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test suite for Book ADT.
 */
public class BookTest {

    /*
     * Testing strategy
     * ==================
     * 
     * Partitions:
     *    title.size: =1,  >1,
     *    title contents: special symbols, case sensitive
     *    
     *    authors.size: =1, >1
     *    authors.item.size: =1, >1
     *    authors.item.content: special symbols, case sensitive, duplicate name
     *    authors.item hold order
     *    authors exposure
     *    
     *    year: =0, >0, MAX
     */
    
    // Test cases:
    // ["a"][["a"]][0] -> ["a"][["a"]][0]
    // ["aA"][["aA"]["aa"]][150] -> ["aA"][["aA"]["aa"]][150]
    // [" % / \ " "][[" % / \ " "][" % / \ " "]][Integer.Max] ->  [" % / \ " "][[" % / \ " "][" % / \ " "]][Integer.Max] 
    
    
    // Covers title.size: =1; authors.size: =1; authors.item.size: =1
    // year: =0
    @Test
    public void testBookTSizeOne_ASizeOne_AContentSizeOne_YearZero() {
        String a = new String("a");
        Book book = new Book(a, Arrays.asList("a"), 0);
        assertEquals("a", book.getTitle());
        assertEquals("a", book.getAuthors().get(0));
        assertEquals(0, book.getYear());
    }
    
    // Covers authors exposure;
    @Test
    public void testBookTExp_AExp_AContentExp() {
        List<String> aList = Arrays.asList("a");
        Book book = new Book("a", aList, 0);
        
        assertFalse(aList == book.getAuthors());
    }
    
    // Covers title.size: >1; title contents: case sensitive
    // authors.size: >1; authors.item.size: >1; authors.item.content: case sensitive
    // authors.item hold order
    // year: >0
    @Test
    public void testBookTSizeTwoCaseSense_ASizeTwo_AContentSizeTwoCaseSense_AOrder_Year150() {
        Book book = new Book("aA", Arrays.asList("aA", "aa"), 150);
        assertEquals("aA", book.getTitle());
        assertEquals(2, book.getAuthors().size());
        assertEquals("aA", book.getAuthors().get(0));
        assertEquals("aa", book.getAuthors().get(1));
        assertEquals(150, book.getYear());
    }
    
    // Covers title contents: special symbols
    // authors.item.content: special symbols, duplicate name
    // year: MAX
    @Test
    public void testBookTSpecial_ASpecial_YearMax() {
        Book book = new Book("% / \\ \" ", Arrays.asList("% / \\ \" ", "% / \\ \" "), Integer.MAX_VALUE);
        assertEquals("% / \\ \" ", book.getTitle());
        assertEquals(2, book.getAuthors().size());
        assertEquals("% / \\ \" ", book.getAuthors().get(0));
        assertEquals("% / \\ \" ", book.getAuthors().get(1));
        assertEquals(Integer.MAX_VALUE, book.getYear());
    }
    
    /*
     * Testing strategy for getTitle()
     * ==================
     * Covered by constructor tests
     */
    
    /*
     * Testing strategy for getAuthors() 
     * ==================
     * Covered by constructor tests
     */
    
    /*
     * Testing strategy for getYear()
     * ==================
     * Covered by constructor tests
     */  
    
    /*
     * Testing strategy for toString()
     * ==================
     * 
     */  
    
    /*
     * Testing strategy for toString()
     * ==================
     * 
     * Partitions: 
     *    # returns a rep. of the book
     *    rep. content: contains title, authors, year
     */
    
    // Test cases:
    // ["aA"][["bB"]["bb"]][150] -> contains:["aA"]; [["bB"]; ["bb"]]; [150];
   
    //Covers rep. content: contains title, authors, year
    @Test
    public void testToStringContainsTitleAuthorsYear() {
        Book book = new Book("aA", Arrays.asList("bB", "bb"), 150);
        assertTrue(book.toString().contains("aA"));
        assertTrue(book.toString().contains("bB"));
        assertTrue(book.toString().contains("bb"));
        assertTrue(book.toString().contains(Integer.toString(150)));
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
