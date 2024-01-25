package com.rk.repository;

import com.rk.model.Excel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<Excel,Long> {

}
