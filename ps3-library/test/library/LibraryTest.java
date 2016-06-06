package library;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test suite for Library ADT.
 */
@RunWith(Parameterized.class)
public class LibraryTest {

    /*
     * Note: all the tests you write here must be runnable against any
     * Library class that follows the spec.  JUnit will automatically
     * run these tests against both SmallLibrary and BigLibrary.
     */

    /**
     * Implementation classes for the Library ADT.
     * JUnit runs this test suite once for each class name in the returned array.
     * @return array of Java class names, including their full package prefix
     */
    @Parameters(name="{0}")
    public static Object[] allImplementationClassNames() {
        return new Object[] { 
            "library.SmallLibrary", 
            "library.BigLibrary"
        }; 
    }

    /**
     * Implementation class being tested on this run of the test suite.
     * JUnit sets this variable automatically as it iterates through the array returned
     * by allImplementationClassNames.
     */
    @Parameter
    public String implementationClassName;    

    /**
     * @return a fresh instance of a Library, constructed from the implementation class specified
     * by implementationClassName.
     */
    public Library makeLibrary() {
        try {
            Class<?> cls = Class.forName(implementationClassName);
            return (Library) cls.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /*
     * Testing strategy for: BookCopy buy(Book book)
     * ==================
     * 
     * Partitions:
     *    books collection size before buying: =0, >0
     *    duplicates amount before buying: =0, >0
     *    duplicates.book equals book;
     *    
     *    # returns new bookCopy
     *    new bookCopy.book equals book;
     */ 
    // Test cases:
    // [[]] + [book] -> [[bookCopy]]
    // [[bookCopy1]] + [book2] -> [[bookCopy1][bookCopy2]]
    // [[bookCopy1]] + [book1] -> [[bookCopy1][bookCopy1]]; [[bookCopy1.book] equal [bookCopy1.book]]
   
    // Covers new bookCopy.book equals book
    @Test
    public void testBuyNewBookEqualsBook() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        BookCopy newBook = library.buy(book);
        assertEquals(newBook.getBook(), book);
    }
    
    // Covers books collection size before buying: =0;
    // duplicates amount before buying: =0
    @Test
    public void testBuyCollectionSizeBeforeZero() {
        Library library = makeLibrary();
        Book book = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book));
        library.buy(book);
        assertEquals(1, library.availableCopies(book).size());
    }
    
    // Covers books collection size before buying: >0;
    @Test
    public void testBuyCollectionSizeBeforeAboveZero() {
        Library library = makeLibrary();
        Book book1 = new Book("This Test Is Just An Example", Arrays.asList("You Should", "Replace It", "With Your Own Tests"), 1990);
        Book book2 = new Book("A", Arrays.asList("B", "C"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book1));
        library.buy(book1);
        assertEquals(1, library.availableCopies(book1).size());
        library.buy(book2);
        assertEquals(1, library.availableCopies(book2).size());
    }
    
    // Covers duplicates amount before buying: >0
    //    duplicates.book equals book;
    @Test
    public void testBuyDuplicatesAmountBeforeAboveZero() {
        Library library = makeLibrary();
        Book book1 = new Book("A", Arrays.asList("B", "C"), 1990);
        assertEquals(Collections.emptySet(), library.availableCopies(book1));
        BookCopy copy1 = library.buy(book1);
        assertEquals(1, library.availableCopies(book1).size());
        
        Book book1copy = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy copy2 = library.buy(book1copy);
        assertEquals(2, library.availableCopies(book1copy).size());
        assertEquals(copy1, copy2);
    }
    
    /*
     * Testing strategy for: void checkout(BookCopy copy);
     * ==================
     * 
     * Partitions:
     *    available copies in library before checkOut: =1, >1
     *    available duplicate copies in library before checkOut: =0, >0
     */ 
    // Test cases:
    // [[bookCopy1]] - [bookCopy1] -> available [[]]
    // [[bookCopy1][bookCopy2]] - [bookCopy2] -> available [[bookCopy1]]; not available [[bookCopy2]] 
    // [[bookCopy1][bookCopy1']] - [bookCopy1] ->  available [[bookCopy1]]; not available [[bookCopy1']]; availableCopies.size = 1;
    
    
    // Covers available copies in library before checkOut: =1
    // available duplicate copies in library before checkOut: =0
    @Test
    public void testCheckOutAvCopiesBeforeOne() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = library.buy(book);
        assertTrue(library.isAvailable(bookCopy));
        library.checkout(bookCopy);
        assertFalse(library.isAvailable(bookCopy));        
    }
    
    // Covers available copies in library before checkOut: >1
    @Test
    public void testCheckOutAvCopiesBeforeTwoDiff() {
        Library library = makeLibrary();
        Book bookA = new Book("A", Arrays.asList("A", "A"), 1990);
        BookCopy bookACopy = library.buy(bookA);
        Book bookB = new Book("B", Arrays.asList("B", "B"), 1990);
        BookCopy bookBCopy = library.buy(bookB);
        assertTrue(library.isAvailable(bookACopy));
        assertFalse(library.isAvailable(bookBCopy));  
        
        library.checkout(bookBCopy);
        assertTrue(library.isAvailable(bookACopy));
        assertFalse(library.isAvailable(bookBCopy));        
    }
    
    // Covers available duplicate copies in library before checkOut: >0
    @Test
    public void testCheckOutAvCopiesBeforeTwoSame() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("A", "A"), 1990);
        BookCopy bookCopy = library.buy(book);
        BookCopy bookCopy2 = library.buy(book);
        assertTrue(library.isAvailable(bookCopy));
        assertFalse(library.isAvailable(bookCopy2)); 
        assertEquals(2, library.availableCopies(book).size());
        
        library.checkout(bookCopy2);
        assertTrue(library.isAvailable(bookCopy));
        assertFalse(library.isAvailable(bookCopy2)); 
        assertEquals(1, library.availableCopies(book).size());
    }
    
    /*
     * Testing strategy for: void checkIn(BookCopy copy);
     * ==================
     * 
     * Partitions:
     *    available copies in library before checkIn: =0, >0
     *    checkOut copies in library before checkIn: =1, >1
     *    available duplicate copies in library before checkIn: =0, >0
     *    checkOut duplicate copies in library before checkIn: =0, >0
     */ 
    // Test cases:
    // checkOut[[bookCopy]] - available[[]] -> checkOut[[]] - available[[bookCopy]]
    // checkOut[[bookCopy1][bookCopy2]] - available[[bookCopy3]] -> checkOut[[bookCopy1]] - available[[bookCopy3][bookCopy2]]
    // checkOut[[bookCopy1][bookCopy1']] - available[[bookCopy1'']] -> checkOut[[bookCopy1]] - available[[bookCopy1''][bookCopy1']]  

    
    // Covers available copies in library before checkIn: =0
    // checkOut copies in library before checkIn: =1
    // available duplicate copies in library before checkIn: =0
    // checkOut duplicate copies in library before checkIn: =0
    @Test
    public void testCheckInAvCopiesZeroOutCopiesOne() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = library.buy(book);
        assertTrue(library.isAvailable(bookCopy));
        
        library.checkout(bookCopy);
        assertFalse(library.isAvailable(bookCopy));
        assertTrue(library.allCopies(book).size() - library.availableCopies(book).size() == 1);
        library.checkin(bookCopy);
        assertTrue(library.isAvailable(bookCopy));
        assertTrue(library.allCopies(book).size() - library.availableCopies(book).size() == 0);
    }
    
    // Covers available copies in library before checkIn: >0
    // checkOut copies in library before checkIn: >1
    @Test
    public void testCheckInAvCopiesOneOutCopiesTwo() {
        Library library = makeLibrary();
        Book bookA = new Book("A", Arrays.asList("A", "A"), 1990);
        Book bookB = new Book("B", Arrays.asList("B", "B"), 1990);
        Book bookC = new Book("C", Arrays.asList("C", "C"), 1990);
        BookCopy bookCopyA = library.buy(bookA);
        BookCopy bookCopyB = library.buy(bookB);
        BookCopy bookCopyC = library.buy(bookC);
        
        library.checkout(bookCopyA);
        library.checkout(bookCopyB);
        assertFalse(library.isAvailable(bookCopyA));
        assertFalse(library.isAvailable(bookCopyB));
        assertTrue(library.isAvailable(bookCopyC));      
        assertTrue(library.allCopies(bookA).size() - library.availableCopies(bookA).size() == 1);
        assertTrue(library.allCopies(bookB).size() - library.availableCopies(bookB).size() == 1);
        assertTrue(library.allCopies(bookC).size() - library.availableCopies(bookC).size() == 0);
        
        library.checkin(bookCopyB);
        assertFalse(library.isAvailable(bookCopyA));
        assertTrue(library.isAvailable(bookCopyB));
        assertTrue(library.isAvailable(bookCopyC));
        assertTrue(library.allCopies(bookA).size() - library.availableCopies(bookA).size() == 1);
        assertTrue(library.allCopies(bookB).size() - library.availableCopies(bookB).size() == 0);
        assertTrue(library.allCopies(bookC).size() - library.availableCopies(bookC).size() == 0);      
    }
    
    //  Covers  available duplicate copies in library before checkIn: >0
    //  checkOut duplicate copies in library before checkIn: >0
    @Test
    public void testCheckInAvDuplicatesOneOutDuplicatesTwo() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("A", "A"), 1990);
        BookCopy bookCopyA = library.buy(book);
        BookCopy bookCopyB = library.buy(book);
        BookCopy bookCopyC = library.buy(book);
        assertTrue(library.availableCopies(book).size() == 3);
        assertTrue(library.isAvailable(bookCopyA)); 
        assertTrue(library.isAvailable(bookCopyB)); 
        assertTrue(library.isAvailable(bookCopyC)); 
        
        library.checkout(bookCopyA);
        library.checkout(bookCopyB);
        assertFalse(library.isAvailable(bookCopyA));
        assertFalse(library.isAvailable(bookCopyB));
        assertTrue(library.isAvailable(bookCopyC));     
        assertTrue(library.allCopies(book).size() == 3);
        assertTrue(library.availableCopies(book).size() == 1);
        
        library.checkin(bookCopyB);
        assertFalse(library.isAvailable(bookCopyA));
        assertTrue(library.isAvailable(bookCopyB));
        assertTrue(library.isAvailable(bookCopyC));
        assertTrue(library.allCopies(book).size() == 3);
        assertTrue(library.availableCopies(book).size() == 2);      
    }
    
    /*
     * Testing strategy for: boolean isAvailable(BookCopy copy);
     * ==================
     * 
     * Partitions:
     *    copy: isAvailable, Absent, checkout
     *    # returns: true, false;
     */ 
    // Test cases:
    // absent[[copy]] -> false
    // checkOut[[copy]] -> false
    // available[[bookCopy]] -> true
    
    // Covers copy: Absent
    @Test
    public void testIsAvailableAbsent() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = new BookCopy(book);      
        assertFalse(library.isAvailable(bookCopy));
    }
    
    // Covers copy: isAvailable, checkout
    @Test
    public void testIsAvailableCheckoutAvailable() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = library.buy(book);      
        assertTrue(library.isAvailable(bookCopy));
        library.checkout(bookCopy);
        assertFalse(library.isAvailable(bookCopy));
    }
    
    /*
     * Testing strategy for: Set<BookCopy> allCopies(Book book);
     * ==================
     * 
     * Partitions:
     *    copies places:  Absent, isAvailable, checkout
     *    # returns: valid Set<BookCopy>
     *    set.size: =0, >0
     */ 
    // Test cases:
    // absent[[copy]] -> set.size = 0
    // checkOut[[copy]] -> set.size = 1
    // available[[copy]] -> set.size = 1
    
    // Covers copies places:  Absent; set.size: =0;
    @Test
    public void testAllCopiesAbsent() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);     
        assertTrue(library.allCopies(book).size() == 0);
    }
    
    // Covers copies places: isAvailable, checkout; set.size: >0;
    @Test
    public void testAllCopiesAvailableCheckedOut() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = library.buy(book);      
        assertTrue(library.allCopies(book).size() == 1);
        library.checkout(bookCopy);
        assertTrue(library.allCopies(book).size() == 1);
    }
    
    /*
     * Testing strategy for: Set<BookCopy> availableCopies(Book book);
     * ==================
     * 
     * Partitions:
     *    copies places:  Absent, isAvailable, checkout
     *    # returns: valid Set<BookCopy>
     *    set.size: =0, >0
     */ 
    // Test cases:
    // absent[[copy]] -> set.size = 0
    // checkOut[[copy]] -> set.size = 0
    // available[[copy]] -> set.size = 1
    
    // Covers copies places:  Absent; set.size: =0;
    @Test
    public void testAvailableCopiesAbsent() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);     
        assertTrue(library.availableCopies(book).size() == 0);
    }
    
    // Covers copies places: isAvailable, checkout; set.size: >0;
    @Test
    public void testAvailableCopiesAvailableCheckedOut() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);
        BookCopy bookCopy = library.buy(book);      
        assertTrue(library.allCopies(book).size() == 1);
        library.checkout(bookCopy);
        assertTrue(library.allCopies(book).size() == 0);
    }

    /*
     * Testing strategy for: List<Book> find(String query);
     * ==================
     * 
     * Partitions:
     * 	  find.size: =0, >0;
     * 	  bookCopy list.size =0, >0; 
     * 	  find.content: book.getTitle(), book.getAuthors().get(i)
     *    find.content: same books matching order by year
     *    copies places:  Absent, isAvailable, checkout
     *    # returns: valid List<Book>
     *    List.size: =0, >0
     *    List.sameBook amount =1;
     */ 
    // Test cases:
    // "" 								-> list.size = 0
    // "absent value" 					-> list.size = 0
    // find(book.getTitle()) 			-> set.size = 1
    // find(book.getTitle()) 			-> set.size > 1
    // find(book.getAuthors().get(i)) 	-> set.size = 1
    // find(book.getAuthors().get(i)) 	-> set.size > 1
    // find(book.getTitle())=> checkOut -> set.size = 1
    // find(book.getTitle())=>available -> set.size = 1   
    // find("matching value") 			-> set.size = 2; titles equal, author equal => order by year
    // find("matching value") + [[bookCopy][bookCopy']] -> set.size = 1;
    
    // Covers find.size: =0;  bookCopy set.size =0;
    // List.size: =0;
    @Test
    public void testFindArgSizeZeroBookListSizeZero() {
        Library library = makeLibrary();    
        assertTrue(library.find("").size() == 0);
    }
    
    // Covers find.size: >0;  bookCopy set.size >0;
    // copies places:  Absent
    // List.size: =0;
    @Test
    public void testFindArgSizeNotZeroBookListSizeNotZero_Absent() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);  
        library.buy(book);
        assertTrue(library.find("absent value").size() == 0);
    }
    
    // Covers book.getTitle() must match =1
    // copies places:  available
    // List.size: >0;
    @Test
    public void testFindGetTitleMatchOnce_Available() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);  
        library.buy(book);
        assertTrue(library.find(book.getTitle()).size() == 1);
    }
    
    // Covers book.getTitle()  match >1
    // copies places:  checkout
    // List.size: >0;
    @Test
    public void voidtestFindGetTitleMatchTwice_Checkout() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990); 
        Book book2 = new Book("A", Arrays.asList("BB", "CC"), 1990); 
        library.buy(book);
        BookCopy book2copy = library.buy(book2);
        library.checkout(book2copy);
        assertTrue(library.find(book.getTitle()).size() == 2);
    }
    
    // Covers book.getAuthors().get(i) must match =1
    @Test
    public void testFindGetAuthorMatchOnce() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "C"), 1990);  
        library.buy(book);
        assertTrue(library.find(book.getAuthors().get(0)).size() == 1);
    }
    
    // Covers book.getAuthors().get(i) must match >1
    @Test
    public void testFindGetAuthorMatchTwice() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "c"), 1990);  
        library.buy(book);
        Book book2 = new Book("A", Arrays.asList("c", "d"), 1990); 
        library.buy(book2);
        assertTrue(library.find(book2.getAuthors().get(0)).size() == 2);
    }
    
    // Covers same books matching order by year
    @Test
    public void testFindSimilarBookOrderByYear() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "c"), 1990);  
        library.buy(book);
        Book book2 = new Book("A", Arrays.asList("c", "d"), 1995); 
        library.buy(book2);
        assertTrue(library.find("A").get(0).equals(book2));
        assertTrue(library.find("A").get(1).equals(book));
    }
    
    // Covers same books matching order by year
    @Test
    public void testFindSameBookOnce() {
        Library library = makeLibrary();
        Book book = new Book("A", Arrays.asList("B", "c"), 1990);  
        library.buy(book);
        library.buy(book);
        assertTrue(library.find("A").size() == 1);
    }
    
    /*
     * Testing strategy for: void lose(BookCopy copy);
     * ==================
     * 
     * Partitions:
     *    copy: isAvailable, Absent, checkout
     *    # returns: true, false;
     */ 
    // Test cases:
    // absent[[copy]] -> false
    // checkOut[[copy]] -> false
    // available[[bookCopy]] -> true
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
