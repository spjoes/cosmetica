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

    String URL_STRING = "https://raw.githubusercontent.com/PinkGoosik/goosik-lib/data/supporters.json";

    public PlayerCosmetics() throws IOException {
        URL url = new URL(URL_STRING);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = jsonParser.parse(new InputStreamReader((InputStream) request.getContent())).getAsJsonArray();
        jsonArray.forEach(jsonElement -> {
            PlayerCosmeticEntry entry = new PlayerCosmeticEntry();
            entry.setPlayerName(jsonElement.getAsJsonObject().get("name").getAsString());
            entry.setCosmetic(jsonElement.getAsJsonObject().get("cosmetic").getAsString());
            entries.add(entry);
        });
    }

    public ArrayList<PlayerCosmeticEntry> getEntries(){
        return this.entries;
    }

    public static class PlayerCosmeticEntry {

        private String playerName;
        private String cosmetic;

        public String getPlayerName() {
            return playerName;
        }

        public String getCosmetic() {
            return cosmetic;
        }

        public void setPlayerName(String name) {
            this.playerName = name;
        }

        public void setCosmetic(String cosmetic) {
            this.cosmetic = cosmetic;
        }
    }
}
