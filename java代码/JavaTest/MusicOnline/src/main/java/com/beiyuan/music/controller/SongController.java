package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Singer;
import com.beiyuan.music.entity.Song;
import com.beiyuan.music.service.QCloudCOSService;
import com.beiyuan.music.service.SingerService;
import com.beiyuan.music.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 歌曲 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/song")
public class SongController {

    @Autowired
    SongService songService;

    @Autowired
    SingerService singerService;

    @Autowired
    QCloudCOSService qCloudCOSService;

    @GetMapping("getSongList/{page}/{limit}")
    public Result getSongList(
            @PathVariable Long page,
            @PathVariable Long limit) {
        //分页查询。page当前页，0和1都是第一页，limit每页的记录数
        Page<Song> pageParam = new Page<>(page, limit);
        IPage<Song> pageModel = songService.page(pageParam, null);
        List<Song> records = pageModel.getRecords();
        for(Song song:records){
            song.setSingerName(singerService.getById(song.getSingerId()).getName());
        }
        return Result.ok(pageModel);
    }

    @GetMapping("getBySingerId/{id}/{page}/{limit}")
    public Result getBySingerId(@PathVariable Long id,
                                @PathVariable Long page,
                                @PathVariable Long limit){
        Page<Song> pageParam = new Page<>(page, limit);
        QueryWrapper<Song> wrapper=new QueryWrapper();
        wrapper.eq("singer_id",id);
        IPage<Song> pageModel = songService.page(pageParam, wrapper);
        List<Song> records = pageModel.getRecords();
        for(Song song:records){
            song.setSingerName(singerService.getById(song.getSingerId()).getName());
        }

        return Result.ok(pageModel);
    }


    //根据歌名或歌手名模糊查询
    @GetMapping("getSongOfName/{page}/{limit}")
    public Result getSingerOfName(String name,
                                  @PathVariable Long page,
                                  @PathVariable Long limit){
        QueryWrapper<Singer> singerQueryWrapper=new QueryWrapper<>();
        singerQueryWrapper.like("name",name);
        Singer singer=singerService.getOne(singerQueryWrapper);


        QueryWrapper<Song> wrapper=new QueryWrapper();
        wrapper.like("name",name);
        if(singer!=null){
            wrapper.or().like("singer_id",singer.getId());
        }
        Page<Song> pageParam=new Page<>(page,limit);
        IPage<Song> pageModel=songService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @PostMapping("updateSong")
    public Result updateSong(@RequestBody Song song){
        if(song.getSingerName()!=null){
            QueryWrapper<Singer> wrapper=new QueryWrapper<>();
            wrapper.eq("name",song.getSingerName());
            Integer singerId=singerService.getOne(wrapper).getId();
            song.setSingerId(singerId);
        }
        return Result.bool(songService.updateById(song));
    }

    @DeleteMapping("deleteSong/{id}")
    public Result deleteSong(@PathVariable Long id){
        Song song=songService.getById(id);
        qCloudCOSService.delete(song.getCover());
        qCloudCOSService.delete(song.getUrl());
        return Result.bool(songService.removeById(id));
    }

    @PostMapping("addSong")
    public Result addSong(@RequestBody Song song){
        System.out.println(song);
        if(song.getSingerName()==null){
            return Result.fail(null).message("歌手名不能为空");
        }
        QueryWrapper<Singer> wrapper=new QueryWrapper<>();
        wrapper.eq("name",song.getSingerName());
        Singer singer=singerService.getOne(wrapper);
        if(singer==null){
            return Result.fail(null).message("请先添加改歌手");
        }

        song.setSingerId(singer.getId());
        return Result.bool(songService.save(song));
    }


}

