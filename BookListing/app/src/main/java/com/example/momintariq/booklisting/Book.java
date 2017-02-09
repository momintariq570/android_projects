package com.example.momintariq.booklisting;

/**
 * Created by momintariq on 2/5/17.
 */

public class Book {

    private String title;
    private String author;
    private String previewLink;

    public Book(String title, String author, String previewLink) {
        this.title = title;
        this.author = author;
        this.previewLink = previewLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }
}
