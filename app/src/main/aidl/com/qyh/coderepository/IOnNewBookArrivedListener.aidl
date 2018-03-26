// IOnNewBookArrivedListener.aidl
package com.qyh.coderepository;

import com.qyh.coderepository.menu.ipc.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
