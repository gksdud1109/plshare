package com.back.plshare.domain.playlistItem.controller;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlistItem.dto.AddItemToPlaylistReqBody;
import com.back.plshare.domain.playlistItem.service.PlaylistItemService;
import com.back.plshare.global.jwt.JwtRq;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pl")
@Tag(name = "ApiV1PlaylistController", description = "플레이리스트 API")
public class ApiV1PlaylistItemController {

    private final PlaylistItemService playlistItemService;
    private final JwtRq jwtRq;



    @PostMapping("/playlists/{id}/tracks")
    @Operation(summary = "플레이리스트 트랙 추가")
    public RsData<Void> addTrackToPlaylist(
            @PathVariable Long id,
            @RequestBody @Valid AddItemToPlaylistReqBody reqBody
    ){
        Member actor = jwtRq.getActor();

        playlistItemService.addPlaylistItem(
                actor,
                id,
                reqBody.title(),
                reqBody.artist(),
                reqBody.album(),
                reqBody.externalTrackIds(),
                reqBody.trackSource()
        );

        return new RsData<>(
                "201-2",
                "트랙이 플레이리스트에 추가되었습니다."
        );
    }

    public record TrackReorderReqBody(
            @NotEmpty
            List<Long> trackIds
    ) {}
    @PutMapping("/playlists/{id}/tracks/reorder")
    @Operation(summary = "플레이리스트 트랙 순서 재정렬")
    public RsData<Void> reorderTracks(
            @PathVariable Long id,
            @RequestBody @Valid TrackReorderReqBody reqBody
    ){
        Member actor = jwtRq.getActor();
        playlistItemService.reorderTracks(actor, id, reqBody.trackIds());

        return new RsData<>(
                "200",
                "트랙 순서가 재정렬되었습니다."
        );
    }
}
