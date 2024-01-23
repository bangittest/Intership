package com.rk.controller;

import com.rk.dto.response.orders.OrderResponseDTO;
import com.rk.service.orders.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/orders")
    public ResponseEntity<?>findAllOrder(){
        List<OrderResponseDTO>list=orderService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/orders/search")
    public ResponseEntity<?>search(@RequestParam(name = "search",required = false,defaultValue = "") String search){
        List<OrderResponseDTO>list=orderService.searchByWareHouseId(search);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
//    @GetMapping("/orders/search/phoneNumber")
//    public ResponseEntity<?>searchByPhoneNumber(@RequestParam (name = "phoneNumber" ,required = false,defaultValue = "")String phoneNumber ){
//        try {
//            Integer phone= Integer.valueOf(phoneNumber);
//            List<OrderResponseDTO>list=orderService.findAllByWarehouseAndSupplier_PhoneNumberAndReceiver_PhoneNumberContains(phone);
//            return new ResponseEntity<>(list,HttpStatus.OK);
//        }catch (NumberFormatException e){
//            return new ResponseEntity<>("loi",HttpStatus.BAD_REQUEST);
//        }
//    }
}
