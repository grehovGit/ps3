package library;

import org.junit.Test;

import library.BookCopy.Condition;

import static org.junit.Assert.*;

import java.util.Arrays;

/**
 * Test suite for BookCopy ADT.
 */
public class BookCopyTest {

    /*
     * Testing strategy for constructor
     * ==================
     * 
     * Partitions:
     *    book: 
     */    

    @Test
    public void testBookCopyEqual() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(book, copy.getBook());
    }
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /*
     * Testing strategy for getBook()
     * ==================
     * Covered by constructor tests
     */  
    
    /*
     * Testing strategy for getCondition()
     * ==================
     * 
     * Partitions:
     *  # returns valid Condition object indicating current condition
     *  condition =GOOD, =BAD
     */ 
    
    // Covers condition =GOOD
    @Test
    public void testGetConditionGood() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        assertEquals(BookCopy.Condition.GOOD, copy.getCondition());
    }
    
    // Covers condition =BAD
    @Test
    public void testGetConditionBad() {
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.DAMAGED);
        assertEquals(Condition.DAMAGED, copy.getCondition());
    }
    
    /*
     * Testing strategy for setCondition()
     * ==================
     * Covered by getCondition() tests
     */  
    
    /*
     * Testing strategy for toString()
     * ==================
     * 
     * Partitions: 
     *    # returns a rep. of the book
     *    rep. content: contains book.toString(), contains "good" if current GOOD condition or 
     *    contains "damaged" if current DAMAGED condition
     */
    
    // Test cases:
    // ["aA"][["bB"]["bb"]][150] -> contains: book.toString(); good;
    // ["aA"][["bB"]["bb"]][150]; set setCondition(DAMAGED) -> contains: book.toString(); damaged;
   
    //Covers rep. content: contains book.toString(), contains "good" if current GOOD condition
    @Test
    public void testToStringContainsBookToStringGood() {
        Book book = new Book("aA", Arrays.asList("bB", "bb"), 150);
        BookCopy copy = new BookCopy(book);
        assertTrue(copy.toString().contains(copy.getBook().toString()));
        String cuted = copy.toString().replaceFirst(copy.getBook().toString(), "");
        assertTrue(cuted.contains("good"));
    }
    
    //Covers rep. content: contains book.toString(), contains "damaged" if current DAMAGED condition
    @Test
    public void testToStringContainsBookToStringDamaged() {
        Book book = new Book("aA", Arrays.asList("bB", "bb"), 150);
        BookCopy copy = new BookCopy(book);
        copy.setCondition(Condition.DAMAGED);
        assertTrue(copy.toString().contains(copy.getBook().toString()));
        String cuted = copy.toString().replaceFirst(copy.getBook().toString(), "");
        assertTrue(cuted.contains("damaged"));
    }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
