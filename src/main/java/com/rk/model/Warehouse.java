package com.rk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//kho
public class Warehouse {
    @Id
    private String warehouseCode;
    private String warehouseName;
    private String address;
    //vĩ độ địa chỉ kho
    private String latitude;
    //kinh độ địa chỉ kho
    private String longitude;
    //sức chứa của kho
    private Integer capacity;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    Set<User>users;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    Set<Orders>orders;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    Set<History> histories;
}
