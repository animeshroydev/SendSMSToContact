package com.animesh.roy.contactapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.animesh.roy.contactapp.TabFragments.Tab1Fragment;
import com.animesh.roy.contactapp.TabFragments.Tab2Fragment;

public class MainActivity extends AppCompatActivity {

    // Screenshot of the completed App: https://photos.app.goo.gl/QyJneLqvo5ibGnVW8
    // Demo Video: https://photos.app.goo.gl/nrUxECig5tVHtuBs8

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "First Tab");
        adapter.addFragment(new Tab2Fragment(), "Second Tab");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


    }
}
