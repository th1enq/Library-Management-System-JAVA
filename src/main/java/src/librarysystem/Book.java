package src.librarysystem;

import java.util.Optional;

public class Book {

    private String type = "ebooks";
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
    private String avail;
    private int rating;

    public Book(String title, String ISBN, String authors, String publisher,
        String publishedDate,
        String description, String thumbnail, String numPage, String category, String price,
        String language, String buyLink) {
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
        this.avail = "YES";
        this.rating = 0;
    }

    public Book(String title, String ISBN, String authors, String publisher,
        String publishedDate,
        String description, String thumbnail, String numPage, String category, String price,
        String language, String buyLink, String avail, int rating) {
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
        this.avail = avail;
        this.rating = rating;
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

    public String getAvail() {
        return avail;
    }

    public int getRating() {
        return rating;
    }

    public void print() {
        System.out.println(title + " "
            + ISBN + " " + authors + " " + publisher + " " + publishedDate + " " + description + " "
            + thumbnail + " " + numPage + " " + category + " " + price + " " +
            language + " " + buyLink + " " + avail + " " + rating);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

