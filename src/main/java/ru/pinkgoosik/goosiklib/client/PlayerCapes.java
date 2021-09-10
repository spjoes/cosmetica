package ru.pinkgoosik.goosiklib.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class PlayerCapes {

    private final ArrayList<PlayerCapes.PlayerCapeEntry> entries = new ArrayList<>();
    private final ArrayList<String> currentlyAvailableCapes = new ArrayList<>(List.of("green", "purple", "red"));

    String URL_STRING = "https://gist.githubusercontent.com/PinkGoosik/5b97e65e9b7cce4a7dcef14205b10f24/raw";

    public PlayerCapes() throws IOException {
        URL url = new URL(URL_STRING);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(new InputStreamReader((InputStream) request.getContent())).getAsJsonArray();
        jsonArray.forEach(jsonElement -> {
            PlayerCapes.PlayerCapeEntry entry = new PlayerCapes.PlayerCapeEntry();
            entry.setPlayerName(jsonElement.getAsJsonObject().get("name").getAsString());
            entry.setPlayerUuid(jsonElement.getAsJsonObject().get("uuid").getAsString());
            entry.setCape(jsonElement.getAsJsonObject().get("cape").getAsString());
            entries.add(entry);
        });
    }

    public ArrayList<PlayerCapes.PlayerCapeEntry> getEntries(){
        return this.entries;
    }

    public ArrayList<String> getAvailableCapes(){
        return this.currentlyAvailableCapes;
    }

    public static class PlayerCapeEntry {

        private String name;
        private String uuid;
        private String cape;

        public String getPlayerName() {
            return name;
        }

        public String getPlayerUuid() {
            return uuid;
        }

        public String getCape() {
            return cape;
        }

        public void setPlayerName(String name) {
            this.name = name;
        }

        public void setPlayerUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setCape(String cape) {
            this.cape = cape;
        }
    }
}
