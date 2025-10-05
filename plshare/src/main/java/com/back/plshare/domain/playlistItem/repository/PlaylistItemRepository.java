package com.back.plshare.domain.playlistItem.repository;

import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT COALESCE(MAX(pi.trackIdx), 0) FROM PlaylistItem pi WHERE pi.playlist.id = :playlistId")
    Integer findMaxTrackIdxWithLock(@Param("playlistId") Long playlistId);
}
