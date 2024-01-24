package com.rk.service.orders;

import com.rk.dto.request.orders.OrderRequest1DTO;
import com.rk.dto.request.orders.OrdersRequestDTO;
import com.rk.dto.response.orders.OrderResponseDTO;
import com.rk.exception.CustomException;
import com.rk.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderResponseDTO>findAll();
    List<OrderResponseDTO>searchByWareHouseId(String warehouseCode);
    List<OrderResponseDTO>searchByPhoneNumber(String phoneNumber);
    List<OrderResponseDTO> filterByStatus(Integer status);
    List<OrderResponseDTO> filterByWarehouseCode(String warehouseCode);
    Page<OrderResponseDTO>paginate(Pageable pageable);
    OrderResponseDTO createOrder(OrdersRequestDTO orderRequestDTO, Long userId) throws CustomException;
    Optional<Orders> getOrderById(String orderId);

    void saveUpdate(Long userId) throws CustomException;

    List<Orders>findTop100ByStatusOrderByCreatedAtAsc(Long userId) throws CustomException;

    void confirmDelivery(String orderId, Integer isSuccessful, Long reason) throws CustomException;
    void handleReturns(Long userId);
}
