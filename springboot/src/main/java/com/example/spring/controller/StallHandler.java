package com.example.spring.controller;



import com.example.spring.entity.*;
import com.example.spring.repository.ApartRepository;
import com.example.spring.repository.StallRepository;
import com.example.spring.repository.LookRecordsRepository;
import com.example.spring.repository.stall_geoRepository;
import com.example.spring.repository.NearbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Component
@RestController
@RequestMapping("/Stall")
public class StallHandler {
    @Autowired
    private StallRepository stallRepository;
    @Autowired
    private LookRecordsRepository lookRecordsRepository;

    @Autowired
    private ApartRepository apartRepository;
    @Autowired
    private stall_geoRepository geoRepository;
    @Autowired
    private NearbyRepository nearbyRepository;

    @GetMapping("/findAll/{page}/{size}")
    public Page<Stall> findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return stallRepository.findAll(pageable);
    }

    @GetMapping("/gradelist/{page}/{size}")
    public Page<Stall> getAllStallsSortedByGrade(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return stallRepository.findAllByOrderBy评分Desc(pageable);
    }

    @GetMapping("/pricelist/{page}/{size}")
    public Page<Stall> getAllStallsSortedByPrice(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return stallRepository.findAllByOrderBy租金Asc(pageable);
    }

    @GetMapping("/reserlist/{page}/{size}")
    public Page<Stall> getAllStallsSortedByReser(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return stallRepository.findAllStallsSortedByReser(pageable);
    }

    @GetMapping("/recordlist/{page}/{size}")
    public Page<Stall> getAllStallsSortedByRecord(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return stallRepository.findAllStallsSortedByRecord(pageable);
    }


    @PostMapping("/saveall")
    public String saveall(@RequestBody Stall stall) {
        Stall result;
        Apart apart=apartRepository.findBy小区(stall.get小区());
        if(apart==null) {
            return "error";
        }
        else {
            result = stallRepository.save(stall);
            //车位坐标信息表
            stall_geo geo=new stall_geo();
            geo.setSid(stall.getId());
            geo.set小区(stall.get小区());
            geo.setX(apart.getX());
            geo.setY(apart.getY());
            geoRepository.save(geo);

            nearby ne=new nearby();
            ne.setSid(stall.getId());
            ne.set小区(stall.get小区());
            ne.set位置(apart.get位置());
            nearbyRepository.save(ne);
            //浏览次数表
            Lookrecords lookrecords=new Lookrecords();
            lookrecords.setId(stall.getId());
            lookrecords.setRecordtimes(0);
            lookrecords.setResertimes(0);
            lookRecordsRepository.save(lookrecords);
        }

        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }

    @PostMapping("/save")
    public String save(@RequestBody Stall stall) {
        Stall result = stallRepository.save(stall);
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }


    @GetMapping("/findById/{id}")
    public Stall findById(@PathVariable("id") Integer id) {
        //Lookrecords lookrecords = lookRecordsRepository.findById(id).get();
        //Stall result3=stallRepository.findById(id).get();
        if(lookRecordsRepository.findById(id).orElse(null)!=null&&stallRepository.findById(id).orElse(null)!=null){
            Lookrecords lookrecords = lookRecordsRepository.findById(id).get();
            Stall result3=stallRepository.findById(id).get();
            lookrecords.setRecordtimes(lookrecords.getRecordtimes() + 1);
            lookRecordsRepository.save(lookrecords);
            return result3;
        }
        else {
            return null;
        }
    }


    @PostMapping("/update")
    public String update(@RequestBody Stall stall) {
        Stall result;
                //stallRepository.findById(stall.getId()).get();
        Apart apart=apartRepository.findBy小区(stall.get小区());
        if(apart==null) {
            return "error";
        }
        else {
            result=stallRepository.save(stall);
            //车位坐标信息表
            stall_geo geo=geoRepository.findBySid(stall.getId());
            geo.set小区(stall.get小区());
            geo.setX(apart.getX());
            geo.setY(apart.getY());
            geoRepository.save(geo);
            //地理位置信息表
            nearby ne=nearbyRepository.findBySidEquals(stall.getId());
            ne.set小区(stall.get小区());
            ne.set位置(apart.get位置());
            nearbyRepository.save(ne);
        }
        if (result != null) {
            return "success";
        } else {
            return "error";
        }
    }


    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        stallRepository.deleteById(id);
    }

    @GetMapping("/findByName/{name}")
    public List<Stall> searchName(@PathVariable("name") String name) {
        return stallRepository.findBy小区ContainingAnd当前状态Containing(name,"空闲中");
    }

    /**
     * 多条件查找符合条件车位，如果进行但条件查询，则前端选择不限即可（后端对应特殊的标识符）
     * @param name 车位所在小区名
     * @param stallstate 车位状态
     * @param stallPrice 车位价格
     * @param time 可用时间段
     * @param grade 评分
     * @return 符合条件的车位
     */
    @GetMapping("/findByNameType/{name}/{stallstate}/{stallPrice}/{time}/{grade}")
    public List<Stall> searchName(@PathVariable("name") String name,
                                  @PathVariable("stallstate") String stallstate,
                                  @PathVariable("stallPrice") String stallPrice,
                                  @PathVariable("time") String time,
                                  @PathVariable("grade") String grade) {
        System.out.println("name: " + name);
        System.out.println("stallstate: " + stallstate);
        System.out.println("stallPrice: " + stallPrice);
        System.out.println("time: " + time);
        System.out.println("grade: " + grade);

        String[] s = stallPrice.split("~");
        String[] s2;


        if (Objects.equals(stallstate, "不限")) {
            stallstate = "";
        }
        if (Objects.equals(time, "不限")) {
            time = "";
        }
        if (Objects.equals(name, "null") || Objects.equals(name, "")) {
            name = "";
        } else {
            name = ""+name+"";
        }
        if(Objects.equals(grade, "不限")) {
            return stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段Containing
                    (name, stallstate, Integer.parseInt(s[0]), Integer.parseInt(s[1]), time);
        }
        else if (Objects.equals(grade, "null")) {
            return stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分IsNull
                    (name, stallstate, Integer.parseInt(s[0]), Integer.parseInt(s[1]), time);
        }
        else {
            s2 = grade.split("~");
            return stallRepository.findBy小区ContainingAnd当前状态ContainingAnd租金BetweenAnd可用时间段ContainingAnd评分Between
                    (name, stallstate, Integer.parseInt(s[0]), Integer.parseInt(s[1]), time, Double.parseDouble(s2[0]),Double.parseDouble(s2[1]));
        }
    }

    @PostMapping("/upgrade/{id}/{grade}")
    public String upgrade(@PathVariable("id") Integer id,
                         @PathVariable("grade") String grade) {
        Stall stall=stallRepository.findById(id).get();
        double grade1=Double.parseDouble(grade);
        if(stall.get评分()!=null){
            stall.set评分((stall.get评分()+grade1)/2);
        }
        else{
            stall.set评分(grade1);
        }
        Stall result1 = stallRepository.save(stall);
        if (result1 != null) {
            return "success";
        } else {
            return "error";
        }
    }

    @Value("${file.upload-path}")
    private String pictureurl;
    //注入业务逻辑层
    //单文件上传
    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File saveFile = new File(pictureurl);
        if (!saveFile.exists()) {
            //若不存在该目录，则创建目录
            saveFile.mkdir();
        }
        try {
            //将文件保存指定目录
            file.transferTo(new File(pictureurl + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(url);
        String imagePath = "http://localhost:8282/files/" + fileName;
        return imagePath;
    }
};
