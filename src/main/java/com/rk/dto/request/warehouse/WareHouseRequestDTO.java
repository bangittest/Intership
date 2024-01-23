package com.rk.dto.request.warehouse;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WareHouseRequestDTO {
    private String warehouseCode;
    private String warehouseName;
    private String address;
    //vĩ độ địa chỉ kho
    private String latitude;
    //kinh độ địa chỉ kho
    private String longitude;
    //sức chứa của kho
    private Integer capacity;
}
