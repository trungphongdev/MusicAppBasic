package com.example.musicappbasic;

import java.io.File;

public class Song {
    private  String nameSong;
    private  String artist;
    private int file;

    public Song(String nameSong, String artist, int file) {
        this.nameSong = nameSong;
        this.artist = artist;
        this.file = file;
    }

    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }
}
