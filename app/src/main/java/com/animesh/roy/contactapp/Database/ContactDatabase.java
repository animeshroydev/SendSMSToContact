package com.animesh.roy.contactapp.Database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.animesh.roy.contactapp.Contact;
import com.animesh.roy.contactapp.Dao.ContactDao;
import com.animesh.roy.contactapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {

    private static final String TAG = "ContactDatabase";

    private static ContactDatabase instance;
    private static Context activity;

    public abstract ContactDao noteDao();

    public static synchronized ContactDatabase getInstance(Context context) {

        activity = context.getApplicationContext();

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();

        }



    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao noteDao;

        private PopulateDbAsyncTask(ContactDatabase db) {
            noteDao = db.noteDao();
        }


        @Override
        protected Void doInBackground(Void... voids) {

            // If we want we can also add those two entry to our database below. which is commented out.
//            noteDao.insert(new Note("Title 1", "Description 1", 1));
//            noteDao.insert(new Contact("Title 2", "Description 2", 2));

            Log.d(TAG, "doInBackground: called");

            // filling the database with initial JSON data that are retrieved through fillWithStartingData function
            fillWithStartingData(activity);
            return null;
        }
    }



    /**
     * Load data into database from JSON file (res > raw > menu_item.json)
     *
     * @param context to get raw data.
     */

    private static void fillWithStartingData(Context context) {
        ContactDao dao = getInstance(context).noteDao();

        JSONArray contacts = loadJsonArray(context);
        try {
            for (int i = 0; i < contacts.length(); i++) {


                JSONObject contact = contacts.getJSONObject(i);


                String contactName = contact.getString("name");
                String phoneNumber = contact.getString("phone");
                int id = contact.getInt("id");

                dao.insert(new Contact(contactName, phoneNumber,
                        id));

                // for debugging
                Log.d(TAG, "fillWithStartingData: name: " + contactName);
                Log.d(TAG, "fillWithStartingData: phoneNumber: " + phoneNumber);
                Log.d(TAG, "fillWithStartingData: id: " + id);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static JSONArray loadJsonArray(Context context) {
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.menu_item);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());

            // array name is contacts in JSON file (res > raw > menu_item.json)
            return json.getJSONArray("contacts");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
