package com.forum.connectivitymanager.ui.reactive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forum.connectivitymanager.R;

import java.util.ArrayList;
import java.util.List;

public class NetworkStatusAdapter extends RecyclerView.Adapter<NetworkStatusAdapter.StatusViewHolder> {

    private List<NetworkStatusEnum> statusList;

    public NetworkStatusAdapter() {
        statusList = new ArrayList<>();
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_network_status, parent, false);

        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        NetworkStatusEnum status = statusList.get(position);
        holder.textView.setText(status.statusText);
        holder.imageView.setImageResource(status.icon);
    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }

    public void addStatus(NetworkStatusEnum status) {
        statusList.add(0, status);
        notifyItemInserted(0);
    }

    static class StatusViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_status);
            imageView = itemView.findViewById(R.id.icon_status);
        }
    }
}
