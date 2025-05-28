package me.drex.polymerqol.config;

import com.google.gson.annotations.SerializedName;

public class ServerConfig {
    public String _c0 = "Allows polymer-qol clients to perform block mining client-side";
    @SerializedName("client_mining")
    public boolean clientMining = true;
    public String _c1 = "Prevent polymer-qol clients from swinging their arm when interacting with fake noteblocks";
    @SerializedName("prevent_swing")
    public boolean preventSwing = true;
}
