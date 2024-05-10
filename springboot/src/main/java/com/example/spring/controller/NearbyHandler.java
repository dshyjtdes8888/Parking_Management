package com.example.spring.controller;

import com.example.spring.entity.POIView;
import com.example.spring.entity.stall_geo;
import com.example.spring.entity.Stall;
import com.example.spring.repository.NearbyRepository;
import com.example.spring.repository.StallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring.entity.nearby;

import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@RequestMapping("/Mapping")
public class NearbyHandler {
    @Autowired
    private NearbyRepository nearbyRepository;

    @GetMapping("/MarkPoint")
    public List<stall_geo> findAll() {
        List<nearby> list = nearbyRepository.findAll();
        List<stall_geo> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            stall_geo tmp = new stall_geo();
            tmp.setSid(list.get(i).getSid());
            tmp.setNid(list.get(i).getNid());
            tmp.set小区(list.get(i).get小区());
            String s = list.get(i).get位置();
            s = s.replaceAll("[a-zA-Z]", "");
            s = s.replaceAll("\\(|\\)", "");
            String[] strings = s.split(" ");
            tmp.setX(Double.parseDouble(strings[0]));
            tmp.setY(Double.parseDouble(strings[1]));
            list1.add(tmp);
        }
        return list1;
    }

    @GetMapping("/CurrentMapping/{id}")
    public stall_geo findByid(@PathVariable Integer id) {
         nearby near = nearbyRepository.findById(id).get();
         stall_geo tmp = new stall_geo();
         tmp.setSid(near.getSid());
         tmp.setNid(near.getNid());
         tmp.set小区(near.get小区());
         String s = near.get位置();
         s = s.replaceAll("[a-zA-Z]", "");
         s = s.replaceAll("\\(|\\)", "");
         String[] strings = s.split(" ");
         tmp.setX(Double.parseDouble(strings[0]));
         tmp.setY(Double.parseDouble(strings[1]));
        return tmp;
    }

    @Autowired
   private StallRepository stallRepository;

    @GetMapping("/NearStall/{id}")
    public List<Stall> findKNNStall(@PathVariable Integer id) {
        nearby ne = nearbyRepository.findById(id).get();
        String s = ne.get位置();
        s = s.replaceAll("[a-zA-Z]", "");
        s = s.replaceAll("\\(|\\)", "");
        String[] strings = s.split(" ");

        List<nearby> list = nearbyRepository.findKNNStall(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
        List<Stall> list1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            list1.add(stallRepository.findById(list.get(i).getSid()).get());
        }
        List<Stall> list2 = stallRepository.findBy小区ContainingAnd租金EqualsAnd可用时间段Containing
                (stallRepository.findById(id).get().get小区(), stallRepository.findById(id).get().get租金(), stallRepository.findById(id).get().get可用时间段());
        for (int i = 0; i < list2.size(); i++) {
            if (!list1.contains(list2.get(i))) {
                list1.add(list2.get(i));
            }
        }
        return list1;
    }
}
