package com.animesh.roy.contactapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import android.os.AsyncTask;

import com.animesh.roy.contactapp.Contact;
import com.animesh.roy.contactapp.Dao.ContactDao;
import com.animesh.roy.contactapp.Database.ContactDatabase;

import java.util.List;

public class ContactRepository {


    // ContactRepository getting data from ContactDao

    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    // Singleton Pattern

    public ContactRepository(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.noteDao();
        allContacts = contactDao.getAllContact();
    }

    public void insert(Contact contact) {
        new InsertNoteAsyncTask(contactDao).execute(contact);
    }

    public void update(Contact contact) {
        new UpdateNoteAsyncTask(contactDao).execute(contact);
    }

    public void delete(Contact contact) {
        new DeleteNoteAsyncTask(contactDao).execute(contact);
    }

    public void deleteAllContact() {
        new DeleteAllNotesAsyncTask(contactDao).execute();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao1;

        private InsertNoteAsyncTask(ContactDao contactDao) {
            this.contactDao1 = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao1.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao1;

        private UpdateNoteAsyncTask(ContactDao contactDao1) {
            this.contactDao1 = contactDao1;
        }

        @Override
        protected Void doInBackground(Contact... notes) {
            contactDao1.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;

        private DeleteNoteAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contact) {
            contactDao.delete(contact[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;

        private DeleteAllNotesAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.deleteAllContact();
            return null;
        }
    }

}
