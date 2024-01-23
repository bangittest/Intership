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
//nhà cung cấp
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(50)")
    private String supplierName;
    private String address;
    @Column(columnDefinition = "varchar(11)")
    private String phoneNumber;
    @Column(columnDefinition = "varchar(50)")
    private String email;
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")
    Set<Orders> orders;
}
