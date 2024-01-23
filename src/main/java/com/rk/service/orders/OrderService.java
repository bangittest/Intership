package com.rk.service.orders;

import com.rk.dto.response.orders.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO>findAll();
    List<OrderResponseDTO>searchByWareHouseId(String warehouseCode);
    List<OrderResponseDTO>findAllByWarehouseAndSupplier_PhoneNumberAndReceiver_PhoneNumberContains(Integer phoneNumber);
}
