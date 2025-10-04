package com.back.plshare.domain.playlist.service;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.repository.PlaylistRepository;
import com.back.plshare.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Playlist create(Member actor, String title, String description, String coverUrl) {

        Playlist playlist = new Playlist(title, description, coverUrl, actor);

        return playlistRepository.save(playlist);
    }

    @Transactional
    public void updatePlaylistInfo(Playlist playlist, Member actor, String title, String description, String coverUrl) {
        if(!playlist.getOwner().getUsername().equals(actor.getUsername())) {
            throw new ServiceException("403-1", "수정 권한이 없습니다");
        }
        playlist.updateInfo(title, description, coverUrl);
    }
}
