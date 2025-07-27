package com.mydeveloperplanet.mymcpserverplanet.server;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

@Service
public class ArtistService {

    private final List<Artist> artists = new ArrayList<>();

    @Tool(name = "get_artists", description = "Get the complete list of Gunter's favorite artists")
    public List<Artist> getArtists() {
        return artists;
    }

    @Tool(name = "search_artist", description = "Search a single artist from Gunter's favorite artists")
    public Artist getArtist(String name) {
        return artists.stream()
                .filter(artist -> artist.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @PostConstruct
    public void init() {
        artists.addAll(List.of(
                new Artist("Bruce Springsteen"),
                new Artist("JJ Johnson")
        ));
    }

}
