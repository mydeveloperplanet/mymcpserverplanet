package com.mydeveloperplanet.mymcpserverplanet.server;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    private final List<Song> songs = new ArrayList<>();

    @Tool(name = "get_songs", description = "Get the complete list of Gunter's favorite songs")
    public List<Song> getSongs() {
        return songs;
    }

    @Tool(name = "search_song", description = "Search a single song from Gunter's favorite songs")
    public Song getSong(String title) {
        return songs.stream()
                .filter(song -> song.title().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostConstruct
    public void init() {
        songs.addAll((List.of(
                new Song(new Artist("Bruce Springsteen"), "My Hometown"),
                new Song(new Artist("JJ Johnson"), "Lament")
        )));
    }

}
