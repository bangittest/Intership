package com.rk.dto.request.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SupplierRequestDTO {
//    @NotBlank(message = "Supplier name is required")
//    @Size(max = 50, message = "Supplier name must be at most 50 characters")
    private String name;

//    @NotBlank(message = "Supplier address is required")
//    @Size(max = 200, message = "Supplier address must be at most 200 characters")
    private String address;

    @Pattern(regexp = "^[0-9]{1,11}$", message = "Invalid supplier phone number")
    private String phoneNumber;

//    @Email(message = "Invalid email format")
//    @Size(max = 50, message = "Email must be at most 50 characters")
    private String email;
}
