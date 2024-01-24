package com.rk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//bên nhận hàng
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(columnDefinition = "varchar(50)")
    private String receiverName ;
    private String address;
    @Column(columnDefinition = "varchar(11)")
    private String phoneNumber;
    @Column(columnDefinition = "varchar(50)")
    private String email;
    //vĩ độ địa chỉ kho
//    @Column(columnDefinition = "decimal(13,10)")
    private Double latitude;
    //kinh độ địa chỉ kho
//    @Column(columnDefinition = "decimal(13,10)")
    private Double longitude;
    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    Set<Orders>orders;
}
