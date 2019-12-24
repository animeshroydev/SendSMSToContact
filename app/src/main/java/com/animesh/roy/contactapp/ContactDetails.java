package com.animesh.roy.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    private static final String TAG = "ContactDetails";

    public static final String EXTRA_ID =
            "com.animesh.architectureexample.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.animesh.architectureexample.EXTRA_NAME";
    public static final String EXTRA_PHONE_NUMBER =
            "com.animesh.architectureexample.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_PRIORITY =
            "com.animesh.architectureexample.EXTRA_PRIORITY";


    private TextView name, phoneNo;
    private Button btn;
    Contact details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);

        Intent intent = getIntent();

        name = findViewById(R.id.contactName);
        phoneNo = findViewById(R.id.description);
        btn = findViewById(R.id.button);

        if (intent.hasExtra(EXTRA_ID)) {

            Log.d(TAG, "onCreate: name of contact: " + (intent.getStringExtra(EXTRA_NAME)));

            name.setText((intent.getStringExtra(EXTRA_NAME)));
            phoneNo.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));

            details  = new Contact((intent.getStringExtra(EXTRA_NAME)), intent.getStringExtra(EXTRA_PHONE_NUMBER), intent.getIntExtra(EXTRA_PRIORITY, 1));

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Intent intent1 = new Intent(ContactDetails.this, ComposeMessage.class);
                    intent1.putExtra("Information", details);
                   startActivity(intent1);

                }
            });
        }
    }
}

