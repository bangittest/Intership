package com.rk.dto.response.user;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private String token;
    private String userName;
    private Set<String> roles;
}
