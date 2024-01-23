package com.rk.service.user;

import com.rk.dto.request.user.RegisterRequestDTO;
import com.rk.dto.request.user.UserRequestDTO;
import com.rk.dto.response.user.UserResponseDTO;
import com.rk.exception.CustomException;
import com.rk.model.User;

public interface UserService {
    User register(RegisterRequestDTO registerRequestDTO) throws CustomException;
    UserResponseDTO login(UserRequestDTO userRequestDTO) throws CustomException;
}
