package com.back.plshare.domain.playlistItem.dto;

import com.back.plshare.domain.playlistItem.entity.TrackSource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddItemToPlaylistReqBody(
        @NotBlank
        String title,

        @NotBlank
        String artist,

        @NotBlank
        String album,

        @NotBlank
        String externalTrackIds, // 외부 트랙 ID (e.g. YouTube ID)

        @NotNull
        TrackSource trackSource // Enum: YOUTUBE, SPOTIFY
) {
}