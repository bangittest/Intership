package com.rk.service.role;

import com.rk.model.Role;
import com.rk.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
