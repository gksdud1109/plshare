package com.back.plshare.domain.playlist.entity;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import com.back.plshare.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Playlist extends BaseEntity {

    @Column(nullable = false)
    String title;

    String description;

    @Column(name = "cover_url")
    String coverUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlaylistVisibility visibility = PlaylistVisibility.PUBLIC;

    @ManyToOne(fetch = FetchType.LAZY)
    Member owner;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaylistItem> playlistItems = new ArrayList<>();


    public Playlist(String title, String description, String coverUrl, Member owner) {
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.owner = owner;
    }
}
