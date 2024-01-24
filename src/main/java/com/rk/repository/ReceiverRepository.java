package com.rk.repository;

import com.rk.model.Receiver;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ReceiverRepository extends JpaRepository<Receiver,Long> {
}
