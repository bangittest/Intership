package com.rk.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String password;
    private String token;
    @Column(columnDefinition = "boolean default true")
    private Boolean status=true;
    @ManyToMany(fetch =FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns =@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role>roles;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "warehouse_id",referencedColumnName = "warehouseCode")
    private Warehouse warehouse;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<History>histories;

}
