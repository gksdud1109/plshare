package com.back.plshare.domain.playlistItem.repository;

import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem,Long> {
}
