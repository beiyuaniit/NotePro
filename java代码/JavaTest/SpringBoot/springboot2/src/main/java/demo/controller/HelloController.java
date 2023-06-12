package demo.controller;

import demo.bean.Car;
import demo.bean.Person;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @author: beiyuan
 * @className: HelloController
 * @date: 2022/7/16  0:03
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private Car car;

    @Autowired
    Person person;

    Counter counter;
    public HelloController(MeterRegistry metricReg){
        counter=metricReg.counter("car.visit.count");
    }

    @RequestMapping("/hello")
    public String handle01(){
        return "Hello World";
    }

    @RequestMapping("/car")
    public Car car(){
        log.info("car...");
        counter.increment();
        return car;
    }

    @RequestMapping(value = "/upload")
    public String upload(
              MultipartFile headerImg
    ) throws IOException {
        if(!headerImg.isEmpty()){
            String originalFilename=headerImg.getOriginalFilename();
            headerImg.transferTo(new File("C:\\Users\\beilinanju\\Desktop\\"+originalFilename));
        }
        return "success";
    }

    @RequestMapping("/person")
    public Person person(){
        return person;
    }


    @RequestMapping("/testUp")
    public String testUp(MultipartFile photo, HttpSession session) throws IOException {
        //获取上传的文件的文件名
        String fileName = photo.getOriginalFilename();
        //处理文件重名问题
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString().replaceAll("-","") + hzName;
        //获取服务器中photo目录的路径
        ServletContext servletContext = session.getServletContext();
        String photoPath = servletContext.getRealPath("/photo");
        File file = new File(photoPath);
        if(!file.exists()){
            file.mkdir();
        }
        String finalPath = photoPath + File.separator + fileName;
        //实现上传功能。意为将浏览器的文件转移到服务器，先读再写
        photo.transferTo(new File(finalPath));
        return "success";
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/temp")
    public String template(){
        Long aLong = jdbcTemplate.queryForObject("select count(*) from st", Long.class);
        log.info("记录总数：{}",aLong);
        System.out.println(jdbcTemplate==null);
        return "success";
    }
    @Autowired
    DataSource dataSource;

    @RequestMapping("/druid")
    public String druid(){
        log.info(dataSource.getClass()+" ");
        return "success";
    }

    @Value("${info.appName}")
    private String val;

    @RequestMapping("/val")
    public String getVal(){
        return val;
    }
}
