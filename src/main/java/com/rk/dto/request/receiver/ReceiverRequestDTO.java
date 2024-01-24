package com.rk.dto.request.receiver;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ReceiverRequestDTO {

        @NotBlank(message = "Supplier name is required")
        @Size(max = 50, message = "Supplier name must be at most 50 characters")
        private String name;

        @NotBlank(message = "Supplier address is required")
        @Size(max = 200, message = "Supplier address must be at most 200 characters")
        private String address;

        @Pattern(regexp = "^[0-9]{1,11}$", message = "Invalid supplier phone number")
        private String phoneNumber;

        @Email(message = "Invalid email format")
        @Size(max = 50, message = "Email must be at most 50 characters")
        private String email;
//        @NotNull(message = "Latitude is required")
//        @DecimalMin(value = "-90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees")
//        @DecimalMax(value = "90.0", inclusive = true, message = "Latitude must be between -90 and 90 degrees")
        private Float latitude;

//        @NotNull(message = "Longitude is required")
//        @DecimalMin(value = "-180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees")
//        @DecimalMax(value = "180.0", inclusive = true, message = "Longitude must be between -180 and 180 degrees")
        private Float longitude;
}
