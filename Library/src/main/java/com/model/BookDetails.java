package com.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="book_detail")
public class BookDetails {
    @Id
    @Column(name ="id")
    private int bookId;
    @Column(name="name")
    private String bookName;

    @Column(name="category_id")
    private int categoryId;
    @Column(name="author_name")
    private String authorName;

    @Column(name="publication")
    private String publication;
    @Column(name="language_id")
    private int languageId;

    @Column(name="cost")
    private int cost;

    @Column(name="isavailable")
    private boolean isAvailable;

    @Column(name="total_copies")
    private int total_copies;



    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getTotal_copies() {
        return total_copies;
    }

    public void setTotal_copies(int total_copies) {
        this.total_copies = total_copies;
    }



    @Override
    public String toString() {
        return "BookDetails{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", categoryId=" + categoryId +
                ", authorName='" + authorName + '\'' +
                ", publication='" + publication + '\'' +
                ", languageId=" + languageId +
                ", cost=" + cost +
                ", isAvailable=" + isAvailable +
                ", total_copies=" + total_copies +
                '}';
    }
}
