package com.forum.keystoredemo;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import javax.crypto.*;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private KeyStore keyStore;

    private final static String TAG = "KeyStoreDemo";
    private final static String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private final static String KEY_ALIAS = "MySecureKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            loadKeyStore();
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        setupButtonClickListeners();
    }

    private void loadKeyStore() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        // Get an instance of the KeyStore using the provider you specify
        keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER);

        // Load the KeyStore into the application space
        keyStore.load(null);

        Log.d(TAG, "KeyStore loaded");
    }

    private void setupButtonClickListeners() {
        findViewById(R.id.btnGenerateKey).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                try {
                    generateKey();
                } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnListAliases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    listAliases();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btnFetchKey).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fetchKey();
                } catch (UnrecoverableEntryException | NoSuchAlgorithmException | KeyStoreException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Note that the new KeyProperties helpers are only available from API 23+
    // For older versions, you would have to specify the properties by text
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void generateKey() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        // Get an instance of the KeyGenerator using the algorithm your encryption will be performing, and the KeyStore provider you want to store the key in
        // Note: For RSA encryption, you will use a KeyPairGenerator instead
        KeyGenerator keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER);

        // Initialise the generator with the specifications of your key
        keyGen.init(new KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build());

        // Generate the key
        SecretKey key = keyGen.generateKey();

        Log.d(TAG, "Key generated: " + key.toString());
    }

    private void listAliases() throws KeyStoreException {
        // Fetch all keys' aliases stored in the KeyStore
        Enumeration<String> aliases = keyStore.aliases();

        TextView txtAliases = findViewById(R.id.txtAliases);
        StringBuilder text = new StringBuilder("Aliases:\n");

        for(String alias: Collections.list(aliases)) {
            text.append(alias).append("\n");
        }

        txtAliases.setText(text);
    }

    private void fetchKey() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        // Fetch the key entry from the KeyStore using the alias you provided it with
        KeyStore.SecretKeyEntry keyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(KEY_ALIAS, null);

        // Extract the key from the KeyEntry and begin using it
        SecretKey key = keyEntry.getSecretKey();

        Log.d(TAG, "Key from KeyStore: " + key.toString());
    }
}