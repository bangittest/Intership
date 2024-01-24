package com.rk.repository;

import com.rk.model.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonsRepository extends JpaRepository<Reason,Long> {
}
