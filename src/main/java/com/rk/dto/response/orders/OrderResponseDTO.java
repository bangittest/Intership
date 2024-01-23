package com.rk.dto.response.orders;

import com.rk.model.Orders;
import lombok.*;

import java.util.Date;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponseDTO {
    private String orderCode;
    //    //ngày tạo hàng
    private Date orderCreated;
    private Integer status ;
    private String warehouseId;
    private String warehouseName;
    //bên nhận hàng
    private String receiverName ;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String receiverEmail;
    //đơn đặt hàng
    private String supplierName;
    private String supplierAddress;
    private String supplierPhoneNumber;
    private String supplierEmail;
    private Date orderStored;
    //giờ giao hàng
    private Date orderDelivered;
    //Số lần giao hàng thất bại
    private Integer numberFailures;
    //Ngày giờ hoàn hàng
    private Date orderReturnCause;


    public OrderResponseDTO(Orders orders) {
        this.orderCode = orders.getId();
//        this.orderCreated = orders.getOrderCreated();
        this.status = orders.getStatus();
        this.warehouseId = String.valueOf(orders.getHistories().stream().map(history -> history.getWarehouse().getId()).collect(Collectors.toList()));
        this.warehouseName = String.valueOf(orders.getHistories().stream().map(history -> history.getWarehouse().getWarehouseName()).collect(Collectors.toList()));
        this.receiverName = orders.getReceiver().getReceiverName();
        this.receiverAddress = orders.getReceiver().getAddress();
        this.receiverPhoneNumber = orders.getReceiver().getPhoneNumber();
        this.receiverEmail = orders.getReceiver().getEmail();
        this.supplierName = orders.getSupplier().getSupplierName();
        this.supplierAddress = orders.getSupplier().getAddress();
        this.supplierPhoneNumber = orders.getSupplier().getPhoneNumber();
        this.supplierEmail = orders.getSupplier().getEmail();
//        this.orderStored = orders.getOrderStored();
//        this.orderDelivered = orders.getOrderDelivered();
        this.numberFailures = orders.getNumberFailures();
//        this.orderReturnCause = orders.getOrderReturnCause();
    }
}
