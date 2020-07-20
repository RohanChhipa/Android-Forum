package com.forum.basicnetworking;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView quoteHolder = findViewById(R.id.quote_holder);

        Single.create(emitter -> {
            String response = simpleGetRequest();
            if (TextUtils.isEmpty(response)) {
                emitter.onError(new Exception("No response received"));
            } else {
                emitter.onSuccess(response);
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(response -> quoteHolder.setText((String) response));
    }

    private String simpleGetRequest() throws Exception {
        URL url = new URL("http://10.0.2.2:3000");
        HttpURLConnection httpsConnection = (HttpURLConnection) url.openConnection();
        httpsConnection.setRequestMethod("GET");

        httpsConnection.connect();
        int responseCode = httpsConnection.getResponseCode();
        if (responseCode >= 200 && responseCode < 300) {
            InputStream inputStream = httpsConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            return scanner.nextLine();
        }

        Toast.makeText(this, R.string.something_broke, Toast.LENGTH_SHORT)
                .show();

        throw new Exception();
    }

    private void simplePostRequest() {

        String examplePayload = "{\"name\": \"Test\"}";

        try {
            URL url = new URL("http://10.0.2.2:3000");
            HttpURLConnection httpsConnection = (HttpURLConnection) url.openConnection();
            httpsConnection.setRequestMethod("GET");

            OutputStream outputStream = httpsConnection.getOutputStream();
            outputStream.write(examplePayload.getBytes());

            httpsConnection.connect();

            InputStream inputStream = httpsConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}