package library;

/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {
    
    private final Book book;
    private Condition condition;

    // Rep invariant:    
    //   book is a representation of an edition of a book
    //   condition represents a current condition of a book
    // Abstraction function:   
    //    represents a particular copy of a book at library with current particular condition    
    // Safety from rep exposure:
    //   All fields are private;
    //   book is final and immutable Book type;
    //   condition is a immutable enum Condition type;
    
    public static enum Condition {
        GOOD, DAMAGED
    };
    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        assert book != null : "invalid argument";
        this.book = book;
        this.condition = Condition.GOOD;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert book != null : "broken rep. invariant";
        assert condition != null : "broken rep. invariant";        
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return book;
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
        checkRep();
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        return book.toString() + (condition == Condition.GOOD ? " condition: good" : " condition: damaged");
    }

    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
