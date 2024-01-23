package com.rk.service.warehouse;

import com.rk.dto.response.warehouse.WareHouseResponseDTO;
import com.rk.model.Warehouse;

import java.util.List;

public interface WareHouseService {
    List<WareHouseResponseDTO>findAll();
    List<WareHouseResponseDTO> save(List<Warehouse>list);
//    WareHouseResponseDTO create(WareHouseRequestDTO wareHouseRequestDTO);
}
