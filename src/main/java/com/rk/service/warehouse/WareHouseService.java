package com.rk.service.warehouse;

import com.rk.dto.request.warehouse.WareHoseRequestDTO;
import com.rk.dto.response.warehouse.WareHoseResponseDTO;
import com.rk.model.Warehouse;

public interface WareHouseService {
    WareHoseResponseDTO save(WareHoseRequestDTO wareHoseRequestDTO);
}
