package com.korike.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Commission;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, Long>{

}