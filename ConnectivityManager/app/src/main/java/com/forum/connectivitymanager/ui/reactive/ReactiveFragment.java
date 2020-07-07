package com.forum.connectivitymanager.ui.reactive;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.forum.connectivitymanager.R;

public class ReactiveFragment extends Fragment {

    private RecyclerView recyclerView;
    private NetworkStatusAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reactive, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest request = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new NetworkStatusAdapter();

        recyclerView = view.findViewById(R.id.network_status_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        NetworkCallback networkCallback = new NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                notifyStatusChange(NetworkStatusEnum.CONNECTED);
            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                notifyStatusChange(NetworkStatusEnum.DISCONNECTED);
            }
        };
        manager.requestNetwork(request, networkCallback);
    }

    private void notifyStatusChange(NetworkStatusEnum status) {
        getActivity().runOnUiThread(() -> adapter.addStatus(status));
    }
}