package com.back.plshare.domain.playlist.service;

import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    public List<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    public void delete(Playlist playlist) {
        playlistRepository.delete(playlist);
    }
}
