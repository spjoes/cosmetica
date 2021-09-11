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

    String URL_STRING = "https://gist.githubusercontent.com/oliviathevampire/19dce3255ce7420b89fb0ab771c93107/raw";

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
            if (jsonElement.getAsJsonObject().get("type") != null) {
                entry.setType(jsonElement.getAsJsonObject().get("type").getAsString());
            } else {
                entry.setType("normal");
            }
            if (jsonElement.getAsJsonObject().get("color") != null) {
                entry.setColor(jsonElement.getAsJsonObject().get("color").getAsString());
            } else {
                entry.setColor("0xFFFFFF");
            }
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
        private String type;
        private String cape;
        private String color;

        public String getPlayerName() {
            return name;
        }

        public String getPlayerUuid() {
            return uuid;
        }

        public String getType() {
            return type;
        }

        public String getCape() {
            return cape;
        }

        public String getColor() {
            return color;
        }

        public void setPlayerName(String name) {
            this.name = name;
        }

        public void setPlayerUuid(String uuid) {
            this.uuid = uuid;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setCape(String cape) {
            this.cape = cape;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
