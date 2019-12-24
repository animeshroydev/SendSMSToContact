package com.animesh.roy.contactapp.TabFragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.animesh.roy.contactapp.Adapters.ContactAdapter;
import com.animesh.roy.contactapp.Contact;
import com.animesh.roy.contactapp.ContactDetails;
import com.animesh.roy.contactapp.R;
import com.animesh.roy.contactapp.ViewModel.ContactViewModel;

import java.util.List;

public class Tab1Fragment extends Fragment {

    // Tab1Fragment is getting contact information (JSON) from ContactViewModel
    // It's important to break down the code to different class to make app easier for testing and debugging as well.
    // Which make the project easy to read and more modularize.

    private static final String TAG = "MainActivity";
    public static final int CONTACT_REQUEST = 2;

    private ContactViewModel contactViewModel;
    Activity activity = getActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setHasFixedSize(true);

        final ContactAdapter adapter = new ContactAdapter();
        recyclerView.setAdapter(adapter);

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(@Nullable List<Contact> notes) {
                adapter.submitList(notes);
            }
        });


        adapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contact note) {

                Intent intent = new Intent(getActivity(), ContactDetails.class);

                intent.putExtra(ContactDetails.EXTRA_ID, note.getId());
                intent.putExtra(ContactDetails.EXTRA_NAME, note.getTitle());
                intent.putExtra(ContactDetails.EXTRA_PHONE_NUMBER, note.getDescription());
                intent.putExtra(ContactDetails.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, CONTACT_REQUEST);

            }
        });

        return view;
    }


}
