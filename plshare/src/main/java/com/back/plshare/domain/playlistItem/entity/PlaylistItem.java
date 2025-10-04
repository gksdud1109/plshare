package com.back.plshare.domain.playlistItem.entity;

import com.back.plshare.domain.playlist.entity.Playlist;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
        @Index(name = "idx_playlist_track_order", columnList = "playlist_id, track_idx")
})
public class PlaylistItem {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    Playlist playlist;  // 소속 플레이리스트

    @Column(name = "track_id",nullable = false)
    String trackId;     // 외부 트랙 ID

    @Enumerated(EnumType.STRING)
    @Column(name = "track_source", nullable = false)
    TrackSource trackSource;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String artist;

    @Column(nullable = false)
    String album;

    @Column(name = "track_idx", nullable = false, unique = true)
    Integer trackIdx;


    public PlaylistItem(Playlist playlist, String title, String artist, String album, String trackId, TrackSource trackSource, int trackIdx) {
        this.playlist = playlist;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.trackId = trackId;
        this.trackSource = trackSource;
        this.trackIdx = trackIdx;
    }
}