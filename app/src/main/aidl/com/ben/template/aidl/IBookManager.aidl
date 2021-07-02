// IBookManager.aidl
package com.ben.template.aidl;

import com.ben.template.aidl.Book;

//import com.ben.template.aidl.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
   List<Book> getBookList();

   void addBook(in Book book);

//   void registerListener(IOnNewBookArrivedListener listener);
//
//   void unRegisterListener(IOnNewBookArrivedListener listener);
}