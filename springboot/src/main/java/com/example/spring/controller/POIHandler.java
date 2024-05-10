package com.example.spring.controller;

import com.example.spring.entity.nearby;
import com.example.spring.entity.POIView;
import com.example.spring.entity.POIjson;
import com.example.spring.repository.NearbyRepository;
import com.example.spring.repository.POIRepository;
import com.example.spring.repository.POIjsonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/POI")
public class POIHandler {
    @Autowired
    private POIRepository poiRepository;
    @Autowired
    private NearbyRepository nearbyRepository;
    @Autowired
    private POIjsonRepository poi;

    @GetMapping("/KNN/{id}/{POItype}")
    public List<POIjson> findKNN(@PathVariable("id") Integer id,
                                 @PathVariable("POItype") String poitype) {
        System.out.println("id: " + id);
        System.out.println("Poitype: " + poitype);
        nearby nearbypoi = nearbyRepository.findBySidEquals(id);
        String s = nearbypoi.get位置();
        s = s.replaceAll("[a-zA-Z]", "");
        s = s.replaceAll("\\(|\\)", "");
        String[] strings = s.split(" ");
        double x = Double.parseDouble(strings[0]);
        double y = Double.parseDouble(strings[1]);

        List<POIView> list = poiRepository.findKNNPark(x, y,"%"+poitype+"%");
        System.out.println(list.size());
        List<POIjson> list1 = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            POIjson poIjson = poi.findByIdEquals(list.get(i).getId());
            list1.add(poIjson);
        }
        System.out.println(list1.size());
        return list1;
    }
}
