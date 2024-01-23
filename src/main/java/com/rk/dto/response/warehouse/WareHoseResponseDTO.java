package com.rk.dto.response.warehouse;

import com.rk.model.Warehouse;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WareHoseResponseDTO {
    private String warehouseCode;
    private String warehouseName;
    private String address;
    //vĩ độ địa chỉ kho
    private String latitude;
    //kinh độ địa chỉ kho
    private String longitude;
    //sức chứa của kho
    private Integer capacity;

    public WareHoseResponseDTO(Warehouse warehouse) {
        this.warehouseCode = warehouse.getWarehouseCode();
        this.warehouseName = warehouse.getWarehouseName();
        this.address = warehouse.getAddress();
        this.latitude = warehouse.getLatitude();
        this.longitude = warehouse.getLongitude();
        this.capacity = warehouse.getCapacity();
    }
}
