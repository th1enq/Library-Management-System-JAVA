package src.librarysystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

public class BookServices {
    public static ArrayList<Book> searchBooks(String query) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String API_KEY = "AIzaSyCI2U6tHVrTcuYbsFilyfbUy4hwkYftIYw";
            String formattedQuery = query.replace(" ", "+");
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + formattedQuery + "&key=" + API_KEY;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonResponse = JsonParser.parseReader(reader).getAsJsonObject();
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

                        String buyLink = saleInfo.has("buyLink") ? saleInfo.get("buyLink").getAsString() : "Unknown";

                        books.add(new Book(title, ISBN, authors, publisher, publishedDate, description, thumbnail, numPage, category, price, language, buyLink));
                    }
                }
            } else {
                System.out.println("Error: Could not fetch data from API. Response code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
