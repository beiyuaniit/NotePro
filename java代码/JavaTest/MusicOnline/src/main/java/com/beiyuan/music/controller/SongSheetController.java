package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Song;
import com.beiyuan.music.entity.SongSheet;
import com.beiyuan.music.service.SongService;
import com.beiyuan.music.service.SongSheetService;
import com.beiyuan.music.service.SongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import java.util.List;

/**
 * <p>
 * 歌单 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/song-sheet")
public class SongSheetController {

    @Autowired
    SongSheetService songSheetService;

    @Autowired
    SongsService songsService;

    @Autowired
    SongService songService;

    @GetMapping("getAllSheet/{page}/{limit}")
    public Result getList(@PathVariable Long page, @PathVariable Long limit){
        Page<SongSheet> pageParam=new Page<>(page,limit);
        IPage<SongSheet> pageModel=songSheetService.page(pageParam);
        return Result.ok(pageModel);
    }

    //获取歌单下所有歌曲
    @GetMapping("getSongs/{songSheetId}")
    public Result getSongs(@PathVariable Long songSheetId){
        List<Integer> songIdList=songsService.getSongId(songSheetId);
        List<Song>songList=songService.listByIds(songIdList);
        return Result.ok(songList);
    }

    @PostMapping("add")
    public Result addSongSheet(@RequestBody  SongSheet songSheet){
        return Result.bool(songSheetService.save(songSheet));
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return Result.bool(songSheetService.removeById(id));
    }

    @PostMapping("update")
    public Result update(@RequestBody SongSheet songSheet){
        return Result.bool(songSheetService.updateById(songSheet));
    }
}

