package com.rk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Orders {
    @Id
    private String id;
//    //ngày tạo hàng
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;
    @Column(columnDefinition = "Integer(1) default 0")
    private Integer status =0;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id",referencedColumnName = "id")
    private Supplier supplier;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id",referencedColumnName = "id")
    private Receiver receiver;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //đơn đặt hàng
    private Date storedAt;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //giờ giao hàng
    private Date deliveredAt;
    //Số lần giao hàng thất bại
    @Column(columnDefinition = "Integer(1) default 0")
    private Integer numberFailures;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    //Ngày giờ hoàn hàng
    private Date returnCause;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id",referencedColumnName = "id")
    private Warehouse warehouse;
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "order_reason",
            joinColumns =@JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "reason_id"))
    private Set<Reason>reasons;
    @JsonIgnore
    @OneToMany(mappedBy = "orders")
    Set<History> histories=new HashSet<>();

}
