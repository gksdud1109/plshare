package com.back.plshare.domain.playlistItem.controller;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.playlistItem.dto.AddItemToPlaylistReqBody;
import com.back.plshare.domain.playlistItem.service.PlaylistItemService;
import com.back.plshare.global.jwt.JwtRq;
import com.back.plshare.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
                reqBody.trackId(),
                reqBody.trackSource()
        );

        return new RsData<>(
                "201-2",
                "트랙이 플레이리스트에 추가되었습니다."
        );
    }

    // TODO: 트랙 재정렬 API(사용자가 UI에서 원하는 순서대로 재정렬하면 그에 맞춰 트랙 idx수정)
}
