package com.rk.dto.request.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRequestDTO {
    @NotEmpty(message = "Tài khoản không được để trống")
    private String userName;
    @Size(min = 3,message = "Nhập mật khẩu ít nhất 6 ký tự")
    private String password;
}
