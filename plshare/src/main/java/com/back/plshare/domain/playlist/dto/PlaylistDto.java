package com.back.plshare.domain.playlist.dto;

import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlistItem.dto.PlaylistItemDto;

import java.util.List;

public record PlaylistDto(
        String title,
        String description,
        String coverUrl,
        List<PlaylistItemDto> items
) {
    public static PlaylistDto fromEntity(Playlist playlist) {
        return new PlaylistDto(
                playlist.getTitle(),
                playlist.getDescription(),
                playlist.getCoverUrl(),
                playlist.getPlaylistItems().stream()
                        .map(PlaylistItemDto::fromEntity)
                        .toList()
        );
    }
}
