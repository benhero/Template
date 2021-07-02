// IOnNewBookArrivedListener.aidl
package com.ben.template.aidl;

import com.ben.template.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
