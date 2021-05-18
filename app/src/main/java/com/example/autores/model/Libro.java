package com.example.autores.model;

public class Libro {
    private String title;
    private String authors;
    private String printType;
    private String publishedDate;
    private String imageLink;

    public Libro() {
    }

    public Libro(String title, String authors, String printType, String publishedDate, String imageLink) {
        this.title = title;
        this.authors = authors;
        this.printType = printType;
        this.publishedDate = publishedDate;
        this.imageLink = imageLink;
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

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
