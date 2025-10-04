package com.back.plshare.domain.playlist.controller;

import com.back.plshare.domain.playlist.dto.PlaylistDto;
import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.service.PlaylistService;
import com.back.plshare.global.exception.ServiceException;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pl")
@Tag(name = "ApiV1PlaylistController", description = "플레이리스트 API")
public class ApiV1PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/playlists")
    @Transactional(readOnly = true)
    @Operation(summary = "플레이리스트 목록 조회")
    public RsData<List<PlaylistDto>> getPlaylists() {
        List<PlaylistDto> playlists = playlistService.findAll()
                .stream()
                .map(PlaylistDto::fromEntity)
                .toList();

        return new RsData(
                "200-1",
                "플레이리스트 목록 조회 성공",
                playlists
        );
    }

    @GetMapping("/playlists/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "플레이리스트 단건 조회")
    public RsData<PlaylistDto> getPlaylist(
            @PathVariable Long id
    ) {
        Playlist playlist = playlistService.findById(id).get();

        return new RsData(
                "200-1",
                "플레이리스트 조회 성공",
                PlaylistDto.fromEntity(playlist)
        );
    }

    @DeleteMapping("/playlists/{id}")
    @Operation(summary = "플레이리스트 삭제")
    public RsData<Void> deletePlaylist(
            @PathVariable Long id
    ){
        Playlist playlist = playlistService.findById(id).orElseThrow(()->{
            throw new ServiceException("401-1", "플레이리스트를 찾을 수 없습니다.");
        });

        playlistService.delete(playlist);

        return new RsData(
                "200-1",
                "플레이리스트 삭제 성공"
        );
    }


}


/*
GET    /playlists
GET    /playlists/{id}
DELETE /playlists/{id}

POST   /playlists/{id}/tracks
PUT  /playlists/{id}/tracks/{trackId}
DELETE /playlists/{id}/tracks/{trackId}
 */