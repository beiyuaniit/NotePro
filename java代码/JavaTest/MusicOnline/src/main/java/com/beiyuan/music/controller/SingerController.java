package com.beiyuan.music.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beiyuan.music.common.result.Result;
import com.beiyuan.music.entity.Singer;
import com.beiyuan.music.service.QCloudCOSService;
import com.beiyuan.music.service.SingerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 歌手 前端控制器
 * </p>
 *
 * @author beiyuan
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/singer")
public class SingerController {

    @Autowired
    SingerService singerService;

    @Autowired
    QCloudCOSService qCloudCOSService;



    @GetMapping("getAllSinger/{page}/{limit}")
    public Result getSingerList(
            @PathVariable Long page,
            @PathVariable Long limit){
        //分页查询。page当前页，0和1都是第一页，limit每页的记录数
        Page<Singer> pageParam=new Page<>(page,limit);
        IPage<Singer> pageModel=singerService.page(pageParam,null);
        return Result.ok(pageModel);
    }

    //根据歌手名字模糊查询
    @GetMapping("getSingerOfName/{page}/{limit}")
    public Result getSingerOfName(String name,
                                  @PathVariable Long page,
                                  @PathVariable Long limit){
        QueryWrapper<Singer> wrapper=new QueryWrapper();
        wrapper.like("name",name);
        Page<Singer> pageParam=new Page<>(page,limit);
        IPage<Singer> pageModel=singerService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @GetMapping("getSingerBySex/{page}/{limit}")
    public Result getSingerBySex(Boolean sex,
                                 @PathVariable Long page,
                                 @PathVariable Long limit){
        QueryWrapper<Singer> wrapper=new QueryWrapper();
        wrapper.eq("sex",sex);
        Page<Singer> pageParam=new Page<>(page,limit);
        IPage<Singer> pageModel=singerService.page(pageParam,wrapper);
        return Result.ok(pageModel);
    }

    @PostMapping("update")
    public Result updateSinger(@RequestBody Singer singer){
        return Result.bool(singerService.updateById(singer));
    }

    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Integer id){
        String avatorUrl=singerService.getById(id).getAvator();
        if(avatorUrl!=null){
            qCloudCOSService.delete(avatorUrl);
        }
        return Result.bool(singerService.removeById(id));
    }

    @PostMapping("addSinger")
    public Result addSinger(@RequestBody Singer singer){
        System.out.println(singer);
        return Result.bool(singerService.save(singer));
    }
}

