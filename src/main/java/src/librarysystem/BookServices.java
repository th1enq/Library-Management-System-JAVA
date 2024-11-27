package src.librarysystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class BookServices {
    private static BookServices instance;

    private BookServices() {}

    public static BookServices getInstance() {
        if (instance == null) {
            synchronized (BookServices.class) {
                if (instance == null) {
                    instance = new BookServices();
                }
            }
        }
        return instance;
    }

    public ArrayList<Book> loadBook(JsonObject jsonResponse) {
        ArrayList<Book> books = new ArrayList<>();

        JsonArray items = jsonResponse.getAsJsonArray("items");

        if (items != null) {
            for (int i = 0; i < items.size(); i++) {
                JsonObject volumeInfo = items.get(i).getAsJsonObject().getAsJsonObject("volumeInfo");
                String title = volumeInfo.get("title").getAsString();

                // Process authors (remove brackets and format nicely)
                String authors = "Unknown";
                if (volumeInfo.has("authors")) {
                    JsonArray authorsArray = volumeInfo.getAsJsonArray("authors");
                    StringBuilder authorsBuilder = new StringBuilder();
                    for (int j = 0; j < authorsArray.size(); j++) {
                        authorsBuilder.append(authorsArray.get(j).getAsString());
                        if (j < authorsArray.size() - 1) {
                            authorsBuilder.append(", ");
                        }
                    }
                    authors = authorsBuilder.toString();
                }

                String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : "Unknown";
                String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : "Unknown";

                String numPage = volumeInfo.has("pageCount") ? String.valueOf(volumeInfo.get("pageCount").getAsInt()) : "Unknown";

                String category = "Unknown";
                if(volumeInfo.has("categories")) {
                    JsonArray categoriesArray = volumeInfo.getAsJsonArray("categories");
                    StringBuilder categoriesBuilder = new StringBuilder();
                    for (int j = 0; j < categoriesArray.size(); j++) {
                        categoriesBuilder.append(categoriesArray.get(j).getAsString());
                        if (j < categoriesArray.size() - 1) {
                            categoriesBuilder.append(", ");
                        }
                    }
                    category = categoriesBuilder.toString();
                }

                // Get book cover image link
                String thumbnail = "";
                if (volumeInfo.has("imageLinks")) {
                    thumbnail = volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString();
                }

                String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No Description";
                String ISBN = "None";
                if (volumeInfo.has("industryIdentifiers")) {
                    JsonArray identifiers = volumeInfo.getAsJsonArray("industryIdentifiers");
                    for (int j = 0; j < (int) identifiers.size(); j++) {
                        JsonObject identifier = identifiers.get(j).getAsJsonObject();
                        String type = identifier.get("type").getAsString();
                        String identifierValue = identifier.get("identifier").getAsString();

                        ISBN = identifierValue;
                    }
                }

                JsonObject saleInfo = items.get(i).getAsJsonObject().getAsJsonObject("saleInfo");

                String price = "Unknown";
                if (saleInfo.has("retailPrice")) {
                    JsonObject retailPriceObject = saleInfo.getAsJsonObject("retailPrice");

                    // Lấy giá trị số tiền và loại tiền tệ nếu có
                    if (retailPriceObject.has("amount") && retailPriceObject.has("currencyCode")) {
                        double amount = retailPriceObject.get("amount").getAsDouble();
                        String currencyCode = retailPriceObject.get("currencyCode").getAsString();
                        price = amount + " " + currencyCode;  // Kết hợp giá trị và loại tiền tệ
                    }
                }

                String language = "Unknown";
                if (volumeInfo.has("language")) {
                    language = volumeInfo.get("language").getAsString();
                }

                String buyLink = volumeInfo.has("infoLink") ? volumeInfo.get("infoLink").getAsString() : "Unknown";

                books.add(new Book(title, ISBN, authors, publisher, publishedDate, description, thumbnail, numPage, category, price, language, buyLink));
            }
        }
        return books;
    }

    public ArrayList<Book> searchBooksByAuthor(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String API_KEY = "AIzaSyCI2U6tHVrTcuYbsFilyfbUy4hwkYftIYw";
            String formattedQuery = "inauthor:" + query.replace(" ", "+");
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery + "&key=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
                books = loadBook(jsonResponse);
            } else {
                System.out.println("Error: Could not fetch data from API. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }


    public ArrayList<Book> searchBooksByCategory(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String API_KEY = "AIzaSyCI2U6tHVrTcuYbsFilyfbUy4hwkYftIYw";
            String formattedQuery = "subject:" + query.replace(" ", "+");
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery + "&maxResults=20&key=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
                books = loadBook(jsonResponse);
            } else {
                System.out.println("Error: Could not fetch data from API. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }


    public ArrayList<Book> searchBooksByTitle(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String API_KEY = "AIzaSyCI2U6tHVrTcuYbsFilyfbUy4hwkYftIYw";
            String formattedQuery = query.replace(" ", "+");
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery + "&maxResults=20&key=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
                books = loadBook(jsonResponse);
            } else {
                System.out.println("Error: Could not fetch data from API. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public Image generateQRCode(String bookUrl) {
        int size = 200;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            BitMatrix bitMatrix = qrCodeWriter.encode(bookUrl, BarcodeFormat.QR_CODE, size, size, hints);

            int qrColor = 0xFF000000; // QR code color (black)
            int bgColor = 0xFFF9F9F9; // Background color (light gray)

            MatrixToImageConfig config = new MatrixToImageConfig(qrColor, bgColor);

            // Create the QR code image with custom colors
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

            // Convert BufferedImage to ByteArrayInputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", outputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            // Return JavaFX Image
            return new Image(inputStream);

        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        // Return null in case of failure
        return null;
    }
}
