package com.forum.connectivitymanager.ui.simple;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.forum.connectivitymanager.R;

public class SimpleFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        TextView mainText = view.findViewById(R.id.main_text);
        view.findViewById(R.id.check_network).setOnClickListener(v -> {
            Network activeNetwork = manager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);

            if (networkCapabilities == null) {
                mainText.setText("Disconnected");
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                mainText.setText("Connected to WiFi");
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                mainText.setText("Connected to cellular");
            }
        });
    }
}