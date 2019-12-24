package com.animesh.roy.contactapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComposeMessage extends AppCompatActivity {

    private static final String TAG = "ComposeMessage";

    private Button sendMessage;
    private OkHttpClient mClient = new OkHttpClient();
    private Context mContext;
    private EditText editText;
    private String phoneNumber;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        mContext = getApplicationContext();


        sendMessage = findViewById(R.id.composeBtnSend);
        editText = findViewById(R.id.editText);

        Intent intent = getIntent();
        Contact student = intent.getParcelableExtra("Information");

        final String name = student.getTitle();
        phoneNumber = student.getDescription();

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String.format("%06d", number);
        s = String.valueOf(number);

        Log.d(TAG, "onCreate: random number: " + s);


            Log.d(TAG, "onCreate: name of contact: " + (name));
            Log.d(TAG, "onCreate: phone number of contact: " + (phoneNumber));



            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (editText.getText().toString().matches("")) {
                        Toast.makeText(mContext, "Cannot be empty. Please write some message...", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        post(mContext.getString(R.string.backend_url), new  Callback(){

                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        editText.setText("");
                                        Toast.makeText(getApplicationContext(),"SMS Sent to " + name,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    }

    // Reference that helped: https://www.twilio.com/docs/libraries/java?utm_source=youtube&utm_medium=video&utm_campaign=youtube_send_sms
    // https://www.twilio.com/blog/2016/05/how-to-send-an-sms-from-android.html

    Call post(String url, Callback callback) throws IOException {
        RequestBody formBody = new FormBody.Builder()
                .add("To", "+91" +phoneNumber)
                .add("Body", "Hi! your OTP is: " + s + ". And " +editText.getText().toString())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call response = mClient.newCall(request);
        response.enqueue(callback);
        return response;
    }


}
