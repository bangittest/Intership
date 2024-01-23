package com.rk.service.warehouse;

import com.rk.dto.request.warehouse.WareHoseRequestDTO;
import com.rk.dto.response.warehouse.WareHoseResponseDTO;
import com.rk.model.Warehouse;
import com.rk.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Override
    public WareHoseResponseDTO save(WareHoseRequestDTO wareHoseRequestDTO) {
        for (Warehouse warehouse:wareHoseRequestDTO.getWarehouseList()) {
            Warehouse warehouse1=new Warehouse();
        }
        return null;
    }
}
