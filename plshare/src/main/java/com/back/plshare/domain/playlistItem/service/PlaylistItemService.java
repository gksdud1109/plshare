package com.back.plshare.domain.playlistItem.service;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.repository.PlaylistRepository;
import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import com.back.plshare.domain.playlistItem.entity.TrackSource;
import com.back.plshare.domain.playlistItem.repository.PlaylistItemRepository;
import com.back.plshare.global.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistItemService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;

    @Transactional
    public void addPlaylistItem(Member actor, Long playlistId,
                                String title, String artist, String album, String trackId, TrackSource trackSource) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ServiceException("404-1", "해당 플레이리스트가 존재하지 않습니다."));

        if (!playlist.getOwner().getId().equals(actor.getId())) {
            throw new ServiceException("403-1", "해당 플레이리스트에 추가할 권한이 없습니다.");
        }


        // TODO: 동시성 처리 고려하기
        int nextIdx = playlistItemRepository.findNextTrackIdx(playlistId) + 1;
        PlaylistItem item = new PlaylistItem(playlist, title, artist, album, trackId, trackSource, nextIdx);
        playlist.getPlaylistItems().add(item); // 양방향 연관관계 관리

        playlistItemRepository.save(item); // 또는 playlistRepository.save(playlist);
    }
}
