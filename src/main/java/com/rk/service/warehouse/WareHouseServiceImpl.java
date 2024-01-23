package com.rk.service.warehouse;

import com.rk.dto.request.warehouse.WareHouseRequestDTO;
import com.rk.dto.response.warehouse.WareHouseResponseDTO;
import com.rk.model.Warehouse;
import com.rk.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WareHouseServiceImpl implements WareHouseService {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    public List<WareHouseResponseDTO> findAll() {
        List<Warehouse>list=warehouseRepository.findAll();
        return list.stream().map(WareHouseResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<WareHouseResponseDTO> save(List<Warehouse>list) {
        List<Warehouse>warehouseArrayList=new ArrayList<>();
        for (Warehouse warehouse:list) {
            Warehouse warehouseNew=new Warehouse();
            warehouseNew.setId(warehouse.getId());
            warehouseNew.setWarehouseName(warehouse.getWarehouseName());
            warehouseNew.setAddress(warehouse.getAddress());
            warehouseNew.setLatitude(warehouse.getLatitude());
            warehouseNew.setLongitude(warehouse.getLongitude());
            warehouseNew.setCapacity(warehouse.getCapacity());
            warehouseRepository.save(warehouseNew);
            warehouseArrayList.add(warehouseNew);
        }
        return warehouseArrayList.stream().map(WareHouseResponseDTO::new).collect(Collectors.toList());
    }

//    @Override
//    public WareHouseResponseDTO create(WareHouseRequestDTO wareHouseRequestDTO) {
//        Warehouse warehouse=new Warehouse();
//        warehouse.setId(wareHouseRequestDTO.getWarehouseId());
//        warehouse.setWarehouseName(wareHouseRequestDTO.getWarehouseName());
//        warehouse.setAddress(wareHouseRequestDTO.getAddress());
//        warehouse.setLatitude(wareHouseRequestDTO.getLatitude());
//        warehouse.setLongitude(wareHouseRequestDTO.getLongitude());
//        warehouse.setCapacity(wareHouseRequestDTO.getCapacity());
//        warehouseRepository.save(warehouse);
//        return new WareHouseResponseDTO(warehouse);
//    }
}
