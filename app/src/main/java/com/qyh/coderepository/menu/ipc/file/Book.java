package com.qyh.coderepository.menu.ipc.file;

import java.io.Serializable;

/**
 * @author 邱永恒
 * @time 2018/3/17  12:09
 * @desc ${TODO}
 */


public class Book implements Serializable{
    private String bookName;
    private int bookId;

    public Book(String bookName, int bookId) {
        this.bookName = bookName;
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
