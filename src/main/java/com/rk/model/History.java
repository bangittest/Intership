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
    private Long id;
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date userTime;
    private Long userId;
    @ManyToOne
    @JoinColumn(name = "orders_id",referencedColumnName = "id")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "warehouse_id",referencedColumnName = "id")
    private Warehouse warehouse;
    private Integer status;
}
