package com.rk.controller;

import com.rk.dto.request.orders.OrdersRequestDTO;
import com.rk.dto.response.orders.OrderResponseDTO;
import com.rk.exception.CustomException;
import com.rk.exception.WareHouseException;
import com.rk.model.Orders;
import com.rk.model.Warehouse;
import com.rk.security.principle.UserDetailService;
import com.rk.service.orders.OrderService;
import com.rk.service.warehouse.WareHouseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private WareHouseService wareHouseService;
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

    @GetMapping("/orders/filterByStatus")
    public ResponseEntity<?> filterByStatus(@RequestParam(name = "status") Integer status) {
        try {
            List<OrderResponseDTO> result = orderService.filterByStatus(status);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/orders/filterByWarehouseCode")
    public ResponseEntity<?> filterByWarehouseCode(@RequestParam(name = "warehouseCode",defaultValue = "",required = false) String warehouseCode) {
        try {
            List<OrderResponseDTO> result = orderService.filterByWarehouseCode(warehouseCode);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/orders/page")
    public ResponseEntity<?> paginate(
            @RequestParam(name = "limit",defaultValue = "10") String limit,
            @RequestParam(name = "page",defaultValue = "0") String noPage) {
        try {
            Integer limit1 = Integer.valueOf(limit);
            Integer noPage1 = Integer.valueOf(noPage);
            Pageable pageable = PageRequest.of(noPage1, limit1);

            Page<OrderResponseDTO> page = orderService.paginate(pageable);

            return new ResponseEntity<>(page, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/orders/search/phone")
    public ResponseEntity<?> searchByPhoneNumber(@RequestParam(name = "phoneNumber",required = false,defaultValue = "") String phoneNumber) {
        try {
            List<OrderResponseDTO> result = orderService.searchByPhoneNumber(phoneNumber);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/orders")
    public ResponseEntity<?>createOrder (@Valid @RequestBody OrdersRequestDTO ordersRequestDTO, Authentication authentication) throws CustomException {
        try {
            Long userId=userDetailService.getUserIdFromAuthentication(authentication);
            OrderResponseDTO orderResponseDTO=orderService.createOrder(ordersRequestDTO,userId);
            return new ResponseEntity<>(orderResponseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/order/satusssssssssss")
//    @Scheduled(fixedRate = 5000)
    public ResponseEntity<?>updateStatus(Authentication authentication) throws CustomException {
        Long userId=userDetailService.getUserIdFromAuthentication(authentication);
        orderService.saveUpdate(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/order-status")
    public ResponseEntity<?>updateStatusss(Authentication authentication) throws CustomException {
        System.out.println("chaj");
        Long userId=userDetailService.getUserIdFromAuthentication(authentication);
        orderService.findTop100ByStatusOrderByCreatedAtAsc(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirmDelivery")
    public ResponseEntity<String> confirmDelivery(
            @RequestParam String orderId,
            @RequestParam Integer isSuccessful,
            @RequestParam Long reasonId) {
        try {
            orderService.confirmDelivery(orderId, isSuccessful, reasonId);
            return ResponseEntity.ok("Delivery confirmation successful.");
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
