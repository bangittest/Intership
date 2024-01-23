package com.rk.repository;

import com.rk.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {
    List<Orders>findAllByIdContainsIgnoreCase(String warehouseCode);
//    List<Orders>findAllBySupplier_PhoneNumberAndReceiver_PhoneNumber(Integer phoneNumber);
}
