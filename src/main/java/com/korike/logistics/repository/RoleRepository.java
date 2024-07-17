package com.korike.logistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korike.logistics.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
