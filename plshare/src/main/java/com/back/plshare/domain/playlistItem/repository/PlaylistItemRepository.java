package com.back.plshare.domain.playlistItem.repository;

import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long> {

    @Query("SELECT COALESCE(MAX(pi.trackIdx), 0) FROM PlaylistItem pi WHERE pi.playlist.id = :playlistId")
    int findNextTrackIdx(@Param("playlistId") Long playlistId);
}
