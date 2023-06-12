package com.beiyuan.music.controller;


import com.beiyuan.music.common.result.Result;

import com.beiyuan.music.entity.Singer;
import com.beiyuan.music.entity.Song;
import com.beiyuan.music.service.QCloudCOSService;
import com.beiyuan.music.service.SingerService;
import com.beiyuan.music.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: beiyuan
 * @date: 2022/11/9  21:24
 */
@Api(tags = "腾讯对象存储")
@RestController
@RequestMapping("/cos")
public class QCloudCOSController {
    @Autowired
    QCloudCOSService QCloudCOSService;
    /**
     * 文件上传
     */
    @Autowired
    SingerService singerService;

    @Autowired
    SongService songService;

    @ApiOperation(value = "歌手头像上传或更新")
    @PostMapping("uploadSingerAvator/{id}")
    public Result uploadSingerAvator(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id) {

        //删除云端旧的
        Singer singer=singerService.getById(id);
        if(singer.getAvator()!=null){
            QCloudCOSService.delete(singer.getAvator());
        }
        //上传新的
        String uploadUrl = QCloudCOSService.upload(file,"singer");
        //更新数据库
        singerService.updateAvator(uploadUrl,id);
        return Result.ok(uploadUrl).message("文件上传成功");
    }

//    @DeleteMapping("deleteSingerAvator")
//    public Result delete(String avatorUrl){
//        //https://music-1309913128.cos.ap-nanjing.myqcloud.com/2022/12/30/399c708fcc454fc0aab54faa66ffa96d24011.jpg
//        Boolean res=QCloudCOSService.delete(avatorUrl);
//        return Result.bool(res);
//    }

    @ApiOperation(value = "歌曲封面更新")
    @PostMapping("uploadSongCover/{id}")
    public Result uploadSongCover(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id) {

        //删除云端旧的
        Song song=songService.getById(id);
        if(song.getCover()!=null){
            QCloudCOSService.delete(song.getCover());
        }
        //上传新的
        String uploadUrl = QCloudCOSService.upload(file,"SongCover");
        //更新数据库
        songService.updateCover(uploadUrl,id);
        return Result.ok(uploadUrl).message("文件上传成功");
    }

    @ApiOperation(value = "歌曲资源更新")
    @PostMapping("uploadSongUrl/{id}")
    public Result uploadSongUrl(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @PathVariable Integer id) {

        //删除云端旧的
        Song song=songService.getById(id);
        if(song.getUrl()!=null){
            QCloudCOSService.delete(song.getUrl());
        }
        //上传新的
        String uploadUrl = QCloudCOSService.upload(file,"SongResource");
        //更新数据库
        boolean res=songService.updateUrl(uploadUrl,id);
        return Result.ok(uploadUrl).message("文件上传成功");
    }
}