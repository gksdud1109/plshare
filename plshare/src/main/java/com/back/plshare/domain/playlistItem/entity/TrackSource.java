package com.back.plshare.domain.playlistItem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TrackSource {

    YOUTUBE("youtube"),
    SPOTIFY("spotify");

    private final String externalCode;

    public static TrackSource from(String externalCode) {
        return Arrays.stream(values())
                .filter(ts -> ts.externalCode.equalsIgnoreCase(externalCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid trackSource: " + externalCode));
    }
}
