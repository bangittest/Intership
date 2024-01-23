package com.rk.controller;

import com.rk.dto.request.warehouse.WareHouseRequestDTO;
import com.rk.dto.response.warehouse.WareHouseResponseDTO;
import com.rk.model.Warehouse;
import com.rk.service.warehouse.WareHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;
    @PostMapping("/warehouse")
    public ResponseEntity<?>create(@RequestBody List<Warehouse>list){
        List<WareHouseResponseDTO>dtoList= wareHouseService.save(list);
        return new ResponseEntity<>(dtoList,HttpStatus.CREATED);
    }
    @GetMapping("/warehouse")
    public ResponseEntity<?>findAll(){
        return new ResponseEntity<>(wareHouseService.findAll(),HttpStatus.OK);
    }

}
