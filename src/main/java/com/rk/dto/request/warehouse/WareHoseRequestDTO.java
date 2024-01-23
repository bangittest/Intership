package com.rk.dto.request.warehouse;

import com.rk.model.Warehouse;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WareHoseRequestDTO {
    List<Warehouse>warehouseList;
}
