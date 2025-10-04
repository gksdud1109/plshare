package com.back.plshare.domain.playlist.controller;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlist.dto.PlaylistDto;
import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.service.PlaylistService;
import com.back.plshare.global.exception.ServiceException;
import com.back.plshare.global.jwt.JwtRq;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private final JwtRq jwtRq;

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

    public record CreatePlaylistReqBody(
            @NotBlank
            @Size(min = 4, max = 50)
            String title,

            @NotBlank
            @Size(min = 4, max = 100)
            String description,

            @Size(max = 100)
            String coverUrl
    ) {
    }
    @PostMapping("/playlists")
    @Operation(summary = "플레이리스트 생성")
    public RsData<PlaylistDto> createPlaylist(
            @RequestBody @Valid CreatePlaylistReqBody reqBody
    ) {
        Member actor = jwtRq.getActor();

        Playlist playlist = playlistService.create(
                actor,
                reqBody.title(),
                reqBody.description(),
                reqBody.coverUrl()
        );

        return new RsData<>(
                "201-1",
                "플레이리스트가 생성되었습니다.",
                PlaylistDto.fromEntity(playlist)
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
    ) {
        Playlist playlist = playlistService.findById(id).orElseThrow(() -> {
            throw new ServiceException("401-1", "플레이리스트를 찾을 수 없습니다.");
        });

        playlistService.delete(playlist);

        return new RsData(
                "200-1",
                "플레이리스트 삭제 성공"
        );
    }

    public record UpdatePlaylistReqBody(
            @NotBlank
            @Size(min = 4, max = 50)
            String title,

            @NotBlank
            @Size(min = 4, max = 100)
            String description,

            @Size(max = 100)
            String coverUrl
    ) {
    }
    @PutMapping("/playlists/{id}")
    @Operation(summary = "플레이리스트 정보 수정")
    public RsData<Void> updatePlaylist(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePlaylistReqBody reqBody
    ){
        Playlist playlist = playlistService.findById(id).orElseThrow(() -> new ServiceException("404-1", "플레이리스트를 찾을 수 없습니다."));

        Member actor = jwtRq.getActor();

        playlistService.updatePlaylistInfo(
                playlist,
                actor,
                reqBody.title(),
                reqBody.description(),
                reqBody.coverUrl()
        );

        return new RsData(
                "200-1",
                "플레이리스트 수정 성공"
        );
    }
}