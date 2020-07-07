package com.forum.connectivitymanager.ui.reactive;

import com.forum.connectivitymanager.R;

public enum NetworkStatusEnum {

    CONNECTED("Connected", R.drawable.ic_wifi),
    DISCONNECTED("Disconnected", R.drawable.ic_wifi_off);

    public final String statusText;
    public final int icon;

    NetworkStatusEnum(String statusText, int icon) {
        this.statusText = statusText;
        this.icon = icon;
    }
}
