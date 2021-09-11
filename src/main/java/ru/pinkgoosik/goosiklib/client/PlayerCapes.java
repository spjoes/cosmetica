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
            PlayerCapeEntry.Builder entry = PlayerCapeEntry.builder()
                .setPlayerName(jsonElement.getAsJsonObject().get("name").getAsString())
                .setPlayerUuid(jsonElement.getAsJsonObject().get("uuid").getAsString())
                .setCape(jsonElement.getAsJsonObject().get("cape").getAsString());

            if (jsonElement.getAsJsonObject().get("type") != null) entry.setType(jsonElement.getAsJsonObject().get("type").getAsString());
            else entry.setType("normal");

            if (jsonElement.getAsJsonObject().get("color") != null) entry.setColor(jsonElement.getAsJsonObject().get("color").getAsString());
            else entry.setColor("0xFFFFFF");

            entries.add(entry.build());
        });
    }

    public ArrayList<PlayerCapes.PlayerCapeEntry> getEntries(){
        return this.entries;
    }

    public ArrayList<String> getAvailableCapes(){
        return this.currentlyAvailableCapes;
    }

    public static record PlayerCapeEntry(String playerName, String playerUuid, String type, String cape, String color) {

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private String playerName, playerUuid, type, cape, color;

            public Builder setPlayerName(String playerName) {
                this.playerName = playerName;
                return this;
            }

            public Builder setPlayerUuid(String playerUuid) {
                this.playerUuid = playerUuid;
                return this;
            }

            public Builder setType(String type) {
                this.type = type;
                return this;
            }

            public Builder setCape(String cape) {
                this.cape = cape;
                return this;
            }

            public Builder setColor(String color) {
                this.color = color;
                return this;
            }

            public PlayerCapeEntry build() {
                return new PlayerCapeEntry(playerName, playerUuid, type, cape, color);
            }
        }

    }
}
