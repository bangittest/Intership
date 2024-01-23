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
    private Integer receiverId ;
    private String receiverName ;
    private String address;
    private String phoneNumber;
    private String email;
    //vĩ độ địa chỉ kho
    private String latitude;
    //kinh độ địa chỉ kho
    private String longitude;
    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    Set<Orders>orders;
}
