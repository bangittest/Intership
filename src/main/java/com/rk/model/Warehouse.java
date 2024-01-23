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
    @Column(columnDefinition = "varchar(20)")
    private String id;
    @Column(columnDefinition = "varchar(50)")
    private String warehouseName;
    private String address;
    //vĩ độ địa chỉ kho
    @Column(columnDefinition = "decimal(18,15)")
    private Float latitude;
    //kinh độ địa chỉ kho
    @Column(columnDefinition = "decimal(18,15)")
    private String longitude;
    //sức chứa của kho
    @Column(columnDefinition = "integer(3)")
    private Integer capacity;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    Set<User>users;
    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    Set<History> histories;
}
