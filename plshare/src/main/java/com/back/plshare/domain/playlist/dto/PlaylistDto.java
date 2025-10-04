package com.back.plshare.domain.playlist.dto;

import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlistItem.dto.PlaylistItemDto;

import java.util.List;

public record PlaylistDto(
        Long id,
        String title,
        String description,
        String coverUrl,
        String ownerUsername,
        List<PlaylistItemDto> items
) {
    public static PlaylistDto fromEntity(Playlist playlist) {
        return new PlaylistDto(
                playlist.getId(),
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getCoverUrl(),
                playlist.getOwner().getUsername(),
                playlist.getPlaylistItems().stream()
                        .map(PlaylistItemDto::fromEntity)
                        .toList()
        );
    }
}
