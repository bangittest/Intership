package com.rk.service.role;

import com.rk.model.Role;
import org.springframework.stereotype.Repository;

public interface RoleService {
    Role findRoleByName(String name);
}
