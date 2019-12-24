package com.animesh.roy.contactapp.Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.animesh.roy.contactapp.Contact;

import java.util.List;

// DAO is Data access object. As you can see here we have defined database interaction method

@Dao
public interface ContactDao {

    @Insert
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact co);

    @Query("DELETE FROM note_table")
    void deleteAllContact();


     // Get all the information from table in Ascending order.

    @Query("SELECT * FROM note_table ORDER BY priority ASC")
    LiveData<List<Contact>> getAllContact();

}
