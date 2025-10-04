package com.back.plshare.global.initData;

import com.back.plshare.domain.member.entity.Member;
import com.back.plshare.domain.member.repositoy.MemberRepository;
import com.back.plshare.domain.playlist.entity.Playlist;
import com.back.plshare.domain.playlist.entity.PlaylistVisibility;
import com.back.plshare.domain.playlist.repository.PlaylistRepository;
import com.back.plshare.domain.playlistItem.entity.PlaylistItem;
import com.back.plshare.domain.playlistItem.entity.TrackSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInitializer implements CommandLineRunner {


    @Autowired
    @Lazy
    private TestDataInitializer self;
    private final PlaylistRepository playlistRepository;
    private final MemberRepository memberRepository;


    @Override
    public void run(String... args) throws Exception {
        self.work1();
    }

    void work1(){
        if(playlistRepository.count()>0)
            return;

        // 사용자 생성
        Member user = new Member("testuser", "테스터", "test1234@example.com", "1234");
        memberRepository.save(user);

        // 플레이리스트 1
        Playlist playlist1 = new Playlist();
        playlist1.setTitle("🔥 오늘의 띵곡");
        playlist1.setDescription("기분 좋을 때 듣기 좋은 노래");
        playlist1.setCoverUrl("https://example.com/image1.jpg");
        playlist1.setOwner(user);
        playlist1.setVisibility(PlaylistVisibility.PUBLIC);

        PlaylistItem item1 = new PlaylistItem();
        item1.setPlaylist(playlist1);
        item1.setTitle("Dynamite");
        item1.setArtist("BTS");
        item1.setAlbum("BE");
        item1.setTrackId("yt123");
        item1.setTrackSource(TrackSource.YOUTUBE);
        item1.setTrackIdx(0);

        PlaylistItem item2 = new PlaylistItem();
        item2.setPlaylist(playlist1);
        item2.setTitle("Butter");
        item2.setArtist("BTS");
        item2.setAlbum("Butter");
        item2.setTrackId("yt124");
        item2.setTrackSource(TrackSource.YOUTUBE);
        item2.setTrackIdx(1);

        playlist1.setPlaylistItems(List.of(item1, item2));
        playlistRepository.save(playlist1);

        // 플레이리스트 2
        Playlist playlist2 = new Playlist();
        playlist2.setTitle("🎧 밤에 듣는 감성");
        playlist2.setDescription("밤하늘 감성 노래 모음");
        playlist2.setCoverUrl("https://example.com/image2.jpg");
        playlist2.setOwner(user);
        playlist2.setVisibility(PlaylistVisibility.PUBLIC);
        playlist2.setPlaylistItems(new ArrayList<>());
        playlistRepository.save(playlist2);
    }
}
