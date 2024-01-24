package com.rk.dto.request.orders;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderRequest1DTO {
    private Integer orderStatus;
    private Date createdDate;
    private String warehouseCode;
    private String warehouseName;
}
