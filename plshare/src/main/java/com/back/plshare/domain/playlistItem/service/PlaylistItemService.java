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

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistItemService {

    private final PlaylistRepository playlistRepository;
    private final PlaylistItemRepository playlistItemRepository;

    @Transactional
    public void addPlaylistItem(Member actor, Long playlistId,
                                String title, String artist, String album, String externalTrackId, TrackSource trackSource) {

        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ServiceException("404", "해당 플레이리스트가 존재하지 않습니다."));

        if (!playlist.getOwner().getId().equals(actor.getId())) {
            throw new ServiceException("403", "해당 플레이리스트에 추가할 권한이 없습니다.");
        }

        int nextIdx = playlistItemRepository.findMaxTrackIdxWithLock(playlistId) + 1;
        PlaylistItem item = new PlaylistItem(playlist, title, artist, album, externalTrackId, trackSource, nextIdx);
        playlist.getPlaylistItems().add(item); // 양방향 연관관계 관리

        playlistItemRepository.save(item); // 또는 playlistRepository.save(playlist);
    }

    @Transactional
    public void reorderTracks(Member actor, Long playlistId, List<Long> playlistItemIds) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ServiceException("404", "해당 플레이리스트가 존재하지 않습니다."));

        if (!playlist.getOwner().getId().equals(actor.getId())) {
            throw new ServiceException("403", "플레이리스트 재정렬 권한이 없습니다.");
        }

        List<PlaylistItem> items = playlistItemRepository.findAllById(playlistItemIds);

        if (items.size() != playlistItemIds.size()) {
            throw new ServiceException("400", "요청한 트랙 수와 실제 존재하는 트랙 수가 일치하지 않습니다.");
        }

        Map<Long, PlaylistItem> itemMap = items.stream()
                .collect(Collectors.toMap(PlaylistItem::getId, Function.identity()));

        for (int i = 0; i < playlistItemIds.size(); i++) {
            PlaylistItem item = itemMap.get(playlistItemIds.get(i));
            if (!item.getPlaylist().getId().equals(playlistId)) {
                throw new ServiceException("400",
                        "잘못된 트랙 ID 포함. playlistItemId=%d, playlistId=%d"
                                .formatted(item.getId(), item.getPlaylist().getId()));
            }
            item.setTrackIdx(i + 1); // 1-indexed
        }
    }
}
