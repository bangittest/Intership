package com.rk.service.user;

import com.rk.dto.request.user.RegisterRequestDTO;
import com.rk.dto.request.user.UserRequestDTO;
import com.rk.dto.response.user.UserResponseDTO;
import com.rk.exception.CustomException;
import com.rk.model.Role;
import com.rk.model.User;
import com.rk.repository.RoleRepository;
import com.rk.repository.UserRepository;
import com.rk.security.jwt.JWTProvider;
import com.rk.security.principle.UserDetailService;
import com.rk.security.principle.UserPrinciple;
import com.rk.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    public User register(RegisterRequestDTO registerRequestDTO) throws CustomException {
        // check trung
        if(userRepository.existsByUserName(registerRequestDTO.getUserName())){
            throw new CustomException("UserName existed");
        }
        User user=new User();
        //roles
        Set<Role> roles=new HashSet<>();
        if (user.getRoles()==null||user.getRoles().isEmpty()){
            roles.add(roleService.findRoleByName("USER"));
        }
        else {
            user.getRoles().forEach(role -> {
                roles.add(roleService.findRoleByName(role.getName()));
            });
        }

        user.setUserName(registerRequestDTO.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setStatus(true);
        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) throws CustomException {
        Authentication authentication;
        try {
            authentication = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(), userRequestDTO.getPassword()));
            Long userId=userDetailService.getUserIdFromAuthentication(authentication);
            User user=userRepository.findById(userId).orElseThrow(()->new CustomException("User not found"));
            if (user.getStatus().equals(false)){
                throw new CustomException("Tài khoản của bạn đã khóa");
            }
        } catch (CustomException e) {
            throw new CustomException("Invalid username or password");
        }

        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();


        return UserResponseDTO.builder()
                .token(jwtProvider.generateToken(userPrinciple))
                .userName(userPrinciple.getUsername())
                .roles(userPrinciple.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
