package ru.pinkgoosik.goosiklib.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PlayerCosmetics {

    private final ArrayList<PlayerCosmeticEntry> entries = new ArrayList<>();

    String URL_STRING = "https://gist.github.com/oliviathevampire/ac530435d47dab1817d6a0b2ae132a3c/raw";

    public PlayerCosmetics() throws IOException {
        URL url = new URL(URL_STRING);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(new InputStreamReader((InputStream) request.getContent())).getAsJsonArray();
        jsonArray.forEach(jsonElement -> {
            PlayerCosmeticEntry.Builder entry = PlayerCosmeticEntry.builder()
                .setPlayerName(jsonElement.getAsJsonObject().get("name").getAsString())
                .setPlayerUuid(jsonElement.getAsJsonObject().get("uuid").getAsString())
                .setCosmetic(jsonElement.getAsJsonObject().get("cosmetic").getAsString())
                .setPlacement(jsonElement.getAsJsonObject().get("placement").getAsString());
            entries.add(entry.build());
        });
    }

    public ArrayList<PlayerCosmeticEntry> getEntries(){
        return this.entries;
    }

    public static record PlayerCosmeticEntry(String playerName, String playerUuid, String cosmetic, String placement) {

        public static PlayerCosmeticEntry.Builder builder() {
            return new PlayerCosmeticEntry.Builder();
        }

        public static class Builder {
            private String playerName, playerUuid, cosmetic, placement;

            public PlayerCosmeticEntry.Builder setPlayerName(String playerName) {
                this.playerName = playerName;
                return this;
            }

            public PlayerCosmeticEntry.Builder setPlayerUuid(String playerUuid) {
                this.playerUuid = playerUuid;
                return this;
            }

            public PlayerCosmeticEntry.Builder setCosmetic(String cosmetic) {
                this.cosmetic = cosmetic;
                return this;
            }

            public PlayerCosmeticEntry.Builder setPlacement(String placement) {
                this.placement = placement;
                return this;
            }

            public PlayerCosmeticEntry build() {
                return new PlayerCosmeticEntry(playerName, playerUuid, cosmetic, placement);
            }
        }

    }
}