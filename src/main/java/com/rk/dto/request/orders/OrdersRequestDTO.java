package com.rk.dto.request.orders;

import com.rk.dto.request.receiver.ReceiverRequestDTO;
import com.rk.dto.request.supplier.SupplierRequestDTO;
import com.rk.model.Receiver;
import com.rk.model.Supplier;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrdersRequestDTO {
    private String id;

    private Date createdAt;

    @Min(value = 0, message = "Invalid order status")
    private Integer status = 0;

    @Valid
    private SupplierRequestDTO supplierRequestDTO;

    @Valid
    private ReceiverRequestDTO receiverRequestDTO;

    private Date storedAt;
    private Date deliveredAt;

    @Min(value = 0, message = "Number of failures must not be negative")
    private Integer numberFailures=0;

//    @Future(message = "Return cause date must be in the future")
    private Date returnCause;
    @NotEmpty(message = "mã đơn hàng không được bỏ trống")
    private String warehouseId;
}
