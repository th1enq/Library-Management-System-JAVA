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
    private int avail;
    private int rating;

    public Book() {
    }

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
        this.avail = 1;
        this.rating = 0;
    }

    public Book(String title, String ISBN, String authors, String publisher,
                String publishedDate,
                String description, String thumbnail, String numPage, String category, String price,
                String language, String buyLink, int avail, int rating) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumPage() {
        return numPage;
    }

    public void setNumPage(String numPage) {
        this.numPage = numPage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public int getAvail() {
        return avail;
    }

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Book ( " +
                "Type='" + type + '\'' +
                ", Title='" + title + '\'' +
                ", Authors='" + authors + '\'' +
                ", Publisher='" + publisher + '\'' +
                ", Published Date='" + publishedDate + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", Description='" + description + '\'' +
                ", Thumbnail='" + thumbnail + '\'' +
                ", Number of Pages='" + numPage + '\'' +
                ", Category='" + category + '\'' +
                ", Price='" + price + '\'' +
                ", Language='" + language + '\'' +
                ", Buy Link='" + buyLink + '\'' +
                ", Availability='" + avail + '\'' +
                ", Rating=" + rating +
                ") ";
    }

}