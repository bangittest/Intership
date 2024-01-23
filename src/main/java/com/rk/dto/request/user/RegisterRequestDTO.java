package com.rk.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDTO {
    @NotEmpty(message = "user không được bỏ trống")
    private String userName;
    @NotEmpty(message = "password không được bỏ trống")
    private String password;
}
