package com.example.gamezale.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.gamezale.Database.Contact;
import com.example.gamezale.Database.ContactDao;
import com.example.gamezale.Database.Database;

import java.util.List;

public class ContactsViewModel extends AndroidViewModel {

    private ContactDao contactDao;
    private LiveData<List<Contact>> contactsLiveData;
    private LiveData<Contact> currentContact;

    public ContactsViewModel(@NonNull Application application)
    {
        super(application);
        contactDao = Database.getDatabase(application).contactDao();
        contactsLiveData = contactDao.getAllContacts();
    }

    public LiveData<List<Contact>> getContactList()
    {
        return this.contactsLiveData;
    }

    public void insert(Contact contact)
    {
        contactDao.insertContact(contact);
    }

//    public LiveData<Contact> getCurrentContact(Contact contact)
//    {
//
//    }

    public void update(Contact contact)
    {
        contactDao.updateContact(contact);
    }

    public void deleteAll()
    {
        contactDao.deleteAll();
    }

    public void delete(Contact contact)
    {
        contactDao.deleteContact(contact);
    }
}
