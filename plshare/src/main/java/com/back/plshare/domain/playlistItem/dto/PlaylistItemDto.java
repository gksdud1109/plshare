package com.back.plshare.domain.playlistItem.dto;

import com.back.plshare.domain.playlistItem.entity.PlaylistItem;

public record PlaylistItemDto(
        String title,
        String artist,
        String album,
        String trackSource,
        String trackId,
        int trackIdx
) {
    public static PlaylistItemDto fromEntity(PlaylistItem item) {
        return new PlaylistItemDto(
                item.getTitle(),
                item.getArtist(),
                item.getAlbum(),
                item.getTrackSource().name(),  // or .getExternalCode() if using value
                item.getTrackId(),
                item.getTrackIdx()
        );
    }
}
