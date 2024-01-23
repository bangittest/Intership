package com.rk.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long HistoryId;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date userTime;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "orders_id",referencedColumnName = "orderCode")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "warehouse_id",referencedColumnName = "warehouseCode")
    private Warehouse warehouse;
    private Integer status;
}
