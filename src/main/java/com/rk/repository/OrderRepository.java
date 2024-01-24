package com.rk.repository;

import com.rk.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String> {
    List<Orders>findAllByIdContainsIgnoreCase(String warehouseCode);
    List<Orders> findByStatus(Integer status);
    List<Orders> findByHistories_Warehouse_Id(String id);
    List<Orders> findByReceiverPhoneNumberContainingOrSupplierPhoneNumberContaining(String receiverPhoneNumber, String supplierPhoneNumber);
    List<Orders>findAllByStatusFalseOrderByCreatedAtAsc();
//    List<Orders> findTop100ByStatusOrderByCreatedAtAsc(Integer status);

    @Query("SELECT o FROM Orders o WHERE o.status = :status ORDER BY o.createdAt ASC")
    List<Orders> findTop100ByStatusOrderByCreatedAtAsc(@Param("status") Integer status);
    @Transactional
    @Query(value = "SELECT MAX(CAST(SUBSTRING(o.id, 11) AS Long)) FROM Orders o WHERE SUBSTRING(o.id, 4, 6) = :datePart")
    Long findMaxIdByDate(@Param("datePart") String datePart);

}
