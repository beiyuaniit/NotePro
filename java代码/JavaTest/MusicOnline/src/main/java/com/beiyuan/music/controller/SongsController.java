package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Song;
import com.beiyuan.music.entity.Songs;
import com.beiyuan.music.service.SongService;
import com.beiyuan.music.service.SongsService;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/songs")
public class SongsController {

    @Autowired
    SongsService songsService;


    @GetMapping("getSongs/{page}/{limit}")
    public Result getSongs(@PathVariable Long page,@PathVariable Long limit){
        Page<Songs>pageParam =new Page<>(page,limit);
        IPage<Songs>pageModel=songsService.page(pageParam);

        return Result.ok(pageModel);
    }


    @DeleteMapping("deleteSongs/{id}")
    public Result deleteSongs(@PathVariable Long id){
        return Result.bool(songsService.removeById(id));
    }

    @PostMapping("updateSongs")
    public Result updateSongs(@RequestBody Songs songs){
        return Result.bool(songsService.updateById(songs));
    }

    @PostMapping("addSongs")
    public Result addSongs(@RequestBody Songs songs){
        return Result.bool(songsService.save(songs));
    }

}

