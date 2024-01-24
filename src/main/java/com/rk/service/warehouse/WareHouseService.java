package com.rk.service.warehouse;

import com.rk.dto.response.warehouse.WareHouseResponseDTO;
import com.rk.exception.CustomException;
import com.rk.exception.WareHouseException;
import com.rk.model.Warehouse;

import java.util.List;

public interface WareHouseService {
    List<WareHouseResponseDTO>findAll();
    List<WareHouseResponseDTO> save(List<Warehouse>list);
    Warehouse findById(String id) throws WareHouseException;
    void updateAvailability(Warehouse warehouse) throws CustomException;
    int getAvailability(String warehouseId) throws CustomException;
//    WareHouseResponseDTO create(WareHouseRequestDTO wareHouseRequestDTO);
}
