package me.drex.polymerqol.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static me.drex.polymerqol.PolymerQOL.LOGGER;

public class ConfigManager {
    public static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    public static final Path CONFIG_FILE = CONFIG_DIR.resolve("polymer-qol-server.json");
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    public static ServerConfig serverConfig = new ServerConfig();

    public static boolean load() {
        LOGGER.info("Loading polymer-qol config");
        if (Files.exists(CONFIG_FILE)) {
            try {
                String data = Files.readString(CONFIG_FILE);
                try {
                    serverConfig = GSON.fromJson(data, ServerConfig.class);
                    return true;
                } catch (JsonSyntaxException e) {
                    LOGGER.error("Failed to parse polymer-qol config", e);
                }
            } catch (IOException e) {
                LOGGER.error("Failed to load polymer-qol config", e);
            }
        } else {
            try {
                Files.writeString(CONFIG_FILE, GSON.toJson(serverConfig));
                return true;
            } catch (IOException e) {
                LOGGER.error("Failed to save polymer-qol config", e);
            }
        }
        return false;
    }
}
