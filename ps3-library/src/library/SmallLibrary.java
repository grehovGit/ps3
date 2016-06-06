package library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out

    // TODO: safety from rep exposure argument
    
    public SmallLibrary() {
        inLibrary = new HashSet<BookCopy>();
        checkedOut = new HashSet<BookCopy>();
    }
    
    // assert the rep invariant
    private void checkRep() {
    	for (BookCopy copy : inLibrary)
    		assert !checkedOut.contains(copy) : "broken rep. contract";
    	for (BookCopy copy : checkedOut)
    		assert copy != null : "broken rep. contract";
    }

    @Override
    public BookCopy buy(Book book) {
    	assert book != null : "spec. contract broken";
    	BookCopy bookCopy = new BookCopy(book);
        inLibrary.add(bookCopy);
        checkRep();
        return bookCopy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
    	assert copy != null : "spec. contract broken";   	
        if (inLibrary.remove(copy)) checkedOut.add(copy);
    }
    
    @Override
    public void checkin(BookCopy copy) {
    	assert copy != null : "spec. contract broken";   	
        if (checkedOut.remove(copy)) inLibrary.add(copy);
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
    	assert copy != null : "spec. contract broken"; 
    	return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
    	assert book != null : "spec. contract broken"; 
        Set<BookCopy> allCopies = new HashSet();
        for (BookCopy copy : inLibrary) 
        	if(copy.getBook().equals(book))
        		allCopies.add(new BookCopy(book));
        for (BookCopy copy : checkedOut) 
        	if(copy.getBook().equals(book))
        		allCopies.add(new BookCopy(book));        
        return allCopies;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
    	assert book != null : "spec. contract broken"; 
        Set<BookCopy> availableCopies = new HashSet();
        for (BookCopy copy : inLibrary) 
        	if(copy.getBook().equals(book))
        		availableCopies.add(new BookCopy(book));
        return availableCopies;
    }

    @Override
    public List<Book> find(String query) {
       	assert query != null : "spec. contract broken"; 
        List<Book> foundCopies = new ArrayList<Book>();
 
        for (BookCopy copy : inLibrary) { 
        	Book book = copy.getBook();
        	if((book.getTitle().contains(query) || book.getAuthors().contains(query))
        		 && !foundCopies.contains(book))
        		foundCopies.add(book);
        }
        foundCopies.sort(new BookComparator());
        return foundCopies;
    }
    
    private class BookComparator implements Comparator<Book> {
    	public int compare (Book b1, Book b2) {
    		return (b1.getYear() < b2.getYear() ? -1 : (b1.getYear() == b2.getYear() ? 0 : 1));
    	}
    }
    
    @Override
    public void lose(BookCopy copy) {
        throw new RuntimeException("not implemented yet");
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
