package src.librarysystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BookServicesTest {
    private BookServices bookServices;

    @BeforeEach
    void setUp() {
        bookServices = BookServices.getInstance();
    }

    @Test
    void testLoadBook() {
        // Mock JSON response
        JsonObject jsonResponse = new JsonObject();
        JsonArray items = new JsonArray();

        JsonObject book1 = new JsonObject();
        JsonObject volumeInfo1 = new JsonObject();
        volumeInfo1.addProperty("title", "Effective Java");
        volumeInfo1.addProperty("authors", "[\"Joshua Bloch\"]");
        volumeInfo1.addProperty("publishedDate", "2018-01-06");
        volumeInfo1.addProperty("publisher", "Addison-Wesley");
        book1.add("volumeInfo", volumeInfo1);
        items.add(book1);

        jsonResponse.add("items", items);

        ArrayList<Book> books = bookServices.loadBook(jsonResponse);

        assertEquals(1, books.size());
        assertEquals("Effective Java", books.get(0).getTitle());
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
        String testUrl = "https://www.example.com";
        javafx.scene.image.Image qrCode = bookServices.generateQRCode(testUrl);

        assertNotNull(qrCode, "The QR code image should not be null");
    }
}
