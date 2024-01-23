package com.rk.service.orders;

import com.rk.dto.response.orders.OrderResponseDTO;
import com.rk.model.Orders;
import com.rk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<OrderResponseDTO> findAll() {
        List<Orders>list=orderRepository.findAll();
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public List<OrderResponseDTO> searchByWareHouseId(String warehouseCode) {
        List<Orders>list=orderRepository.findAllByIdContainsIgnoreCase(warehouseCode);
        return list.stream().map(OrderResponseDTO::new).toList();
    }

    @Override
    public List<OrderResponseDTO> findAllByWarehouseAndSupplier_PhoneNumberAndReceiver_PhoneNumberContains(Integer phoneNumber) {
//               List<Orders>list=orderRepository.findAllBySupplier_PhoneNumberAndReceiver_PhoneNumber(phoneNumber);
        return null;
    }


}
