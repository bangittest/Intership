package com.rk.dto.response.orders;

import com.rk.model.Orders;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdersResponseDTO {
    private String orderCode;
    private Date createdAt;
    private Integer status;
    private String wareHoseCode;
    private String wareHouseName;
    private String warehouseAddress;
    private Date dateStorage;
    private String supplierName;
    private String supplierAddress;
    private String supplierPhoneNumber;
    private String supplierEmail;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhoneNumber;
    private String receiverEmail;
    private Date historyDate;
    private Set<Integer> statusHistory;
    private String failure;

    public OrdersResponseDTO(Orders orders) {
        this.orderCode = orders.getId();
        this.createdAt = orders.getCreatedAt();
        this.status = orders.getStatus();
        this.wareHoseCode = orders.getWarehouse().getId();
        this.wareHouseName = orders.getWarehouse().getWarehouseName();
        this.warehouseAddress = orders.getWarehouse().getAddress();
        this.dateStorage = orders.getStoredAt();
        this.supplierName = orders.getSupplier().getSupplierName();
        this.supplierAddress = orders.getSupplier().getAddress();
        this.supplierPhoneNumber = orders.getSupplier().getPhoneNumber();
        this.supplierEmail = orders.getSupplier().getEmail();
        this.receiverName = orders.getReceiver().getReceiverName();
        this.receiverAddress = orders.getReceiver().getAddress();
        this.receiverPhoneNumber = orders.getReceiver().getPhoneNumber();
        this.receiverEmail = orders.getReceiver().getEmail();
        this.historyDate = (Date) orders.getHistories().stream().map(i -> i.getUserTime()).collect(Collectors.toList());
        this.statusHistory = orders.getHistories().stream().map(i -> i.getStatus()).collect(Collectors.toSet());
        this.failure = failure;
    }
}
