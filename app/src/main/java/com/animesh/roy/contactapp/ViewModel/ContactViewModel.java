package com.animesh.roy.contactapp.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;

import com.animesh.roy.contactapp.Contact;
import com.animesh.roy.contactapp.Repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    // ContactViewModel  getting data from ContactRepository using LiveData

    private ContactRepository repository;
    private LiveData<List<Contact>> allContacts;

    public ContactViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

}
