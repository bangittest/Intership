package com.rk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    private String orderCode;
//    //ngày tạo hàng
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date orderCreated;
    @Column(columnDefinition = "Integer default 0")
    private Integer status =0;
    @ManyToOne
    @JoinColumn(name = "warehouse_code",referencedColumnName = "warehouseCode")
    private Warehouse warehouse;
    @ManyToOne
    @JoinColumn(name = "supplier_id",referencedColumnName = "supplierId")
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "receiver_id",referencedColumnName = "receiverId")
    private Receiver receiver;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //đơn đặt hàng
    private Date orderStored;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //giờ giao hàng
    private Date orderDelivered;
    //Số lần giao hàng thất bại
    private Integer numberFailures;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //Ngày giờ hoàn hàng
    private Date orderReturnCause;

    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "order_reason",
            joinColumns =@JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "reason_id"))
    private Set<Reason>reasons;
    @JsonIgnore
    @OneToMany(mappedBy = "orders")
    Set<History> histories;

}
