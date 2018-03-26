// IBookManager.aidl
package com.qyh.coderepository;

import com.qyh.coderepository.menu.ipc.aidl.Book;
import com.qyh.coderepository.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
