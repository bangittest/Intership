package com.rk.dto.response.warehouse;

import com.rk.model.Warehouse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WareHouseResponseDTO {
    private String warehouseId;
    private String warehouseName;
    private String address;
    //vĩ độ địa chỉ kho
    private Double latitude;
    //kinh độ địa chỉ kho
    private Double longitude;
    //sức chứa của kho
    private Integer capacity;

    public WareHouseResponseDTO(Warehouse warehouse) {
        this.warehouseId = warehouse.getId();
        this.warehouseName = warehouse.getWarehouseName();
        this.address = warehouse.getAddress();
        this.latitude = warehouse.getLatitude();
        this.longitude = warehouse.getLongitude();
        this.capacity = warehouse.getCapacity();
    }

}
