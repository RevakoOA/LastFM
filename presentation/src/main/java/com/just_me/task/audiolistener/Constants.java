package com.just_me.task.audiolistener;

/**
 * Created by USER on 28-Aug-17.
 */

public interface Constants {
    enum Country {
        UKRAINE ("Ukraine"), BELARUS ("Belarus"), MOLDOVA ("Moldova");

        private final String name;

        Country(String s) {
            name = s;
        }

        public boolean equalsName(String otherName) {
            return name.equals(otherName);
        }

        public static Country fromString(String text) {
            for (Country c : Country.values()) {
                if (c.name.equalsIgnoreCase(text)) {
                    return c;
                }
            }
            return null;
        }

        public String toString() {
            return this.name;
        }
    };

    String API_KEY = "e81f61890b7ff8633ca024d0faa449e7";
    String URL = "http://ws.audioscrobbler.com";
    String V = "/2.0/";
    String BASE_URL = URL + V;
    String country = "country";
    String GET_TOP_ARTISTS_END = "?method=geo.gettopartists&country={" + country + "}&api_key=" + API_KEY + "&format=json";
    String GET_TOP_ARTISTS = URL + V + GET_TOP_ARTISTS_END;
    String artist = "{artist}";
    String GET_ARTIST_TOP_ALBUMS = URL + V + "?method=artist.gettopalbums&artist=" + artist + "&api_key=" + API_KEY + "&format=json";
}
