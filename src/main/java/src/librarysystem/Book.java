package src.librarysystem;

import java.util.Optional;

public class Book {
    private String title;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String thumbnail;
    private String ISBN;
    private String description;
    private String numPage;
    private String category;
    private String price;
    private String language;
    private String buyLink;
    private String type = "ebooks";

    public Book(String title, String ISBN, String authors, String publisher, String publishedDate, String description, String thumbnail, String numPage, String category, String price, String language, String buyLink) {
        this.title = title;
        this.ISBN = ISBN;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.thumbnail = thumbnail;
        this.publisher = publisher;
        this.description = description;
        this.numPage = numPage;
        this.category = category;
        this.price = price;
        this.language = language;
        this.buyLink = buyLink;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getDescription() {
        return description;
    }

    public String getNumPage() {
        return numPage;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }

    public String getLanguage() {
        return language;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

