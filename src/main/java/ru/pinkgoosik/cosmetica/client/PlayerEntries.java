package ru.pinkgoosik.cosmetica.client;

import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PlayerEntries {

	private final Entry entries;
	private final Availabilities availabilities;

	public PlayerEntries() throws IOException {
		URL url = new URL("https://gist.githubusercontent.com/spjoes/f12c53972c28d8fa11baacb25c440503/raw");
		URLConnection request = url.openConnection();
		request.connect();
		entries = CosmeticaClient.GSON.fromJson(new JsonParser().parse(new InputStreamReader((InputStream)
				request.getContent())), Entry.class);
		url = new URL("https://gist.githubusercontent.com/oliviathevampire/d3d698385f92537d5790711283989cfb/raw");
		request = url.openConnection();
		request.connect();
		availabilities = CosmeticaClient.GSON.fromJson(new JsonParser().parse(new InputStreamReader((InputStream)
				request.getContent())), Availabilities.class);
	}

	public Entry entries() {
		return this.entries;
	}

	public Availabilities availabilities() {
		return this.availabilities;
	}

	public static class Availabilities {
		public Cloaks cloaks;
		public String[] cosmetics;
		public String[] attributes;

		public static class Cloaks {
			public String[] colors;
			public String[] types;
		}
	}

	public static class Entry {
		public Entries[] entries;

		public static class Entries {
			@SerializedName("player_information")
			public PlayerInformation playerInformation;
			public String attributes = "normal";
			@SerializedName("cloak_information")
			public CloakInformation cloakInformation;
			@SerializedName("cosmetic_information")
			public CosmeticInformation cosmeticInformation;

			public static class PlayerInformation {
				public String name;
				public String uuid;
			}

			public static class CloakInformation {
				public String type = "normal";
				@SerializedName("cloak_color")
				public String cloakColor = "per_dimension";
				@SerializedName("glowing_color")
				public String glowingColor = "0xFFFFFF";
			}

			public static class CosmeticInformation {
				public String type = "";
				public String placement = "";
			}
		}
	}
}
