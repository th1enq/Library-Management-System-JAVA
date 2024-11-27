package src.librarysystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class BookServicesTest {
    private BookServices bookServices;

    @BeforeEach
    void setUp() {
        bookServices = BookServices.getInstance();
    }


    @Test
    void testSearchBooksByTitle() {
        ArrayList<Book> books = bookServices.searchBooksByTitle("Java Programming");

        assertNotNull(books, "The result should not be null");
        assertTrue(books.size() > 0, "The result should contain at least one book");
    }

    @Test
    void testSearchBooksByAuthor() {
        ArrayList<Book> books = bookServices.searchBooksByAuthor("James");

        assertNotNull(books, "The result should not be null");
        assertTrue(books.size() > 0, "The result should contain at least one book");
    }

    @Test
    void testSearchBooksByCategory() {
        ArrayList<Book> books = bookServices.searchBooksByCategory("Programming");

        assertNotNull(books, "The result should not be null");
        assertTrue(books.size() > 0, "The result should contain at least one book");
    }

    @Test
    void testGenerateQRCode() {
        String testUrl = "https://www.instagram.com/cristiano/";
        javafx.scene.image.Image qrCode = bookServices.generateQRCode(testUrl);

        assertNotNull(qrCode, "The QR code image should not be null");
    }
}
